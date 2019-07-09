package com.discordsrv.sponge.impl;

import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.abstracted.Server;
import org.spongepowered.api.Sponge;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerImpl implements Server {

    @Override
    public Set<Player> getOnlinePlayers() {
        return Sponge.getServer().getOnlinePlayers().stream()
                .map(PlayerImpl::new)
                .collect(Collectors.toSet());
    }

}
