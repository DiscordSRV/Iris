package com.discordsrv.sponge.listener.award;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.Text;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.MessageEventListener;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAwardedAdvancementEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.achievement.GrantAchievementEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerAchievementListener extends MessageEventListener {

//    @Listener(order = Order.POST)
//    @IsCancelled(Tristate.UNDEFINED)
//    public void onAchievement(GrantAchievementEvent.TargetPlayer event, @Root Player player) {
//        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event, player));
//    }

    @Override
    public boolean onEvent(MessageChannelEvent event) {
        if (event instanceof GrantAchievementEvent.TargetPlayer && event.getCause().root() instanceof Player) {
            handle((GrantAchievementEvent.TargetPlayer) event);
            return true;
        }
        return false;
    }

    private void handle(GrantAchievementEvent.TargetPlayer event) {
        Optional<MessageChannel> messageChannel = event.getChannel();
        if (!messageChannel.isPresent()) {
            Log.debug("Received a achievement message with no channel present");
            return;
        }

        String achievement = event.getAchievement().getTranslation().get(Text.languageAsLocale());
        SpongePlugin.get().getChannelManager().getChannel(messageChannel.get()).ifPresent(channel ->
                DiscordSRV.get().getEventBus().publish(new PlayerAwardedAdvancementEventImpl(
                        achievement, new PlayerImpl(event.getTargetEntity()), event.isCancelled() || event.isMessageCancelled(), channel))
        );

    }
}
