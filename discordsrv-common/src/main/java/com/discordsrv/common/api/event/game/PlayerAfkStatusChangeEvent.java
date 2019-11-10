package com.discordsrv.common.api.event.game;

import com.discordsrv.common.abstracted.channel.Channel;
import net.kyori.text.Component;

import javax.annotation.Nullable;

public interface PlayerAfkStatusChangeEvent extends PublishCancelable, PlayerEvent {

    Channel getChannel();
    boolean isGoingAfk();
    @Nullable Component getMessage();
}
