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
import com.discordsrv.common.api.event.PlayerAwardedEvent;
import org.bukkit.entity.Player;

public class PlayerAwardedAdvancementEventImpl extends CancelableEvent implements PlayerAwardedEvent {

    private final String advancement;
    private final Player player;

    public PlayerAwardedAdvancementEventImpl(String advancement, Player player) {
        this.advancement = advancement;
        this.player = player;
    }

    @Override
    public String getAward() {
        return advancement;
    }

    @Override
    public com.discordsrv.common.abstracted.Player getPlayer() {
        return PlayerImpl.get(player).orElseThrow(() -> new RuntimeException("PlayerAwardedAdvancementEvent has null player"));
    }

}
