package com.discordsrv.common;

import com.discordsrv.common.abstracted.PluginManager;
import com.discordsrv.common.abstracted.Server;

public class DiscordSRV {

    private final PluginManager pluginManager;
    private final Server server;

    public DiscordSRV(PluginManager pluginManager, Server server) {
        this.pluginManager = pluginManager;
        this.server = server;
    }

    public void shutdown() {

    }

}
