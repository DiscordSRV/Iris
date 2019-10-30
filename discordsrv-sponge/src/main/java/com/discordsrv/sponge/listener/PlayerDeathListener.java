package com.discordsrv.sponge.listener;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.MessageEventListener;
import com.discordsrv.sponge.impl.event.PlayerConnectionEventImpl;
import com.discordsrv.sponge.impl.event.PlayerDeathEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerDeathListener extends MessageEventListener {

//    @Listener(order = Order.POST)
//    @IsCancelled(Tristate.UNDEFINED)
//    public void onDeath(DestructEntityEvent.Death event) {
//        if (!(event.getTargetEntity() instanceof Player)) return;
//        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
//    }

    @Override
    public boolean onEvent(MessageChannelEvent event) {
        if (event instanceof DestructEntityEvent.Death && ((DestructEntityEvent.Death) event).getTargetEntity() instanceof Player) {
            handle((DestructEntityEvent.Death) event);
            return true;
        }
        return false;
    }

    private void handle(DestructEntityEvent.Death event) {
        Optional<MessageChannel> messageChannel = event.getChannel();
        if (!messageChannel.isPresent()) {
            Log.debug("Received a death message with no channel present");
            return;
        }

        SpongePlugin.get().getChannelManager().getChannel(messageChannel.get()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerDeathEventImpl(event, channel))
        );
    }
}
