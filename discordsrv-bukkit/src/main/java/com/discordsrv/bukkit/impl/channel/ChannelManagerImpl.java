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

import alexh.weak.Dynamic;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.abstracted.channel.BaseChannelManager;
import com.discordsrv.common.abstracted.channel.ChannelManager;
import com.discordsrv.common.logging.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChannelManagerImpl extends BaseChannelManager {

    @Override
    public void load() {
        synchronized (ChannelManager.channels) {
            ChannelManager.channels.clear();
            Dynamic channels = DiscordSRV.getConfig().dget("Channels");
            Log.debug("Loading channels from " + channels);
            channels.children().forEach(definition -> {
                String gameChannel = definition.get("Game").convert().intoString();

                Set<String> discordChannels = new HashSet<>();
                Dynamic dynamicDiscordChannels = definition.get("Discord");
                if (dynamicDiscordChannels.isList()) {
                    dynamicDiscordChannels.children()
                            .map(d -> d.convert().intoString())
                            .forEach(discordChannels::add);
                } else {
                    String raw = dynamicDiscordChannels.convert().intoString();
                    if (raw.contains(",")) {
                        Arrays.stream(raw.split(","))
                                .map(String::trim)
                                .filter(StringUtils::isNotBlank)
                                .filter(StringUtils::isNumeric)
                                .forEach(discordChannels::add);
                    } else {
                        raw = raw.trim();
                        if (StringUtils.isNotBlank(raw) && StringUtils.isNumeric(raw)) {
                            discordChannels.add(raw);
                        }
                    }
                }

                //TODO plugin channel implementations
                Log.debug("Channel " + gameChannel + " <-> " + discordChannels);
                getChannels().add(new VanillaChannel(gameChannel, discordChannels));
            });
        }
    }

}
