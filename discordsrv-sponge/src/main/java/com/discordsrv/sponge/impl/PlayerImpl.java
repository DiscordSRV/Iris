package com.discordsrv.sponge.impl;

import com.discordsrv.common.abstracted.Player;
import org.spongepowered.api.data.key.Keys;

import java.util.UUID;

public class PlayerImpl implements Player {

    private final org.spongepowered.api.entity.living.player.Player player;

    public PlayerImpl(org.spongepowered.api.entity.living.player.Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayNameData().displayName().get().toPlain();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public boolean isVanished() {
        return player.get(Keys.VANISH).orElse(false);
    }

}
