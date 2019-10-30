package com.discordsrv.sponge.impl.channel;

import com.discordsrv.common.abstracted.channel.BaseChannel;
import com.discordsrv.sponge.SpongePlugin;
import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import io.github.nucleuspowered.nucleus.api.chat.NucleusChatChannel;
import net.kyori.text.Component;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Optional;
import java.util.Set;

public class NucleusStaffChatChannel extends BaseChannel implements MessageChannelTranslator {

    public NucleusStaffChatChannel(Set<String> targetChannelIds) {
        super("staff", targetChannelIds);
    }

    @Override
    public void sendToMinecraft(Component message) {
        NucleusAPI.getStaffChatService().ifPresent(service -> service.getStaffChat().send(SpongePlugin.serialize(message)));
    }

    @Override
    public Optional<BaseChannel> translate(MessageChannel messageChannel) {
        if (messageChannel instanceof NucleusChatChannel.StaffChat) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
