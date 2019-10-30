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
import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerChatEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEventImpl extends PublishCancelableEvent implements PlayerChatEvent {

    @Getter private final AsyncPlayerChatEvent rawEvent;
    @Getter private final Channel channel;
    @Getter private final TextComponent message;

    public PlayerChatEventImpl(AsyncPlayerChatEvent rawEvent, Channel channel) {
        this.rawEvent = rawEvent;
        this.channel = channel;
        this.message = LegacyComponentSerializer.INSTANCE.deserialize(rawEvent.getMessage());
        this.setWillPublish(!rawEvent.isCancelled());
    }

    @Override
    public Player getPlayer() {
        return PlayerImpl.get(rawEvent.getPlayer()).orElseThrow(() -> new RuntimeException("PlayerChatEvent has null player"));
    }

}
