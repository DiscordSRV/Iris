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

package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.common.abstracted.channel.BaseChannelManager;
import com.discordsrv.common.logging.Log;
import lombok.Getter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.*;

public class ChannelManagerImpl extends BaseChannelManager {

    @Getter private Set<MessageChannelTranslator> channelTranslators = new HashSet<>();

    @Override
    public void load() {
        Map<String, Object> channelsFromConfig = DiscordSRV.getConfig().getMap("Channels");
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

            if (channelsToLink.isEmpty()) {
                Log.error("Channel " + entry.getKey() + " was assigned to 0 channels");
                continue;
            }

            Log.debug("Channel " + entry.getKey() + " <-> " + channelsToLink);

            // Boop
            boolean useBoop = Sponge.getPluginManager().isLoaded("boop");
            if (useBoop) {
                getChannelTranslators().add(new BoopChannelTranslator());
            }

            // Nucleus
            if (Sponge.getPluginManager().isLoaded("nucleus")) {
                if (entry.getKey().equalsIgnoreCase("staff-chat")) {
                    if (io.github.nucleuspowered.nucleus.api.NucleusAPI.getStaffChatService().isPresent()) {
                        NucleusStaffChatChannel channel = new NucleusStaffChatChannel(channelsToLink);
                        getChannels().add(channel);
                        getChannelTranslators().add(channel);
                        Log.debug("Hooked Nucleus Staff chat");
                    } else {
                        Log.debug("Nucleus staff chat was disabled through Nucleus; not enabling the staff chat channel");
                    }
                }

                if (entry.getKey().equalsIgnoreCase("helpop")) {
                    NucleusHelpOpChannel channel = new NucleusHelpOpChannel(channelsToLink);
                    getChannels().add(channel);
                    getChannelTranslators().add(channel);
                    Log.debug("Hooked Nucleus HelpOp forwarding");
                }
            }

            // UltimateChat
            if (Sponge.getPluginManager().isLoaded("ultimatechat")) {
                br.net.fabiozumbi12.UltimateChat.Sponge.UCChannel channel = br.net.fabiozumbi12.UltimateChat.Sponge.UChat.get().getAPI().getChannels()
                        .stream()
                        .filter(c -> c.getName().equalsIgnoreCase(entry.getKey()))
                        .findAny().orElse(null);

                if (channel != null) {
                    UltimateChatChannel chatChannel = new UltimateChatChannel(channel, channelsToLink);
                    getChannels().add(chatChannel);
                    Log.debug("Hooked to Ultimate Chat channel: " + chatChannel.getName());
                } else {
                    Log.debug("No matching Ultimate Chat channel found for: " + entry.getKey());
                }
            }

            // Vanilla
            if (entry.getKey().equalsIgnoreCase("global")) {
                Log.debug("Hooked Vanilla chat");
                VanillaChannel channel = new VanillaChannel(channelsToLink);
                getChannels().add(channel);
                getChannelTranslators().add(channel);
                if (useBoop) {
                    getChannels().add(new BoopChannel(channel, MessageChannel.TO_ALL));
                }
            }
        }
    }

    @Override
    public Optional<BaseChannel> getChannel(String target) {
        return super.getChannel(target);
    }

    public Optional<BaseChannel> getChannel(MessageChannel messageChannel) {
        return channelTranslators.stream()
                .map(translator -> translator.translate(messageChannel))
                .filter(Optional::isPresent)
                .findAny().orElse(Optional.empty());
    }
}
