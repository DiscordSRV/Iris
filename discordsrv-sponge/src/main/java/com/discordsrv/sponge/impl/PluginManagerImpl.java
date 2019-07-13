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

import com.discordsrv.common.abstracted.PluginManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class PluginManagerImpl implements PluginManager {


    @Override
    public boolean pluginIsEnabled(String name) {
        return pluginIsEnabled(name, true);
    }

    @Override
    public boolean pluginIsEnabled(String name, boolean caseInsensitive) {
        return true; //TODO?
    }

    @Override
    public <T> Optional<T> getPlugin(String name) {
        return getPlugin(name, true);
    }

    @Override
    public <T> Optional<T> getPlugin(String name, boolean caseInsensitive) {
        PluginContainer container = Sponge.getPluginManager().getPlugins().stream()
                .filter(p -> {
                    if (caseInsensitive) {
                        return p.getName().equalsIgnoreCase(name);
                    } else {
                        return p.getName().equals(name);
                    }
                }).findFirst().orElse(null);

        if (container != null) {
            return (Optional<T>) container.getInstance();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, ?> getPlugins() {
        return Sponge.getPluginManager().getPlugins().stream()
                .collect(Collectors.toMap(PluginContainer::getName, plugin -> plugin));
    }

}
