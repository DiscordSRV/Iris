package com.discordsrv.sponge.listener.message;

import com.discordsrv.common.api.ListenerPriority;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.sponge.event.SpongeMessageEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class GenericMessageListener {

    @Subscribe(priority = ListenerPriority.HIGH)
    public void onMessage(SpongeMessageEvent event) {
        if (event.isHandled()) return;
        handle(event.getEvent());
        event.setHandled(true);
    }

    private void handle(MessageChannelEvent event) {
        // TODO: event? (This includes any MessageChannelEvent that wasn't handled by something else)
    }
}
