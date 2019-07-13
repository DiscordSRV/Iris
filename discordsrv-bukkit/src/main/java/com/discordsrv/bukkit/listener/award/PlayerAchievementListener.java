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

import com.discordsrv.bukkit.impl.event.PlayerAwardedAdvancementEventImpl;
import com.discordsrv.common.DiscordSRV;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class PlayerAchievementListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent event) {
        if (event.getAchievement() == null) return;

        // turn "ACHIEVEMENT_NAME" into "Achievement Name"
        String name = Arrays.stream(event.getAchievement().name().toLowerCase().split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));

        DiscordSRV.get().getEventBus().publish(new PlayerAwardedAdvancementEventImpl(name, event.getPlayer()));
    }

}
