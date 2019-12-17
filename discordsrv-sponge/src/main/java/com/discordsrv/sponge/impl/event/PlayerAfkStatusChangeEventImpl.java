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

package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerAfkStatusChangeEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;
import net.kyori.text.Component;

import javax.annotation.Nullable;

public class PlayerAfkStatusChangeEventImpl extends PublishCancelableEvent implements PlayerAfkStatusChangeEvent {

    @Getter private final boolean goingAfk;
    @Getter private final Player player;
    @Getter private final Component message;
    @Getter private final Channel channel;

    public PlayerAfkStatusChangeEventImpl(boolean goingAfk, Player player, @Nullable Component message, Channel channel, boolean cancelled) {
        this.goingAfk = goingAfk;
        this.player = player;
        this.message = message;
        this.channel = channel;
        this.setWillPublish(!cancelled);
    }
}
