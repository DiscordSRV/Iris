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

package com.discordsrv.sponge;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.event.MessageListener;
import com.discordsrv.sponge.impl.PluginManagerImpl;
import com.discordsrv.sponge.impl.ServerImpl;
import com.discordsrv.sponge.impl.channel.ChannelManagerImpl;
import com.discordsrv.sponge.listener.NucleusAfkListener;
import com.discordsrv.sponge.listener.message.GenericMessageListener;
import com.discordsrv.sponge.listener.message.chat.ChatListener;
import com.discordsrv.sponge.listener.message.PlayerConnectionListener;
import com.discordsrv.sponge.listener.message.PlayerDeathListener;
import com.discordsrv.sponge.listener.message.award.PlayerAchievementListener;
import com.discordsrv.sponge.listener.message.award.PlayerAdvancementListener;
import com.discordsrv.sponge.listener.NucleusBroadcastListener;
import com.google.inject.Inject;
import github.scarsz.configuralize.ParseException;
import lombok.Getter;
import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.SpongeExecutorService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

@Plugin(
        id = "discordsrv",
        name = "DiscordSRV",
        description = "The most powerful, configurable, open-source Discord to Minecraft bridging plugin available.",
        url = "https://discordsrv.com",
        authors = {
                "Scarsz"
        },
        dependencies = {
                @Dependency(id = "nucleus", optional = true)
        }
)
public class SpongePlugin implements com.discordsrv.common.logging.Logger {

    @Inject @Getter @ConfigDir(sharedRoot = false) private File dataFolder;
    @Inject @Getter private Logger logger;
    @Getter private DiscordSRV srv;
    @Getter private SpongeExecutorService asyncExecutor;

    @Listener
    public void onGamePreInit(GamePreInitializationEvent event) {
        Log.use(this);

        try {
            srv = DiscordSRV.builder()
                    .dataFolder(dataFolder)
                    .channelManager(new ChannelManagerImpl())
                    .pluginManager(new PluginManagerImpl())
                    .server(new ServerImpl())
                    .build();
        } catch (IOException e) {
            logger.error("I/O exception while saving configuration files");
            e.printStackTrace();
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        } catch (LoginException | InterruptedException e) {
            logger.error("Failed to login to Discord");
            e.printStackTrace();
            return;
        }

        asyncExecutor = Sponge.getScheduler().createAsyncExecutor(this);

        try {
            Class.forName("org.spongepowered.api.event.advancement.AdvancementEvent");
            MessageListener.LISTENERS.add(new PlayerAdvancementListener());
        } catch (ClassNotFoundException ignored) {
            MessageListener.LISTENERS.add(new PlayerAchievementListener());
        }
        MessageListener.LISTENERS.add(new ChatListener());
        MessageListener.LISTENERS.add(new PlayerConnectionListener());
        MessageListener.LISTENERS.add(new PlayerDeathListener());
        MessageListener.LISTENERS.add(new GenericMessageListener());

        Sponge.getEventManager().registerListeners(this, new MessageListener());

        // for plugin hooks, fully-qualified package names are used to avoid loading classes
        // that might not have their respective plugin's classes available
        if (Sponge.getPluginManager().isLoaded("nucleus")) {
            Sponge.getEventManager().registerListeners(this, new com.discordsrv.sponge.listener.NucleusAfkListener());
            Sponge.getEventManager().registerListeners(this, new com.discordsrv.sponge.listener.NucleusBroadcastListener());
        }
    }

    @Listener
    public void onStopped(GameStoppedServerEvent event) {
        srv.shutdown();
    }

    @Override
    public void log(Log.LogLevel level, String message) {
        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(message);
                } else {
                    logger.info("[DEBUG] " + message);
                }
                break;
        }
    }

    public Component serialize(Text text) {
        return GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(text)); // TODO switch to jackson serializer once available
    }

    public Text serialize(Component component) {
        return TextSerializers.JSON.deserialize(GsonComponentSerializer.INSTANCE.serialize(component)); // TODO switch to jackson serializer once available
    }

    public static SpongePlugin get() {
        return (SpongePlugin) Sponge.getPluginManager()
                .getPlugin("discordsrv").orElseThrow(RuntimeException::new)
                .getInstance().orElseThrow(RuntimeException::new);
    }
}
