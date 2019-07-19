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

import com.discordsrv.common.Builder;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.impl.PluginManagerImpl;
import com.discordsrv.sponge.impl.ServerImpl;
import com.discordsrv.sponge.listener.ChatListener;
import com.discordsrv.sponge.listener.PlayerConnectionListener;
import com.discordsrv.sponge.listener.PlayerDeathListener;
import com.discordsrv.sponge.listener.award.PlayerAchievementListener;
import com.discordsrv.sponge.listener.award.PlayerAdvancementListener;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.SpongeExecutorService;

import java.io.File;

@Plugin(
        id = "discordsrv",
        name = "DiscordSRV",
        description = "The most powerful, configurable, open-source Discord to Minecraft bridging plugin available.",
        url = "https://discordsrv.com",
        authors = {
                "Scarsz"
        }
)
public class SpongePlugin implements com.discordsrv.common.logging.Logger {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Inject
    private Logger logger;

    private DiscordSRV srv;

    private SpongeExecutorService asyncExecutor;

    @Listener
    public void onGamePreInit(GamePreInitializationEvent event) {
        Log.use(this);

        srv = new Builder()
                .usingPluginManager(new PluginManagerImpl())
                .usingServer(new ServerImpl())
                .build();

        asyncExecutor = Sponge.getScheduler().createAsyncExecutor(this);

        try {
            Class.forName("org.spongepowered.api.event.advancement.AdvancementEvent");
            Sponge.getEventManager().registerListeners(this, new PlayerAdvancementListener());
        } catch (ClassNotFoundException ignored) {
            Sponge.getEventManager().registerListeners(this, new PlayerAchievementListener());
        }
        Sponge.getEventManager().registerListeners(this, new ChatListener());
        Sponge.getEventManager().registerListeners(this, new PlayerConnectionListener());
        Sponge.getEventManager().registerListeners(this, new PlayerDeathListener());
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

    public File getDataFolder() {
        return dataFolder;
    }

    public SpongeExecutorService getAsyncExecutor() {
        return asyncExecutor;
    }

    public static SpongePlugin get() {
        return (SpongePlugin) Sponge.getPluginManager()
                .getPlugin("discordsrv").orElseThrow(RuntimeException::new)
                .getInstance().orElseThrow(RuntimeException::new);
    }
}
