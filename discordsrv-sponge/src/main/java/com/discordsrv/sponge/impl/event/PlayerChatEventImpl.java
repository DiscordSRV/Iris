package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerChatEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import net.kyori.text.Component;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class PlayerChatEventImpl extends PublishCancelableEvent implements PlayerChatEvent {

    private final MessageChannelEvent event;
    private final Component message;
    private final Channel channel;

    public PlayerChatEventImpl(MessageChannelEvent event, Channel channel) {
        this.event = event;
        this.message = SpongePlugin.get().serialize(event.getMessage());
        this.channel = channel; //TODO: get from MessageChannel (MessageChannel -> channel)
        this.setWillPublish(!event.isMessageCancelled());
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public Component getMessage() {
        return message;
    }

    @Override
    public Player getPlayer() {
        return event.getCause().first(org.spongepowered.api.entity.living.player.Player.class).map(PlayerImpl::new).orElse(null);
    }

}
