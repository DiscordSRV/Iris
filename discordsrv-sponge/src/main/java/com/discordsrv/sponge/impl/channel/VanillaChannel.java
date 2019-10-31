package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import net.kyori.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;
import java.util.Set;

public class VanillaChannel extends BaseChannel implements MessageChannelTranslator {

    public VanillaChannel(Set<String> targetChannelIds) {
        super("global", targetChannelIds);
    }

    @Override
    public void sendToMinecraft(Component message) {
        MessageChannel.TO_ALL.send(SpongePlugin.serialize(message));
    }

    @Override
    public Optional<BaseChannel> translate(MessageChannel messageChannel) {
        if (messageChannel.getClass().getName().startsWith("org.spongepowered.api.text.channel.MessageChannel")) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
