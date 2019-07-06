package com.discordsrv.bukkit.impl;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerImpl implements com.discordsrv.common.abstracted.Player {

    private final Player player;

    public PlayerImpl(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

}
