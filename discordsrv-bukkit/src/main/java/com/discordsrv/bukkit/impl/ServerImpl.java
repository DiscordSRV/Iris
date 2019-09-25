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

package com.discordsrv.bukkit.impl;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.Server;
import com.discordsrv.common.logging.Log;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerImpl implements Server {

    private static Method getOnlinePlayersMethod;

    static {
        try {
            getOnlinePlayersMethod = Bukkit.class.getMethod("getOnlinePlayers");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Player> getOnlinePlayers() {
        try {
            Stream<?> stream;
            if (getOnlinePlayersMethod.getReturnType().equals(Collection.class)) {
                //noinspection unchecked
                stream = ((Collection<? extends org.bukkit.entity.Player>) getOnlinePlayersMethod.invoke(null)).stream();
            } else {
                stream = Arrays.stream((org.bukkit.entity.Player[]) getOnlinePlayersMethod.invoke(null));
            }

            return stream
                    .filter(o -> {
                        if (o instanceof org.bukkit.entity.Player) {
                            return true;
                        } else {
                            Log.error("Bukkit#getOnlinePlayers gave a result that was NOT a player object -> " + o);
                            return false;
                        }
                    })
                    .map(o -> (org.bukkit.entity.Player) o)
                    .map(PlayerImpl::get)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.error("Failed to get online players: " + e.getMessage());
            return Collections.emptySet();
        }
    }

}
