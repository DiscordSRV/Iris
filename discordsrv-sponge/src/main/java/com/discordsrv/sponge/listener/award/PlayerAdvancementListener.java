package com.discordsrv.sponge.listener.award;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.MessageEventListener;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAwardedAdvancementEventImpl;
import org.spongepowered.api.event.advancement.AdvancementEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerAdvancementListener extends MessageEventListener {

//    @Listener(order = Order.POST)
//    public void onAdvancement(AdvancementEvent.Grant event) {
//        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
//    }

    @Override
    public boolean onEvent(MessageChannelEvent event) {
        if (event instanceof AdvancementEvent.Grant) {
            handle((AdvancementEvent.Grant) event);
            return true;
        }
        return false;
    }

    private void handle(AdvancementEvent.Grant event) {
        Optional<MessageChannel> messageChannel = event.getChannel();
        if (!messageChannel.isPresent()) {
            Log.debug("Received a advancement message with no channel present (original channel: " + event.getOriginalChannel().getClass().getName() + ")");
            return;
        }

        SpongePlugin.get().getChannelManager().getChannel(messageChannel.get()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerAwardedAdvancementEventImpl(
                        event.getAdvancement().getName(), new PlayerImpl(event.getTargetEntity()), event.isMessageCancelled(), channel))
        );
    }
}
