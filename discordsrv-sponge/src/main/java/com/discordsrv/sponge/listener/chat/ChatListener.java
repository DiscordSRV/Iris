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

package com.discordsrv.sponge.listener.chat;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.PlayerChatEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.util.Tristate;

public class ChatListener {

    @Listener(order = Order.POST)
    @IsCancelled(Tristate.UNDEFINED)
    public void onChatEvent(MessageChannelEvent.Chat event, @First Player player) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event, player));
    }

    private void handle(MessageChannelEvent event, Player player) {
        DiscordSRV.get().getChannelManager().getChannel("global").ifPresent(c ->
                DiscordSRV.get().getEventBus().publish(new PlayerChatEventImpl(event, c))
        );
    }

}
