package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerAfkStatusChangeEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;
import net.kyori.text.Component;

import javax.annotation.Nullable;

public class PlayerAfkStatusChangeImpl extends PublishCancelableEvent implements PlayerAfkStatusChangeEvent {

    @Getter private final boolean goingAfk;
    @Getter private final Player player;
    @Getter private final Component message;
    @Getter private final Channel channel;

    public PlayerAfkStatusChangeImpl(boolean goingAfk, Player player, @Nullable Component message, Channel channel, boolean cancelled) {
        this.goingAfk = goingAfk;
        this.player = player;
        this.message = message;
        this.channel = channel;
        this.setWillPublish(!cancelled);
    }
}
