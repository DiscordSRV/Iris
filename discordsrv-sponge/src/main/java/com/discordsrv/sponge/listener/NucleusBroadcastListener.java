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

package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.GenericChatMessageEventImpl;
import io.github.nucleuspowered.nucleus.api.events.NucleusTextTemplateEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

public class NucleusBroadcastListener {

    @Listener(order = Order.POST)
    public void onBroadcast(NucleusTextTemplateEvent.Broadcast event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(NucleusTextTemplateEvent.Broadcast event) {
        SpongePlugin.get().getChannelManager().getChannel(Sponge.getServer().getBroadcastChannel()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new GenericChatMessageEventImpl(channel,
                        SpongePlugin.serialize(event.getMessageFor(Sponge.getServer().getConsole())), event.isCancelled()))
        );
    }
}
