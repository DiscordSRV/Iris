package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.CancelableEvent;
import com.discordsrv.common.api.event.PlayerDeathEvent;
import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerDeathEventImpl extends CancelableEvent implements PlayerDeathEvent {

    private final DestructEntityEvent.Death event;
    private final Component message;

    public PlayerDeathEventImpl(DestructEntityEvent.Death event) {
        this.event = event;
        this.message = GsonComponentSerializer.INSTANCE.deserialize(TextSerializers.JSON.serialize(event.getMessage()));
        setCancelled(event.isCancelled() || event.isMessageCancelled());
    }

    @Override
    public Component getMessage() {
        return message;
    }

    @Override
    public Player getPlayer() {
        Entity entity = event.getTargetEntity();
        if (!(entity instanceof Player)) throw new RuntimeException("Death event's target entity wasn't a Player");

        return (Player) entity;
    }
}
