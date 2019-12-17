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

import com.discordsrv.common.Text;
import com.discordsrv.common.api.ListenerPriority;
import com.discordsrv.common.api.Subscribe;
import com.discordsrv.common.api.event.discord.GuildMessageProcessingEvent;
import com.discordsrv.common.logging.Log;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.TextColor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public abstract class BaseChannelManager implements ChannelManager {

    @Getter private Set<BaseChannel> channels = new HashSet<>();

    @Subscribe(priority = ListenerPriority.MONITOR, ignoring = true)
    public void onGuildMessageProcessingEventEvent(GuildMessageProcessingEvent event) {
        Log.debug("Processing message from " + event.getAuthor() + " > " + event.getMessage().getContentRaw());

        // set the event to be handled as the channel manager is the final stop
        event.setHandled(true);

        //TODO #getMember == null when the message is from a webhook. maybe investigate accepting webhook messages
        if (event.getMember() == null || event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        BaseChannel destinationChannel = getChannel(event.getChannel().getId()).orElse(null);
        if (destinationChannel == null) {
            Log.debug("No destination channel found for message, skipping");
            return;
        }

        //TODO reserialization

        TextComponent.Builder whatWasSaid = TextComponent.builder(event.getMessage().getContentDisplay());

        // handle attachments //TODO add config option to turn off translating attachments
        if (event.getMessage().getAttachments().size() > 0) {
            // add a space if there was more to this message than just attachments
            if (StringUtils.isNotBlank(Text.asPlain(whatWasSaid.build()))) whatWasSaid.append(TextComponent.space());

            TextComponent leftArrow = TextComponent.of("<").color(TextColor.WHITE);
            TextComponent rightArrow = TextComponent.of(">").color(TextColor.WHITE);

            for (Iterator<Message.Attachment> i = event.getMessage().getAttachments().iterator(); i.hasNext();) {
                Message.Attachment attachment = i.next();
                String extension = (attachment.isImage() ? attachment.getWidth() + "x" + attachment.getHeight() + " " : "")
                        + "." + FilenameUtils.getExtension(attachment.getFileName());
                TextComponent file = TextComponent.of(attachment.getFileName()).color(TextColor.AQUA);
                TextComponent hover = TextComponent
                        .of("File: ").color(TextColor.AQUA).append(TextComponent.of(attachment.getFileName()).color(TextColor.WHITE))
                        .append(TextComponent.of("\nType: ")).color(TextColor.AQUA).append(TextComponent.of(extension).color(TextColor.WHITE))
                        .append(TextComponent.of("\n\n"))
                        .append(TextComponent.of("This is a user-uploaded file, sent through Discord. " +
                                "Open executable files at your own risk.").color(TextColor.RED));

                whatWasSaid.append(TextComponent.builder().append(leftArrow).append(file).append(rightArrow)
                        .clickEvent(ClickEvent.openUrl(attachment.getUrl()))
                        .hoverEvent(HoverEvent.showText(hover))
                );

                if (i.hasNext()) whatWasSaid.append(TextComponent.space());
            }
        }

        TextComponent discordName = TextComponent.of(event.getMember().getEffectiveName());
        TextComponent topRole = TextComponent.of(event.getMember().getRoles().get(0).getName());

        TextComponent.Builder content = TextComponent.builder();
        content.append(topRole.append(TextComponent.space()).append(discordName));
        content.append(TextComponent.of(" > ").color(TextColor.DARK_GRAY));
        content.append(whatWasSaid);
        destinationChannel.sendToMinecraft(content.build());
    }

    @Override
    public Optional<BaseChannel> getChannel(String target) {
        return channels.stream()
                .filter(channel -> channel.getName().equalsIgnoreCase(target) ||
                        channel.getTargetChannelIds().stream().anyMatch(s -> s.equals(target)))
                .findFirst();
    }

}
