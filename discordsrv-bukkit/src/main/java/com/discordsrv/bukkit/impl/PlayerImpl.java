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
import com.discordsrv.common.logging.Log;
import net.kyori.text.Component;
import net.kyori.text.adapter.bukkit.TextAdapter;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class PlayerImpl implements Player {

    private static final Map<UUID, Player> PLAYER_CACHE = new HashMap<>();

    public static Optional<Player> get(UUID uuid) {
        try {
            return Optional.of(
                    PLAYER_CACHE.computeIfAbsent(
                            uuid,
                            v -> new PlayerImpl(Bukkit.getPlayer(uuid))
                    )
            );
        } catch (Exception e) {
            Log.debug("Failed to get player " + uuid + ": " + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            return Optional.empty();
        }
    }

    public static Optional<Player> get(org.bukkit.entity.Player player) {
        return get(player.getUniqueId());
    }

    public static Optional<Player> get(String username) {
        Optional<Player> optionalPlayer = PLAYER_CACHE.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(username))
                .findFirst();

        if (optionalPlayer.isPresent()) {
            return optionalPlayer;
        }

        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(username);

        if (bukkitPlayer != null) {
            try {
                PlayerImpl player = new PlayerImpl(bukkitPlayer);
                PLAYER_CACHE.put(player.getUuid(), player);
                return Optional.of(player);
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private final org.bukkit.entity.Player player;

    private PlayerImpl(org.bukkit.entity.Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Passed null player to PlayerImpl");
        }

        this.player = player;
        PLAYER_CACHE.put(getUuid(), this);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public Component getDisplayName() {
        return LegacyComponentSerializer.INSTANCE.deserialize(player.getDisplayName());
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public boolean isVanished() {
        //TODO
        return false;
    }

    @Override
    public void sendMessage(Component component) {
        TextAdapter.sendComponent(player, component);
    }
}
