/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.discordsrv.common.listener.game;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.Text;
import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.Get;
import com.discordsrv.common.api.ListenerPriority;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.api.event.game.PlayerChatEvent;
import com.discordsrv.common.logging.Log;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.kyori.text.Component;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.StringPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class PlayerChatListener {

    private final AsyncHttpClient http = new DefaultAsyncHttpClient();
    private final Pattern webhookNamePattern = Pattern.compile("^DiscordSRV [0-9]+ #[12]$");
    private final Map<String, AtomicInteger> lastUsedWebhook = new HashMap<>();

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void onChat(PlayerChatEvent event, Channel channel, Player player, @Get(name="message") Component message) {
        Log.debug("Received " + (!event.willPublish() ? "NON-PUBLISHING " : "") + "chat event: " + player.getName() + " -> " + channel + " > " + Text.asPlain(message));
        if (!event.willPublish()) return;

        for (TextChannel textChannel : event.getChannel().getTargetChannels()) {
            textChannel.retrieveWebhooks().queue(webhooks -> {
                List<Webhook> good = new ArrayList<>();
                List<Webhook> bad = new ArrayList<>();
                for (Webhook webhook : webhooks) {
                    if (webhookNamePattern.matcher(webhook.getName()).matches()) {
                        if (good.size() < 2) {
                            good.add(webhook);
                        } else {
                            bad.add(webhook);
                        }
                    }
                }
                bad.forEach(w -> w.delete().queue(null, e -> Log.error("Failed to delete webhook " + w + ": " + e.getMessage())));
                DiscordSRV.get().getScheduler().runTaskAsync(() -> {
                    try {
                        synchronized (lastUsedWebhook) {
                            AtomicInteger counter = lastUsedWebhook.computeIfAbsent(textChannel.getId(), tc -> new AtomicInteger());
                            int next = counter.intValue() != 1 ? 1 : 2;
                            counter.set(next);
                        }

                        for (int i = 0; i < 2; i++) {
                            if (good.size() < 2) {
                                int number = good.size() == 0 ? 1 : 2;
                                Webhook webhook = textChannel.createWebhook("DiscordSRV " + textChannel.getId() + " #" + number).complete();
                                good.add(webhook);
                            }
                        }

                        AtomicInteger counter = lastUsedWebhook.computeIfAbsent(textChannel.getId(), tc -> new AtomicInteger());
                        int next = counter.intValue() != 1 ? 1 : 2;
                        counter.set(next);

                        Webhook webhook = good.get(next - 1);
                        BoundRequestBuilder post = http.preparePost(webhook.getUrl());
                        post.addBodyPart(new StringPart("content", Text.asPlain(message)));
                        post.addBodyPart(new StringPart("username", player.getName()));
                        post.addBodyPart(new StringPart("avatar_url", "https://crafatar.com/avatars/{uuid}?overlay".replace("{uuid}", player.getUuid().toString())));
                        ListenableFuture<Response> future = post.execute();
                        future.addListener(() -> {
                            try {
                                int code = future.get().getStatusCode();
                                if (code != 204) {
                                    Log.error("Received bad API response for webhook message delivery: " + code);
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }, null);
                    } catch (PermissionException e) {
                        Log.error("Failed to create webhook for channel " + textChannel + ": " + e.getMessage());
                    }
                });
            }, e -> Log.error("Failed to retrieve webhooks for channel " + textChannel + ": " + e.getMessage()));
        }

    }

}
