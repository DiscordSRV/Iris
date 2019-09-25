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

package com.discordsrv.common.abstracted.channel;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import lombok.Getter;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import net.kyori.text.TextComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class BaseChannel implements Channel {

    @Getter private final String name;
    @Getter private final Set<String> targetChannelIds;

    public BaseChannel(String name, Set<String> targetChannelIds) {
        this.name = name;
        this.targetChannelIds = targetChannelIds;
    }

    public void sendToMinecraft(String message) {
        sendToMinecraft(TextComponent.of(message));
    }
    public abstract void sendToMinecraft(TextComponent message);

    public void sendToDiscord(String message) {
        sendToDiscord(new MessageBuilder(message).build());
    }
    public void sendToDiscord(String message, Consumer<? super Message> success) {
        sendToDiscord(new MessageBuilder(message).build(), success);
    }
    public void sendToDiscord(String message, Consumer<? super Message> success, Consumer<? super Throwable> fail) {
        sendToDiscord(new MessageBuilder(message).build(), success, fail);
    }
    public void sendToDiscord(Message message) {
        sendToDiscord(message, null);
    }
    public void sendToDiscord(Message message, Consumer<? super Message> success) {
        sendToDiscord(message, success, null);
    }
    public void sendToDiscord(Message message, Consumer<? super Message> success, Consumer<? super Throwable> fail) {
        getTargetChannels().stream()
                .map(textChannel -> textChannel.sendMessage(message))
                .forEach(messageAction -> messageAction.queue(success, fail));
    }

    public Set<Message> sendToDiscordNow(String message) {
        return sendToDiscordNow(new MessageBuilder(message).build());
    }
    public Set<Message> sendToDiscordNow(Message message) {
        return getTargetChannels().stream()
                .map(textChannel -> textChannel.sendMessage(message))
                .map(RestAction::complete)
                .collect(Collectors.toSet());
    }

    public Set<TextChannel> getTargetChannels() {
        return targetChannelIds.stream()
                .filter(id -> {
                    if (StringUtils.isBlank(id) || id.length() < 18) {
                        Log.error("Invalid channel ID \"" + id + "\" given for channel " + name);
                        return false;
                    }
                    return true;
                })
                .map(id -> DiscordSRV.get().getJda().getTextChannelById(id))
                .collect(Collectors.toSet());
    }

}
