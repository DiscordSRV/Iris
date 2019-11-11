package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerConnectionEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.PlayerImpl;
import lombok.Getter;
import net.kyori.text.Component;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnectionEventImpl extends PublishCancelableEvent implements PlayerConnectionEvent {

    @Getter private final boolean firstTime;
    @Getter private final Player player;
    @Getter private final Component message;
    @Getter private final State state;
    private final Channel channel; // TODO: not used

    public PlayerConnectionEventImpl(ClientConnectionEvent event, Channel channel) {
        org.spongepowered.api.entity.living.player.Player player = ((TargetPlayerEvent) event).getTargetEntity();
        this.firstTime = !player.hasPlayedBefore();
        this.player = new PlayerImpl(player);
        this.message = SpongePlugin.serialize(((MessageChannelEvent) event).getMessage());
        this.state = event instanceof ClientConnectionEvent.Join ? State.JOIN : State.QUIT;
        this.channel = channel;
        this.setWillPublish(!((MessageChannelEvent) event).isMessageCancelled());
    }

}
