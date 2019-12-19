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

package com.discordsrv.common.listener.game;

import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.Text;
import com.discordsrv.common.abstracted.Player;
import com.discordsrv.common.api.ListenerPriority;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.api.event.game.PlayerDeathEvent;
import com.discordsrv.common.logging.Log;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class PlayerDeathListener {

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void onChat(PlayerDeathEvent event, Player player) {
        Log.debug("Received " + (!event.willPublish() ? "NON-PUBLISHING " : "") + "death event: " + event.getPlayer().getName() + " -> " + Text.asPlain(event.getMessage()));
        if (!event.willPublish()) return;

        DiscordSRV.get().getChannelManager().getChannel("deaths").ifPresent(c ->
                c.sendToDiscord(new EmbedBuilder()
                        .setColor(Color.DARK_GRAY)
                        .setTitle(Text.asPlain(event.getMessage()))
                        .setFooter("\u200B", "https://crafatar.com/avatars/{uuid}?size=48&overlay".replace("{uuid}", player.getUuid().toString()))
                        .build()
                )
        );
    }

}
