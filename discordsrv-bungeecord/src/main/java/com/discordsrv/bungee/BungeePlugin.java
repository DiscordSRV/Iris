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

package com.discordsrv.bungee;

import com.discordsrv.bungee.impl.PluginManagerImpl;
import com.discordsrv.bungee.impl.ServerImpl;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.common.logging.Logger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import javax.security.auth.login.LoginException;

public final class BungeePlugin extends Plugin implements Logger {

    private DiscordSRV srv;

    @Override
    public void onEnable() {
        Log.use(this);

        try {
            srv = DiscordSRV.builder()
                    .pluginManager(new PluginManagerImpl())
                    .server(new ServerImpl())
                    .build();
        } catch (LoginException e) {
            getLogger().severe("Failed to login to Discord");
            e.printStackTrace();
            return;
        }

        // TODO: events
    }

    @Override
    public void onDisable() {
        srv.shutdown();
    }

    @Override
    public void log(Log.LogLevel level, String message) {
        switch (level) {
            case INFO:
                getLogger().info(message);
                break;
            case WARN:
                getLogger().warning(message);
                break;
            case ERROR:
                getLogger().severe(message);
                break;
            case DEBUG:
                getLogger().info("[DEBUG] " + message);
                break;
        }
    }

    public static BungeePlugin get() {
        return (BungeePlugin) ProxyServer.getInstance().getPluginManager().getPlugin("DiscordSRV");
    }
}
