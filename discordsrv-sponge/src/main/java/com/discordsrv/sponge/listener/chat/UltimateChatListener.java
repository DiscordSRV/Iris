package com.discordsrv.sponge.listener.chat;

import br.net.fabiozumbi12.UltimateChat.Sponge.API.SendChannelMessageEvent;
import br.net.fabiozumbi12.UltimateChat.Sponge.UCChannel;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.sponge.SpongePlugin;
import com.discordsrv.sponge.impl.event.PlayerChatEventImpl;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

public class UltimateChatListener {

    @Listener(order = Order.POST)
    public void onSendChannelMessage(SendChannelMessageEvent event) {
        SpongePlugin.get().getAsyncExecutor().execute(() -> handle(event));
    }

    private void handle(SendChannelMessageEvent event) {
        UCChannel channel = event.getChannel();

        SpongePlugin.get().getChannelManager().getChannel(channel.getName()).ifPresent(c ->
                DiscordSRV.get().getEventBus().publish(new PlayerChatEventImpl(event, event.getMessage(), event.isCancelled(), c))
        );
    }
}
