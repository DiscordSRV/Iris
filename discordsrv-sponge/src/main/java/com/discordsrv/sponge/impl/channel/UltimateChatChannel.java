package com.discordsrv.sponge.impl.channel;

import br.net.fabiozumbi12.UltimateChat.Sponge.UCChannel;
import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import net.kyori.text.Component;
import org.spongepowered.api.Sponge;

import java.util.Set;

public class UltimateChatChannel extends BaseChannel {

    private final UCChannel channel;

    public UltimateChatChannel(UCChannel channel, Set<String> targetChannelIds) {
        super(channel.getName(), targetChannelIds);
        this.channel = channel;
    }

    @Override
    public void sendToMinecraft(Component message) {
        channel.sendMessage(Sponge.getServer().getConsole(), SpongePlugin.serialize(message), false); // direct: probably not important
    }
}
