package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.GenericChatMessageEventImpl;
import io.github.nucleuspowered.nucleus.api.events.NucleusTextTemplateEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

public class NucleusBroadcastListener {

    @Listener(order = Order.POST)
    public void onBroadcast(NucleusTextTemplateEvent.Broadcast event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(NucleusTextTemplateEvent.Broadcast event) {
        SpongePlugin.get().getChannelManager().getChannel(Sponge.getServer().getBroadcastChannel()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new GenericChatMessageEventImpl(channel,
                        SpongePlugin.serialize(event.getMessageFor(Sponge.getServer().getConsole())), event.isCancelled()))
        );
    }
}
