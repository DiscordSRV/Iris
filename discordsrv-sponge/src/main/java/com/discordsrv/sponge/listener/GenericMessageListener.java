package com.discordsrv.sponge.listener;

import com.discordsrv.sponge.event.MessageEventListener;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class GenericMessageListener extends MessageEventListener {

    public GenericMessageListener() {
        receiveAfterHandled = false;
        priority = -10;
    }

    @Override
    public boolean onEvent(MessageChannelEvent event) {
        handle(event);
        return true;
    }

    private void handle(MessageChannelEvent event) {
        // TODO: event? (This includes any MessageChannelEvent that wasn't handled by something else)
    }
}
