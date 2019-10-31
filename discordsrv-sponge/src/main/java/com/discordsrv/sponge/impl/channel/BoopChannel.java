package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import flavor.pie.boop.BoopableChannel;
import net.kyori.text.Component;
import org.spongepowered.api.text.channel.MessageChannel;

public class BoopChannel extends BaseChannel {

    private final BoopableChannel channel;

    public BoopChannel(BaseChannel baseChannel, MessageChannel messageChannel) {
        super("boop_" + baseChannel.getName(), baseChannel.getTargetChannelIds());
        this.channel = new BoopableChannel(messageChannel);
    }

    @Override
    public void sendToMinecraft(Component message) {
        channel.send(SpongePlugin.serialize(message));
    }
}
