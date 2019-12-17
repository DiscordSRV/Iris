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

package com.discordsrv.sponge.event;

import com.discordsrv.common.api.event.discord.HandledEvent;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.api.event.message.MessageChannelEvent;

/**
 * Message event class for DiscordSRV-Sponge.
 * Runs on Sponge's POST {@link org.spongepowered.api.event.Order Order} and includes cancelled events.
 * Always async.
 */
public class SpongeMessageEvent implements HandledEvent {

    @Getter @Setter private boolean handled = false;
    @Getter private final MessageChannelEvent event;

    public SpongeMessageEvent(MessageChannelEvent event) {
        this.event = event;
    }
}
