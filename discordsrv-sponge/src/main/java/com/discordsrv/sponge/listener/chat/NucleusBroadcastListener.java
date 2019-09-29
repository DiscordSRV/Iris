package com.discordsrv.sponge.listener.chat;

import com.discordsrv.sponge.SpongePlugin;
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
        event.getMessageFor(Sponge.getServer().getConsole()); // TODO
    }
}
