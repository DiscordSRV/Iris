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

import com.discordsrv.common.api.event.discord.GuildMessageProcessingEvent;
import com.discordsrv.common.api.event.discord.HandledEvent;
import com.discordsrv.common.api.event.game.PublishCancelable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * <p>Marks a {@link Method} as one that should receive DiscordSRV API events</p>
 * <p>Functionally identical to Bukkit's EventHandler annotation</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

    /**
     * The priority of which the event should be ran. Multiple listeners with the same priority will be ran haphazardly.
     * @return the priority of the event listener method
     */
    ListenerPriority priority() default ListenerPriority.NORMAL;

    /**
     * <p>Whether or not this listener should be ran if the event was already handled by something else or not</p>
     * <p>In the case of a {@link HandledEvent}, events that have been handled will be ignored by this listener.</p>
     * <p>In the case of a {@link PublishCancelable}, events that shouldn't be published will be ignored by this listener.</p>
     * @return whether or not this listener will accept already handled events
     */
    boolean ignoring() default false;

    /**
     * <p>Whether or not this listener will accept Discord events from itself.</p>
     * <p>Only applicable for listeners accepting events from Discord, such as {@link GuildMessageProcessingEvent}.</p>
     */
    boolean ignoreSelf() default true;

    /**
     * <p>Whether or not this listener will accept Discord events from webhooks.</p>
     * <p>Only applicable for listeners accepting events from Discord, such as {@link GuildMessageProcessingEvent}.</p>
     */
    boolean ignoreWebhooks() default true;

}
