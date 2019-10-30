package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;

public interface MessageChannelTranslator {

    Optional<BaseChannel> translate(MessageChannel messageChannel);
}
