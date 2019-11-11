package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.PlayerAwardedEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;

public class PlayerAwardedAdvancementEventImpl extends PublishCancelableEvent implements PlayerAwardedEvent {

    @Getter private final String award;
    @Getter private final Player player;
    private final Channel channel; // TODO: not used

    public PlayerAwardedAdvancementEventImpl(String award, Player player, boolean cancelled, Channel channel) {
        this.award = award;
        this.player = player;
        this.channel = channel;
        this.setWillPublish(!cancelled);
    }

}
