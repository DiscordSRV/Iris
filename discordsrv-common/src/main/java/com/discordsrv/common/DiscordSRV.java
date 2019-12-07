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

package com.discordsrv.common;

import com.discordsrv.common.abstracted.PluginManager;
import com.discordsrv.common.abstracted.Server;
import com.discordsrv.common.abstracted.channel.ChannelManager;
import com.discordsrv.common.api.EventBus;
import com.discordsrv.common.api.event.discord.GuildMessageProcessingEvent;
import com.discordsrv.common.listener.PlayerChatListener;
import com.discordsrv.common.listener.discord.DiscordCannedResponseListener;
import com.discordsrv.common.logging.Log;
import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.Language;
import github.scarsz.configuralize.ParseException;
import github.scarsz.configuralize.Source;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DiscordSRV {

    private static DiscordSRV INSTANCE;

    @Getter private final DynamicConfig config;
    @Getter private final ChannelManager channelManager;
    @Getter private final EventBus eventBus;
    @Getter private final OkHttpClient httpClient;
    @Getter private final JDA jda;

    // platform supplied objects
    @Getter private final File dataFolder;
    @Getter private final PluginManager pluginManager;
    @Getter private final Server server;

    @Builder(builderClassName = "DSRVBuilder")
    DiscordSRV(@NonNull File dataFolder, @NonNull ChannelManager channelManager, @NonNull PluginManager pluginManager,
               @NonNull Server server) throws LoginException, IOException, ParseException, InterruptedException {
        if (INSTANCE != null) {
            throw new IllegalStateException("DiscordSRV is a singleton class. It should not be instantiated. Check usage.");
        } else {
            DiscordSRV.INSTANCE = this;
        }

        this.dataFolder = dataFolder;
        //noinspection ResultOfMethodCallIgnored
        this.dataFolder.mkdir();
        this.config = new DynamicConfig(
                new Source(DiscordSRV.class, "config", new File(getDataFolder(), "config.yml")),
                new Source(DiscordSRV.class, "messages", new File(getDataFolder(), "messages.yml"))
        );
        this.config.saveAllDefaults();
        this.config.loadAll();
        this.config.getOptionalString("Debug.Forced language")
                .flatMap(s -> Arrays.stream(Language.values())
                        .filter(language -> language.getCode().equalsIgnoreCase(s) ||
                                            language.name().equalsIgnoreCase(s))
                        .findFirst())
                .ifPresent(Text::setLanguage);

        this.channelManager = channelManager;
        this.channelManager.load();
        this.eventBus = new EventBus();
        this.pluginManager = pluginManager;
        this.server = server;

        this.httpClient = new OkHttpClient.Builder()
                // more lenient timeouts for slow networks (these 3 are 10 seconds by default)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build();

        this.jda = new JDABuilder()
                .setHttpClient(httpClient)
                .setToken(System.getenv("DISCORDSRV_TOKEN") != null
                        ? System.getenv("DISCORDSRV_TOKEN")
                        : config.getString("Token"))
                .addEventListeners(new ListenerAdapter() {
                    @Override
                    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                        eventBus.publish(new GuildMessageProcessingEvent(event));
                    }
                })
                .build().awaitReady();

        registerListeners();
    }

    private void registerListeners() {
        if (eventBus.getListener(DiscordCannedResponseListener.class) == null) eventBus.subscribe(new DiscordCannedResponseListener());
        if (eventBus.getListener(PlayerChatListener.class) == null) eventBus.subscribe(new PlayerChatListener());
    }

    public void shutdown() {
        // try to shut down jda gracefully
        if (jda != null) {
            CompletableFuture<Void> shutdownTask = new CompletableFuture<>();
            jda.addEventListener(new ListenerAdapter() {
                @Override
                public void onShutdown(@Nonnull ShutdownEvent event) {
                    shutdownTask.complete(null);
                }
            });
            jda.shutdown();
            try {
                shutdownTask.get(5, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                Log.warn("JDA took too long to shut down, skipping");
            } catch (InterruptedException | ExecutionException e) {
                Log.warn("Error occurred while waiting for JDA to shutdown: " + e.getMessage());
            }
        }
    }

    public static DiscordSRV get() {
        return INSTANCE;
    }

}
