package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerDeathEvent;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class PlayerDeathEventImpl extends CancelableEvent implements PlayerDeathEvent {

    private final DestructEntityEvent.Death event;

    public PlayerDeathEventImpl(DestructEntityEvent.Death event) {
        this.event = event;
        setCancelled(event.isCancelled() || event.isMessageCancelled());
    }

    @Override
    public String getMessage() {
        return event.getMessage().toPlain();
    }

    @Override
    public Player getPlayer() {
        Entity entity = event.getTargetEntity();
        if (!(entity instanceof Player)) throw new RuntimeException("Death event's target entity wasn't a Player");

        return (Player) entity;
    }
}
