package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerChatEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerChatEventImpl extends CancelableEvent implements PlayerChatEvent {

    private final MessageChannelEvent event;
    private final Component message;
    private final String channel;

    public PlayerChatEventImpl(MessageChannelEvent event, String channel) {
        this.event = event;
        this.message = GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(event.getMessage()));
        this.channel = channel; //TODO: get from MessageChannel (MessageChannel -> channel)
        setCancelled(event.isMessageCancelled());
    }

    @Override
    public String getChannel() {
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
