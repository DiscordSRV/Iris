package com.discordsrv.bungee.impl;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.Server;
import net.md_5.bungee.api.ProxyServer;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerImpl implements Server {

    @Override
    public Set<Player> getOnlinePlayers() {
        return ProxyServer.getInstance().getPlayers().stream()
                .map(PlayerImpl::new)
                .collect(Collectors.toSet());
    }
}
