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

package com.discordsrv.common.api;

/**
 * <p>Completely inspired by the Bukkit API's EventPriority system</p>
 * <p>Event priorities mean the same as Bukkit's; it's in a separate enum to prevent
 * depending on Bukkit classes when they might not be available</p>
 * <p>Defaults to {@link #NORMAL} in {@link Subscribe} annotations where it's not specifically set</p>
 */
public enum ListenerPriority {

    /**
     * Event call is of very low importance and should be ran first, to allow
     * other plugins to further customize the outcome.
     */
    LOWEST,

    /**
     * Event call is of low importance.
     */
    LOW,

    /**
     * Event call is neither important nor unimportant, and may be ran
     * normally.
     */
    NORMAL,

    /**
     * Event call is of high importance.
     */
    HIGH,

    /**
     * Event call is critical and must have the final say in what happens
     * to the event.
     */
    HIGHEST,

    /**
     * Event is listened to purely for monitoring the outcome of an event.
     * No modifications to the event should be made under this priority.
     */
    MONITOR;

}