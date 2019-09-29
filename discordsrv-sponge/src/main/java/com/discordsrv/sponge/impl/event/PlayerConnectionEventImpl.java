package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.PlayerConnectionEvent;
import com.discordsrv.common.api.event.PublishCancelableEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerConnectionEventImpl extends PublishCancelableEvent implements PlayerConnectionEvent {

    private final org.spongepowered.api.entity.living.player.Player player;
    private final Component message;
    private final State state;

    public PlayerConnectionEventImpl(ClientConnectionEvent event) {
        this.player = ((TargetPlayerEvent) event).getTargetEntity();
        this.message = GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(((MessageChannelEvent) event).getMessage()));
        this.state = event instanceof ClientConnectionEvent.Join ? State.JOIN : State.QUIT;
        this.setPublishCanceled(((MessageChannelEvent) event).isMessageCancelled());
    }

    @Override
    public boolean isFirstTime() {
        return !player.hasPlayedBefore();
    }

    @Override
    public Component getMessage() {
        return message;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Player getPlayer() {
        return new PlayerImpl(player);
    }
}
