package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerChatEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerChatEventImpl extends CancelableEvent implements PlayerChatEvent {

    private final MessageChannelEvent event;
    private final TextComponent message;
    private final String channel;

    public PlayerChatEventImpl(MessageChannelEvent event, String channel) {
        this.event = event;
        this.message = (TextComponent) GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(event.getMessage()));
        this.channel = channel; //TODO: get from MessageChannel (MessageChannel -> channel)
        setCanceled(event.isMessageCancelled());
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public TextComponent getMessage() {
        return message;
    }

    @Override
    public Player getPlayer() {
        return event.getCause().first(org.spongepowered.api.entity.living.player.Player.class).map(PlayerImpl::new).orElse(null);
    }

}
