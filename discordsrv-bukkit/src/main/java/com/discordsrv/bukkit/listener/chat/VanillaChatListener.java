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

package com.discordsrv.bukkit.listener.chat;

import com.discordsrv.bukkit.BukkitPlugin;
import com.discordsrv.bukkit.impl.event.PlayerChatEventImpl;
import com.discordsrv.common.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class VanillaChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        // this event isn't always asynchronous even though the event's name starts with "Async"
        // blame md_5 for that one (•_•)
        Bukkit.getScheduler().runTaskAsynchronously(BukkitPlugin.get(), () -> handle(event));
    }

    private void handle(AsyncPlayerChatEvent event) {
        DiscordSRV.get().getEventBus().publish(new PlayerChatEventImpl(event, "global"));
    }

}
