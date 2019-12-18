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

package com.discordsrv.bukkit.impl;

import com.discordsrv.bukkit.BukkitPlugin;
import com.discordsrv.common.abstracted.Scheduler;
import org.bukkit.Bukkit;

public class SchedulerImpl implements Scheduler {

    @Override
    public void runTaskLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(BukkitPlugin.get(), runnable, delay);
    }

    @Override
    public void runTaskRepeating(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(BukkitPlugin.get(), runnable, delay, period);
    }

    @Override
    public void runTaskAsyncLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitPlugin.get(), runnable, delay);
    }

    @Override
    public void runTaskAsyncRepeating(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BukkitPlugin.get(), runnable, delay, period);
    }

}
