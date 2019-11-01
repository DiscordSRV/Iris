package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.SpongeMessageEvent;
import com.discordsrv.sponge.impl.event.PlayerConnectionEventImpl;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerConnectionListener {

    @Subscribe
    public void onMessage(SpongeMessageEvent event) {
        MessageChannelEvent messageEvent = event.getEvent();
        if (messageEvent instanceof ClientConnectionEvent.Join || messageEvent instanceof ClientConnectionEvent.Disconnect) {
            handle((ClientConnectionEvent) messageEvent);
            event.setHandled(true);
        }
    }

    private void handle(ClientConnectionEvent event) {
        Optional<MessageChannel> messageChannel = ((MessageChannelEvent) event).getChannel();
        if (!messageChannel.isPresent()) {
            Log.debug("Received a " + (event instanceof ClientConnectionEvent.Join ? "join" : "leave") + " message with no channel present (original channel: " + ((MessageChannelEvent) event).getOriginalChannel().getClass().getName() + ")");
            return;
        }

        SpongePlugin.get().getChannelManager().getChannel(messageChannel.get()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerConnectionEventImpl(event, channel))
        );
    }
}
