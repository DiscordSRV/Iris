package com.discordsrv.sponge.listener.message;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.SpongeMessageEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.util.Tristate;

public class MessageListener {

    @Listener(order = Order.POST)
    @IsCancelled(value = Tristate.UNDEFINED)
    public void onMessage(MessageChannelEvent event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(MessageChannelEvent event) {
        DiscordSRV.get().getEventBus().publish(new SpongeMessageEvent(event));
    }
}
