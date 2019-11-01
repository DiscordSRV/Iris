package com.discordsrv.sponge.listener.award;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.Text;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.event.SpongeMessageEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAwardedAdvancementEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.achievement.GrantAchievementEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class PlayerAchievementListener {

    @Subscribe
    public void onMessage(SpongeMessageEvent event) {
        MessageChannelEvent messageEvent = event.getEvent();
        if (messageEvent instanceof GrantAchievementEvent.TargetPlayer && messageEvent.getCause().root() instanceof Player) {
            handle((GrantAchievementEvent.TargetPlayer) messageEvent);
            event.setHandled(true);
        }
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
