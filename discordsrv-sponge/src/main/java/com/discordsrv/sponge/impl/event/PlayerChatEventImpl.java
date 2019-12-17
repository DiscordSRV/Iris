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
import com.discordsrv.common.api.event.game.PlayerChatEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import lombok.Getter;
import net.kyori.text.Component;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

public class PlayerChatEventImpl extends PublishCancelableEvent implements PlayerChatEvent {

    @Getter private final Player player;
    @Getter private final Component message;
    @Getter private final Channel channel;

    public PlayerChatEventImpl(Event event, Text message, boolean cancelled, Channel channel) {
        this.player = event.getCause().first(org.spongepowered.api.entity.living.player.Player.class).map(PlayerImpl::new).orElse(null);
        this.message = SpongePlugin.serialize(message);
        this.channel = channel;
        this.setWillPublish(!cancelled);
    }

}
