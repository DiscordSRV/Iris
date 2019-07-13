package com.discordsrv.sponge.listener.award;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import com.discordsrv.sponge.impl.event.PlayerAwardedAdvancementEventImpl;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.achievement.GrantAchievementEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.util.Tristate;

import java.util.Locale;
import java.util.Optional;

public class PlayerAchievementListener {

    @Listener(order = Order.POST)
    @IsCancelled(Tristate.UNDEFINED)
    public void onAchievement(GrantAchievementEvent.TargetPlayer event, @Root Player player) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event, player));
    }

    private void handle(GrantAchievementEvent.TargetPlayer event, Player player) {
        Optional<MessageChannel> channel = event.getChannel(); // TODO use
        if (channel.isPresent()) {
            String achievement = event.getAchievement().getTranslation().get(Locale.ENGLISH);
            DiscordSRV.get().getEventBus().publish(new PlayerAwardedAdvancementEventImpl(
                    achievement, new PlayerImpl(player), event.isCancelled() || event.isMessageCancelled()));
        } else {
            Log.debug("Received a achievement message with no channel present");
        }
    }
}
