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

package com.discordsrv.common.abstracted.channel;

import lombok.Synchronized;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface ChannelManager {

    Set<BaseChannel> channels = new HashSet<>();
    Set<BaseChannel> getChannels();
    Optional<BaseChannel> getChannel(String target);
    default Optional<BaseChannel> getChannel(TextChannel target) {
        return getChannel(target.getId());
    }

    @Synchronized(value = "channels") void load();

}
