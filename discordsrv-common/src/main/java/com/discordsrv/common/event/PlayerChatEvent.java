package com.discordsrv.common.event;

import com.discordsrv.common.abstracted.Player;

public interface PlayerChatEvent {

    String getMessage();
    Player getPlayer();

}
