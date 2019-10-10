package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.game.PlayerAwardedEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;

public class PlayerAwardedAdvancementEventImpl extends PublishCancelableEvent implements PlayerAwardedEvent {

    private final String advancement;
    private final Player player;

    public PlayerAwardedAdvancementEventImpl(String advancement, Player player, boolean cancelled) {
        this.advancement = advancement;
        this.player = player;
        this.setWillPublish(!cancelled);
    }

    @Override
    public String getAward() {
        return advancement;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
