package com.discordsrv.common.api.event.game;

import com.discordsrv.common.abstracted.channel.Channel;
import com.discordsrv.common.api.event.Event;
import net.kyori.text.Component;

public interface GenericChatMessageEvent extends PublishCancelable, Event {

    Channel getChannel();
    Component getMessage();
}
