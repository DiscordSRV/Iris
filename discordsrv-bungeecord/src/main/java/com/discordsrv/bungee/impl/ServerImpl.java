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

    @Override
    public void executeConsoleCommand(String command) {
        ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
    }

}
