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

package com.discordsrv.common.abstracted;

/**
 * Scheduler interface for DiscordSRV.
 *
 * This file reference ticks, there are 20 ticks per second
 */
public interface Scheduler {

    /**
     * Runs the provided {@link Runnable} on the main server thread.
     * <br><b>Keep in mind running a task sync (on the main thread) is usually not necessary with DiscordSRV
     *
     * @param runnable the task to execute
     */
    default void runTask(Runnable runnable) {
        runTaskLater(runnable, 0);
    }

    /**
     * Runs the provided {@link Runnable} after the specified amount of ticks on the main thread.
     * <br><b>Keep in mind running a task sync (on the main thread) is usually not necessary with DiscordSRV
     *
     * @param runnable the task to execute
     * @param delay ticks before executing the task
     */
    void runTaskLater(Runnable runnable, long delay);

    /**
     * Runs the specified {@link Runnable} every <i>period</i> ticks on the main thread. The first execution will be after <i>delay</i> ticks.
     * <br><b>Keep in mind running a task sync (on the main thread) is usually not necessary with DiscordSRV
     *
     * @param runnable the task to execute
     * @param delay ticks before executing the first task
     * @param period ticks in between running tasks
     */
    void runTaskRepeating(Runnable runnable, long delay, long period);

    /**
     * Runs the provided {@link Runnable} off of the main thread.
     *
     * @param runnable the task to execute
     */
    default void runTaskAsync(Runnable runnable) {
        runTaskAsyncLater(runnable, 0);
    }

    /**
     * Runs the provided {@link Runnable} after the specified amount of ticks off of the main thread.
     *
     * @param runnable the task to execute
     * @param delay ticks before executing the task
     */
    void runTaskAsyncLater(Runnable runnable, long delay);

    /**
     * Runs the specified {@link Runnable} every <i>period</i> ticks off of the main thread. The first execution will be after <i>delay</i> ticks.
     *
     * @param runnable the task to execute
     * @param delay ticks before executing the first task
     * @param period ticks in between running tasks
     */
    void runTaskAsyncRepeating(Runnable runnable, long delay, long period);

}
