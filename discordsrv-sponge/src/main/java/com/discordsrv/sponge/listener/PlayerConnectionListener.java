package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.MessageEventListener;
import com.discordsrv.sponge.impl.event.PlayerChatEventImpl;
import com.discordsrv.sponge.impl.event.PlayerConnectionEventImpl;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerConnectionListener extends MessageEventListener {

//    @Listener(order = Order.POST)
//    public void onLogin(ClientConnectionEvent.Join event) {
//        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
//    }
//
//    @Listener(order = Order.POST)
//    public void onDisconnect(ClientConnectionEvent.Disconnect event) {
//        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
//    }

    @Override
    public boolean onEvent(MessageChannelEvent event) {
        if (event instanceof ClientConnectionEvent.Join || event instanceof ClientConnectionEvent.Disconnect) {
            handle((ClientConnectionEvent) event);
            return true;
        }
        return false;
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
