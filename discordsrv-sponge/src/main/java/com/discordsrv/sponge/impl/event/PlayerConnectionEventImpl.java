package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerConnectionEvent;
import com.discordsrv.sponge.impl.PlayerImpl;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnectionEventImpl extends CancelableEvent implements PlayerConnectionEvent {

    private final org.spongepowered.api.entity.living.player.Player player;
    private final String message;
    private final State state;

    public PlayerConnectionEventImpl(ClientConnectionEvent event) {
        this.player = ((TargetPlayerEvent) event).getTargetEntity();
        this.message = ((MessageChannelEvent) event).getMessage().toPlain();
        this.state = event instanceof ClientConnectionEvent.Join ? State.JOIN : State.QUIT;
        setCancelled(((MessageChannelEvent) event).isMessageCancelled());
    }

    @Override
    public boolean isFirstTime() {
        return !player.hasPlayedBefore();
    }

    @Override
    public String getMessage() {
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
