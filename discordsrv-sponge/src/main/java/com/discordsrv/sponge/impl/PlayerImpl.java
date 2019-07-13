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

package com.discordsrv.sponge.impl;

import com.discordsrv.common.abstracted.Player;
import net.kyori.text.Component;
import net.kyori.text.adapter.spongeapi.TextAdapter;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.UUID;

public class PlayerImpl implements Player {

    private final org.spongepowered.api.entity.living.player.Player player;

    public PlayerImpl(org.spongepowered.api.entity.living.player.Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public Component getDisplayName() {
        return GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(player.getDisplayNameData().displayName().get()));
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public boolean isVanished() {
        return player.get(Keys.VANISH).orElse(false);
    }

    @Override
    public void sendMessage(Component component) {
        TextAdapter.sendComponent(player, component);
    }

}
