/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.discordsrv.common;

import com.discordsrv.common.abstracted.PluginManager;
import com.discordsrv.common.abstracted.Server;
import com.discordsrv.common.api.EventBus;
import com.discordsrv.common.listener.PlayerChatListener;

public class DiscordSRV {

    private static DiscordSRV INSTANCE;

    private final EventBus eventBus;
    private final PluginManager pluginManager;
    private final Server server;

    DiscordSRV(PluginManager pluginManager, Server server) {
        DiscordSRV.INSTANCE = this;
        this.eventBus = new EventBus();
        this.pluginManager = pluginManager;
        this.server = server;

        registerListeners();
    }

    private void registerListeners() {
        if (eventBus.getListener(PlayerChatListener.class) == null) eventBus.subscribe(new PlayerChatListener());
    }

    public void shutdown() {

    }

    public static DiscordSRV get() {
        return INSTANCE;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public Server getServer() {
        return server;
    }

}
