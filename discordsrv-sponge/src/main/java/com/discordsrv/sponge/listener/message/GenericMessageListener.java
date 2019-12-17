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

package com.discordsrv.sponge.listener.message;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.api.ListenerPriority;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.SpongeMessageEvent;
import com.discordsrv.sponge.impl.event.GenericChatMessageEventImpl;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class GenericMessageListener {

    @Subscribe(priority = ListenerPriority.HIGH)
    public void onMessage(SpongeMessageEvent event) {
        if (event.isHandled()) return;
        handle(event.getEvent());
        event.setHandled(true);
    }

    private void handle(MessageChannelEvent event) {
        Optional<MessageChannel> messageChannel = event.getChannel();
        if (!messageChannel.isPresent()) {
            Log.debug("Received a chat message with no channel present (original channel: " + event.getOriginalChannel().getClass().getName() + ")");
            return;
        }

        SpongePlugin.get().getChannelManager().getChannel(messageChannel.get()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new GenericChatMessageEventImpl(channel,
                        SpongePlugin.serialize(event.getMessage()), (event instanceof Cancellable && ((Cancellable) event).isCancelled()) || event.isMessageCancelled()))
        );
    }
}
