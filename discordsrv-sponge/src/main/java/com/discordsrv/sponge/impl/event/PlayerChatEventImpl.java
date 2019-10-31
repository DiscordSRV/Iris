package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerChatEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import lombok.Getter;
import net.kyori.text.Component;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;

public class PlayerChatEventImpl extends PublishCancelableEvent implements PlayerChatEvent {

    @Getter private final Player player;
    @Getter private final Component message;
    @Getter private final Channel channel;

    public PlayerChatEventImpl(Event event, Text message, boolean cancelled, Channel channel) {
        this.player = event.getCause().first(org.spongepowered.api.entity.living.player.Player.class).map(PlayerImpl::new).orElse(null);
        this.message = SpongePlugin.serialize(message);
        this.channel = channel;
        this.setWillPublish(!cancelled);
    }

}
