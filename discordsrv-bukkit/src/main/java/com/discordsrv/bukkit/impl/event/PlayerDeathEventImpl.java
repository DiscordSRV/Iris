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

package com.discordsrv.bukkit.impl.event;

import com.discordsrv.bukkit.impl.PlayerImpl;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerDeathEvent;
import lombok.Getter;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;

public class PlayerDeathEventImpl extends CancelableEvent implements PlayerDeathEvent {

    @Getter private final org.bukkit.event.entity.PlayerDeathEvent rawEvent;
    @Getter private final Component message;

    public PlayerDeathEventImpl(org.bukkit.event.entity.PlayerDeathEvent rawEvent) {
        this.rawEvent = rawEvent;
        this.message = this.rawEvent.getDeathMessage() != null
                ? LegacyComponentSerializer.INSTANCE.deserialize(this.rawEvent.getDeathMessage())
                : TextComponent.empty();
    }

    @Override
    public com.discordsrv.common.abstracted.Player getPlayer() {
        return PlayerImpl.get(rawEvent.getEntity()).orElseThrow(() -> new RuntimeException("PlayerDeathEvent has null player"));
    }

}
