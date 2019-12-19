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
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAfkStatusChangeEventImpl;
import io.github.nucleuspowered.nucleus.api.events.NucleusAFKEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

public class NucleusAfkListener {

    @Listener(order = Order.POST)
    public void onNucleusAfk(NucleusAFKEvent event) {
        if (event instanceof NucleusAFKEvent.GoingAFK || event instanceof NucleusAFKEvent.ReturningFromAFK) {
            DiscordSRV.get().getScheduler().runTaskAsync(() -> handle(event));
        }
    }

    private void handle(NucleusAFKEvent event) {
        // todo: support multiple versions of nucleus

        SpongePlugin.get().getChannelManager().getChannel(event.getChannel()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerAfkStatusChangeEventImpl(
                        event instanceof NucleusAFKEvent.GoingAFK, new PlayerImpl(event.getTargetEntity()),
                        SpongePlugin.serialize(event.getMessage().orElse(SpongePlugin.EMPTY_TEXT)), channel, false))
        );
    }
}
