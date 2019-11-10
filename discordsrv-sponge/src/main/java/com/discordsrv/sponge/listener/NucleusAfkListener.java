package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAfkStatusChangeImpl;
import io.github.nucleuspowered.nucleus.api.events.NucleusAFKEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.text.Text;

public class NucleusAfkListener {

    @Listener(order = Order.POST)
    public void onNucleusAfk(NucleusAFKEvent event) {
        if (event instanceof NucleusAFKEvent.GoingAFK || event instanceof NucleusAFKEvent.ReturningFromAFK) {
            SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
        }
    }

    private void handle(NucleusAFKEvent event) {
        // todo: support multiple versions of nucleus

        SpongePlugin.get().getChannelManager().getChannel(event.getChannel()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerAfkStatusChangeImpl(
                        event instanceof NucleusAFKEvent.GoingAFK, new PlayerImpl(event.getTargetEntity()),
                        SpongePlugin.serialize(event.getMessage().orElse(Text.EMPTY)), channel, false))
        );
    }
}
