package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.game.PlayerDeathEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import lombok.Getter;
import net.kyori.text.Component;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class PlayerDeathEventImpl extends PublishCancelableEvent implements PlayerDeathEvent {

    private final DestructEntityEvent.Death event;
    @Getter private final Component message;

    public PlayerDeathEventImpl(DestructEntityEvent.Death event) {
        this.event = event;
        this.message = SpongePlugin.get().serialize(event.getMessage());
        this.setWillPublish(!event.isCancelled() && !event.isMessageCancelled());
    }

    @Override
    public Player getPlayer() {
        Entity entity = event.getTargetEntity();
        if (!(entity instanceof Player)) throw new RuntimeException("Death event's target entity wasn't a Player");

        return (Player) entity;
    }
}
