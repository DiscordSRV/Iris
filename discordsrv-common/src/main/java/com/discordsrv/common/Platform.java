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

import com.discordsrv.common.logging.Log;

public enum Platform {

    BUKKIT,
    BUNGEE,
    SPONGE;

    public static final Platform PLATFORM;

    static {
        PLATFORM = isBukkit()
                ? Platform.BUKKIT
                : isBungee()
                    ? Platform.BUNGEE
                    : isSponge()
                        ? Platform.SPONGE
                        : null;
        if (PLATFORM == null) throw new IllegalStateException("Unable to determine platform type");
        Log.debug("Platform = " + PLATFORM);
    }

    private static boolean isBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isBungee() {
        try {
            Class.forName("net.md_5.bungee.api.ProxyServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isSponge() {
        try {
            Class.forName("org.spongepowered.api.Sponge");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
