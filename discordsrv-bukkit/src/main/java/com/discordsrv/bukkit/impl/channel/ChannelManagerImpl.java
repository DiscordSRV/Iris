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

package com.discordsrv.bukkit.impl.channel;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.abstracted.channel.BaseChannelManager;
import com.discordsrv.common.logging.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChannelManagerImpl extends BaseChannelManager {

    @Override
    public void load() {
        Map<String, Object> channelsFromConfig = DiscordSRV.get().getConfig().getMap("Channels");
        getChannels().clear();
        Log.debug("Loading channels from " + channelsFromConfig);
        for (Map.Entry<String, Object> entry : channelsFromConfig.entrySet()) {
            Set<String> channelsToLink = new HashSet<>();
            if (entry.getValue() instanceof Collection) {
                //noinspection unchecked
                ((Collection) entry.getValue()).stream()
                        .map(String::valueOf)
                        .forEach(o -> channelsToLink.add((String) o));
            } else {
                channelsToLink.add(String.valueOf(entry.getValue()));
            }

            Log.debug("Channel " + entry.getKey() + " <-> " + channelsToLink);
            //TODO plugin channel implementations
            VanillaChannel channel = new VanillaChannel(channelsToLink);
            getChannels().add(channel);
        }
    }

}
