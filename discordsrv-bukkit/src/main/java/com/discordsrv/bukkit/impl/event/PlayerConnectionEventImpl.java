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
import com.discordsrv.common.api.event.PlayerConnectionEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEventImpl extends CancelableEvent implements PlayerConnectionEvent {

    private final org.bukkit.entity.Player player;
    private final String message;
    private final State state;

    public PlayerConnectionEventImpl(PlayerJoinEvent event) {
        this.player = event.getPlayer();
        this.message = event.getJoinMessage();
        this.state = State.JOIN;
    }

    public PlayerConnectionEventImpl(PlayerQuitEvent event) {
        this.player = event.getPlayer();
        this.message = event.getQuitMessage();
        this.state = State.QUIT;
    }

    @Override
    public boolean isFirstTime() {
        return !player.hasPlayedBefore();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Player getPlayer() {
        return PlayerImpl.get(player).orElseThrow(() -> new RuntimeException("PlayerConnectionEvent has null player"));
    }

}
