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

package com.discordsrv.bukkit;

import com.discordsrv.bukkit.impl.PluginManagerImpl;
import com.discordsrv.bukkit.impl.ServerImpl;
import com.discordsrv.bukkit.listener.PlayerAchievementListener;
import com.discordsrv.bukkit.listener.PlayerConnectionListener;
import com.discordsrv.bukkit.listener.PlayerDeathListener;
import com.discordsrv.bukkit.listener.award.PlayerAdvancementListener;
import com.discordsrv.bukkit.listener.chat.VanillaChatListener;
import com.discordsrv.common.Builder;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.common.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin implements Logger {

    private DiscordSRV srv;

    @Override
    public void onEnable() {
        Log.use(this);

        srv = new Builder()
                .usingPluginManager(new PluginManagerImpl())
                .usingServer(new ServerImpl())
                .build();

        Bukkit.getPluginManager().registerEvents(new VanillaChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerAchievementListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerAdvancementListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    @Override
    public void onDisable() {
        srv.shutdown();
    }

    @Override
    public void log(Log.LogLevel level, String message) {
        switch (level) {
            case INFO:
                getLogger().info(message);
                break;
            case WARN:
                getLogger().warning(message);
                break;
            case ERROR:
                getLogger().severe(message);
                break;
            case DEBUG:
                getLogger().info("[DEBUG] " + message);
                break;
        }
    }

    public static BukkitPlugin get() {
        return getPlugin(BukkitPlugin.class);
    }

}
