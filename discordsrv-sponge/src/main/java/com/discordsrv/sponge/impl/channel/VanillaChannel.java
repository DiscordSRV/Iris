package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import net.kyori.text.Component;
import org.spongepowered.api.Sponge;

import java.util.Set;

public class VanillaChannel extends BaseChannel {

    public VanillaChannel(Set<String> targetChannelIds) {
        super("global", targetChannelIds);
    }

    @Override
    public void sendToMinecraft(Component message) {
        Sponge.getServer().getBroadcastChannel().send(SpongePlugin.get().serialize(message));
    }
}
