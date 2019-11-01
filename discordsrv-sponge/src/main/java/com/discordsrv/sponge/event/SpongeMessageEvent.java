package com.discordsrv.sponge.event;

import com.discordsrv.common.api.event.discord.HandledEvent;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.api.event.message.MessageChannelEvent;

/**
 * Message event class for DiscordSRV-Sponge.
 * Runs on Sponge's POST {@link org.spongepowered.api.event.Order Order} and includes cancelled events.
 * Always async.
 */
public class SpongeMessageEvent implements HandledEvent {

    @Getter @Setter private boolean handled = false;
    @Getter private final MessageChannelEvent event;

    public SpongeMessageEvent(MessageChannelEvent event) {
        this.event = event;
    }
}
