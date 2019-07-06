package com.discordsrv.bukkit.impl;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.Server;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerImpl implements Server {

    @Override
    public Set<Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .map(PlayerImpl::new)
                .collect(Collectors.toSet());
    }

}
