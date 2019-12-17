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

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import io.github.nucleuspowered.nucleus.api.chat.NucleusChatChannel;
import net.kyori.text.Component;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;
import java.util.Set;

public class NucleusStaffChatChannel extends BaseChannel implements MessageChannelTranslator {

    public NucleusStaffChatChannel(Set<String> targetChannelIds) {
        super("staff", targetChannelIds);
    }

    @Override
    public void sendToMinecraft(Component message) {
        NucleusAPI.getStaffChatService().ifPresent(service -> service.getStaffChat().send(SpongePlugin.serialize(message)));
    }

    @Override
    public Optional<BaseChannel> translate(MessageChannel messageChannel) {
        if (messageChannel instanceof NucleusChatChannel.StaffChat) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
