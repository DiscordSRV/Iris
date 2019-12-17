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
import com.discordsrv.common.api.event.game.PlayerDeathEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import lombok.Getter;
import net.kyori.text.Component;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class PlayerDeathEventImpl extends PublishCancelableEvent implements PlayerDeathEvent {

    @Getter private final Player player;
    @Getter private final Component message;
    private final Channel channel; // TODO: not used

    public PlayerDeathEventImpl(DestructEntityEvent.Death event, Channel channel) {
        Entity targetEntity = event.getTargetEntity();
        if (!(targetEntity instanceof Player)) throw new RuntimeException("Death event's target entity wasn't a Player");
        this.player = new PlayerImpl((org.spongepowered.api.entity.living.player.Player) targetEntity);
        this.message = SpongePlugin.serialize(event.getMessage());
        this.channel = channel;
        this.setWillPublish(!event.isCancelled() && !event.isMessageCancelled());
    }
}
