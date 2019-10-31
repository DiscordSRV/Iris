package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import flavor.pie.boop.BoopableChannel;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public class BoopChannelTranslator implements MessageChannelTranslator {

    @Override
    public Optional<BaseChannel> translate(MessageChannel messageChannel) {
        if (messageChannel instanceof BoopableChannel) {
            BoopableChannel boopableChannel = (BoopableChannel) messageChannel;
            MessageChannel originalChannel = boopableChannel.getOriginalChannel();
            BaseChannel baseChannel = SpongePlugin.get().getChannelManager().getChannel(originalChannel).orElse(null);
            if (baseChannel != null) {
                return Optional.of(new BoopChannel(baseChannel, originalChannel));
            }
        }
        return Optional.empty();
    }
}
