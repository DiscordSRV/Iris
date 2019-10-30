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
import com.discordsrv.common.api.event.game.PlayerConnectionEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;
import net.kyori.text.Component;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEventImpl extends PublishCancelableEvent implements PlayerConnectionEvent {

    @Getter private final PlayerEvent rawEvent;
    @Getter private final Component message;
    @Getter private final State state;

    public PlayerConnectionEventImpl(PlayerJoinEvent rawEvent) {
        this.rawEvent = rawEvent;
        this.message = LegacyComponentSerializer.INSTANCE.deserialize(rawEvent.getJoinMessage());
        this.state = State.JOIN;
        this.setWillPublish(StringUtils.isNotBlank(rawEvent.getJoinMessage()));
    }

    public PlayerConnectionEventImpl(PlayerQuitEvent rawEvent) {
        this.rawEvent = rawEvent;
        this.message = LegacyComponentSerializer.INSTANCE.deserialize(rawEvent.getQuitMessage());
        this.state = State.QUIT;
        this.setWillPublish(StringUtils.isNotBlank(rawEvent.getQuitMessage()));
    }

    @Override
    public boolean isFirstTime() {
        return !rawEvent.getPlayer().hasPlayedBefore();
    }

    @Override
    public Player getPlayer() {
        return PlayerImpl.get(rawEvent.getPlayer()).orElseThrow(() -> new RuntimeException("PlayerConnectionEvent has null player"));
    }

}
