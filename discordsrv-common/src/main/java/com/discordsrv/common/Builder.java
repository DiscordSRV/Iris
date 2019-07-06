package com.discordsrv.common;

import com.discordsrv.common.abstracted.PluginManager;
import com.discordsrv.common.abstracted.Server;

public class Builder {

    private PluginManager pluginManager;
    private Server server;

    public Builder usingPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
        return this;
    }

    public Builder usingServer(Server server) {
        this.server = server;
        return this;
    }

    public DiscordSRV build() {
        return new DiscordSRV(
                this.pluginManager,
                this.server
        );
    }
}
