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

package com.discordsrv.sponge.impl;

import com.discordsrv.common.abstracted.Scheduler;
import com.discordsrv.sponge.SpongePlugin;

import java.util.concurrent.TimeUnit;

public class SchedulerImpl implements Scheduler {

    @Override
    public void runTask(Runnable runnable) {
        SpongePlugin.get().getSyncExecutor().execute(runnable);
    }

    @Override
    public void runTaskLater(Runnable runnable, long delay) {
        SpongePlugin.get().getSyncExecutor().schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskRepeating(Runnable runnable, long delay, long period) {
        SpongePlugin.get().getSyncExecutor().scheduleAtFixedRate(runnable, delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskAsync(Runnable runnable) {
        SpongePlugin.get().getAsyncExecutor().execute(runnable);
    }

    @Override
    public void runTaskAsyncLater(Runnable runnable, long delay) {
        SpongePlugin.get().getAsyncExecutor().schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskAsyncRepeating(Runnable runnable, long delay, long period) {
        SpongePlugin.get().getAsyncExecutor().scheduleAtFixedRate(runnable, delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }
}
