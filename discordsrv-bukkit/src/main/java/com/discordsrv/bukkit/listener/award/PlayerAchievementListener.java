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

package com.discordsrv.bukkit.listener.award;

import com.discordsrv.bukkit.BukkitPlugin;
import com.discordsrv.bukkit.impl.event.PlayerAwardedEventImpl;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayerAchievementListener implements Listener, EventExecutor {

    private final HandlerList handlerList;

    public PlayerAchievementListener() throws ClassNotFoundException {
        Class<?> achievementEventClass = Class.forName("org.bukkit.event.player.PlayerAchievementAwardedEvent");
        if (achievementEventClass.isAnnotationPresent(Deprecated.class)) throw new ClassNotFoundException();

        try {
            Method handlerMethod = Arrays.stream(achievementEventClass.getMethods())
                    .filter(m -> m.getParameterCount() == 0)
                    .filter(m -> m.getName().startsWith("get"))
                    .filter(m -> m.getReturnType() == HandlerList.class)
                    .filter(m -> Modifier.isStatic(m.getModifiers()))
                    .findFirst().orElseThrow(RuntimeException::new);
            handlerList = (HandlerList) handlerMethod.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void register() {
        handlerList.register(new RegisteredListener(
                this,
                this,
                EventPriority.MONITOR,
                BukkitPlugin.get(),
                false
        ));
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        if (!(event instanceof PlayerEvent) || !event.getClass().getName().contains("Achievement")) {
            Log.debug("Wrong event was sent to PlayerAchievementListener, ignoring");
            return;
        }

        try {
            Object achievement = event.getClass().getMethod("getAchievement").invoke(event);
            if (achievement == null) {
                Log.debug("PlayerAchievementListener received a null achievement, ignoring");
                return;
            }
            String achievementName = (String) achievement.getClass().getMethod("name").invoke(achievement);

            // turn "ACHIEVEMENT_NAME" into "Achievement Name"
            achievementName = Arrays.stream(achievementName.toLowerCase().split("_"))
                    .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" "));

            DiscordSRV.get().getEventBus().publish(new PlayerAwardedEventImpl((PlayerEvent) event, achievementName));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
