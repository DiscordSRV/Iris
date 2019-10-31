package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import flavor.pie.boop.BoopableChannel;
import net.kyori.text.Component;
import org.spongepowered.api.text.channel.MessageChannel;

public class BoopChannel extends BaseChannel {

    private final MessageChannel channel;

    public BoopChannel(BaseChannel baseChannel, MessageChannel messageChannel) {
        super("boop_" + baseChannel.getName(), baseChannel.getTargetChannelIds());
        this.channel = messageChannel;
    }

    @Override
    public void sendToMinecraft(Component message) {
        new BoopableChannel(channel).send(SpongePlugin.serialize(message));
    }
}
