package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.PlayerConnectionEventImpl;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerConnectionListener {

    @Listener(order = Order.POST)
    public void onLogin(ClientConnectionEvent.Join event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    @Listener(order = Order.POST)
    public void onDisconnect(ClientConnectionEvent.Disconnect event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(ClientConnectionEvent event) {
        Optional<MessageChannel> channel = ((MessageChannelEvent) event).getChannel(); // TODO use
        if (channel.isPresent()) {
            DiscordSRV.get().getEventBus().publish(new PlayerConnectionEventImpl(event));
        } else {
            Log.debug("Received a leave message with no channel present");
        }
    }
}
