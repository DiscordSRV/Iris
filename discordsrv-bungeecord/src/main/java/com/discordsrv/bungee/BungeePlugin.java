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

import com.discordsrv.common.logging.Log;
import com.discordsrv.common.logging.Logger;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeePlugin extends Plugin implements Logger {

    @Override
    public void onEnable() {
        Log.use(this);
    }

    @Override
    public void onDisable() {

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

}
