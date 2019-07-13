package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.PlayerDeathEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public class PlayerDeathListener {

    @Listener(order = Order.POST)
    @IsCancelled(Tristate.UNDEFINED)
    public void onDeath(DestructEntityEvent.Death event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(DestructEntityEvent.Death event) {
        Optional<MessageChannel> channel = event.getChannel(); // TODO use
        if (channel.isPresent()) {
            if (!(event.getTargetEntity() instanceof Player)) return;
            DiscordSRV.get().getEventBus().publish(new PlayerDeathEventImpl(event));
        } else {
            Log.debug("Received a death message with no channel present");
        }
    }
}
