package com.discordsrv.sponge.event;

import com.discordsrv.sponge.SpongePlugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.util.Tristate;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MessageListener {
    
    public static final List<MessageEventListener> LISTENERS = new CopyOnWriteArrayList<>();

    @Listener(order = Order.POST)
    @IsCancelled(value = Tristate.UNDEFINED)
    public void onMessage(MessageChannelEvent event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(MessageChannelEvent event) {
        List<MessageEventListener> listeners = LISTENERS.stream()
                .sorted(Comparator.comparingInt(one -> one.priority))
                .collect(Collectors.toList());

        boolean handled = false;
        for (MessageEventListener listener : listeners) {
            if (!listener.receiveAfterHandled && handled) {
                continue;
            }

            if (listener.onEvent(event)) {
                handled = true;
            }
        }
    }
}
