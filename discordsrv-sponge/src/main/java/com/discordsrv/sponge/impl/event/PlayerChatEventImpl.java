package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerChatEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class PlayerChatEventImpl extends CancelableEvent implements PlayerChatEvent {

    private final MessageChannelEvent event;
    private final String channel;

    public PlayerChatEventImpl(MessageChannelEvent event, String channel) {
        this.event = event;
        this.channel = channel; // TODO: get from MessageChannel (MessageChannel -> channel)
        setCancelled(event.isMessageCancelled());
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getMessage() {
        return event.getMessage().toPlain();
    }

    @Override
    public Player getPlayer() {
        return event.getCause().first(org.spongepowered.api.entity.living.player.Player.class).map(PlayerImpl::new).orElse(null);
    }
}
