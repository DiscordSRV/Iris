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
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEventImpl extends CancelableEvent implements PlayerChatEvent {

    private final AsyncPlayerChatEvent event;
    private final String channel;

    public PlayerChatEventImpl(AsyncPlayerChatEvent event, String channel) {
        this.event = event;
        this.channel = channel;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getMessage() {
        return event.getMessage();
    }

    @Override
    public Player getPlayer() {
        return PlayerImpl.get(event.getPlayer()).orElseThrow(() -> new RuntimeException("PlayerChatEvent has null player"));
    }

}
