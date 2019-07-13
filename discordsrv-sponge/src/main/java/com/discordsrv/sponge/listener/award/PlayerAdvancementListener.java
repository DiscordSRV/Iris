package com.discordsrv.sponge.listener.award;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAwardedAdvancementEventImpl;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.advancement.AdvancementEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerAdvancementListener {

    @Listener(order = Order.POST)
    public void onAdvancement(AdvancementEvent.Grant event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(AdvancementEvent.Grant event) {
        Optional<MessageChannel> channel = event.getChannel(); // TODO use
        if (channel.isPresent()) {
            DiscordSRV.get().getEventBus().publish(new PlayerAwardedAdvancementEventImpl(
                    event.getAdvancement().getName(), new PlayerImpl(event.getTargetEntity()), event.isMessageCancelled()));
        } else {
            Log.debug("Received a advancement message with no channel present");
        }
    }
}
