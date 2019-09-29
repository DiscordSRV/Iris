package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.event.PlayerDeathEvent;
import com.discordsrv.common.api.event.PublishCancelableEvent;
import com.discordsrv.sponge.SpongePlugin;
import lombok.Getter;
import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerDeathEventImpl extends PublishCancelableEvent implements PlayerDeathEvent {

    private final DestructEntityEvent.Death event;
    @Getter private final Component message;

    public PlayerDeathEventImpl(DestructEntityEvent.Death event) {
        this.event = event;
        this.message = SpongePlugin.get().serialize(event.getMessage());
        this.setPublishCanceled(event.isCancelled() || event.isMessageCancelled());
    }

    @Override
    public Player getPlayer() {
        Entity entity = event.getTargetEntity();
        if (!(entity instanceof Player)) throw new RuntimeException("Death event's target entity wasn't a Player");

        return (Player) entity;
    }
}
