package com.discordsrv.sponge.impl.event;

import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.game.GenericChatMessageEvent;
import com.discordsrv.common.api.event.game.PublishCancelableEvent;
import lombok.Getter;
import net.kyori.text.Component;

public class GenericChatMessageEventImpl extends PublishCancelableEvent implements GenericChatMessageEvent {

    @Getter private final Channel channel;
    @Getter private final Component message;

    public GenericChatMessageEventImpl(Channel channel, Component message, boolean cancelled) {
        this.channel = channel;
        this.message = message;
        this.setWillPublish(!cancelled);
    }
}
