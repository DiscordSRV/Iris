package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import net.kyori.text.Component;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;
import java.util.Set;

public class NucleusHelpOpChannel extends BaseChannel implements MessageChannelTranslator {

    public NucleusHelpOpChannel(Set<String> targetChannelIds) {
        super("helpop", targetChannelIds);
    }

    @Override
    public void sendToMinecraft(Component message) {} // one way channel, no Discord -> Minecraft

    @Override
    public Optional<BaseChannel> translate(MessageChannel messageChannel) {
        if (messageChannel.getClass().getName().startsWith("io.github.nucleuspowered.nucleus.modules.message.HelpOpMessageChannel")) { // not included in api
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
