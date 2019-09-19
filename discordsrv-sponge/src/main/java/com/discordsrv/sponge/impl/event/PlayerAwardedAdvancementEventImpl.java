package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerAwardedEvent;

public class PlayerAwardedAdvancementEventImpl extends CancelableEvent implements PlayerAwardedEvent {

    private final String advancement;
    private final Player player;

    public PlayerAwardedAdvancementEventImpl(String advancement, Player player, boolean cancelled) {
        this.advancement = advancement;
        this.player = player;
        setCanceled(cancelled);
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
