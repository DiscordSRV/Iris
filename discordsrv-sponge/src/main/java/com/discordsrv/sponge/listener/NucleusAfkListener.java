package com.discordsrv.sponge.listener;

import com.discordsrv.sponge.SpongePlugin;
import io.github.nucleuspowered.nucleus.api.events.NucleusAFKEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

public class NucleusAfkListener {

    @Listener(order = Order.POST)
    public void onNucleusAfk(NucleusAFKEvent event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(NucleusAFKEvent event) {
        // TODO
    }
}
