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

package com.discordsrv.common.abstracted.channel;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.text.Component;

import java.util.Set;
import java.util.function.Consumer;

public interface Channel {

    String getName();
    Set<String> getTargetChannelIds();
    Set<TextChannel> getTargetChannels();

    void sendToMinecraft(String message);
    void sendToMinecraft(Component message);
    void sendToDiscord(String message);
    void sendToDiscord(String message, Consumer<? super Message> success);
    void sendToDiscord(String message, Consumer<? super Message> success, Consumer<? super Throwable> fail);
    void sendToDiscord(Message message);
    void sendToDiscord(Message message, Consumer<? super Message> success);
    void sendToDiscord(Message message, Consumer<? super Message> success, Consumer<? super Throwable> fail);
    Set<Message> sendToDiscordNow(String message);
    Set<Message> sendToDiscordNow(Message message);

}
