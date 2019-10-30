package com.discordsrv.sponge.event;

import org.spongepowered.api.event.message.MessageChannelEvent;

/**
 * Internal event listener class for DiscordSRV-Sponge.
 * Runs on Sponge's POST {@link org.spongepowered.api.event.Order Order} and includes cancelled events.
 * Always async.
 */
public abstract class MessageEventListener {

    /**
     * Should this event listener receive events even after they are handled
     */
    public boolean receiveAfterHandled = true;

    /**
     * The priority of this event listener. Order: positive -> negative (important if receiveAfterHandled is false)
     */
    public byte priority = 0;

    /**
     * Event listener.
     *
     * @param event The event
     * @return if this even listener processed this event
     */
    public abstract boolean onEvent(MessageChannelEvent event);
}
