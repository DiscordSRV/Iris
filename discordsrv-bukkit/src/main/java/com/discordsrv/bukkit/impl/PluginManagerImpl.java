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

package com.discordsrv.bukkit.impl;

import com.discordsrv.common.abstracted.PluginManager;
import org.bukkit.Bukkit;

import java.util.Arrays;
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
        return getPlugin(name, caseInsensitive)
                .filter(o -> ((org.bukkit.plugin.Plugin) o).isEnabled())
                .isPresent();
    }

    @Override
    public <Plugin> Optional<Plugin> getPlugin(String name) {
        return getPlugin(name, true);
    }

    @Override
    public <Plugin> Optional<Plugin> getPlugin(String name, boolean caseInsensitive) {
        Optional<org.bukkit.plugin.Plugin> optionalPlugin = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(p -> {
                    if (caseInsensitive) {
                        return p.getName().equalsIgnoreCase(name);
                    } else {
                        return p.getName().equals(name);
                    }
                })
                .findFirst();

        if (optionalPlugin.isPresent()) {
            Plugin plugin = (Plugin) optionalPlugin.get();
            return Optional.of(plugin);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, ?> getPlugins() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .collect(Collectors.toMap(org.bukkit.plugin.Plugin::getName, plugin -> plugin));
    }

}
