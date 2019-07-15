package com.discordsrv.bungee.impl;

import com.discordsrv.common.abstracted.Player;
import net.kyori.text.Component;
import net.kyori.text.adapter.bungeecord.TextAdapter;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class PlayerImpl implements Player {

    private final ProxiedPlayer proxiedPlayer;

    public PlayerImpl(ProxiedPlayer proxiedPlayer) {
        this.proxiedPlayer = proxiedPlayer;
    }

    @Override
    public String getName() {
        return proxiedPlayer.getName();
    }

    @Override
    public Component getDisplayName() {
        return LegacyComponentSerializer.INSTANCE.deserialize(proxiedPlayer.getDisplayName());
    }

    @Override
    public UUID getUuid() {
        return proxiedPlayer.getUniqueId();
    }

    @Override
    public boolean isVanished() {
        return false; // TODO?
    }

    @Override
    public void sendMessage(Component component) {
        TextAdapter.sendComponent(proxiedPlayer, component);
    }
}
