package com.discordsrv.bungee.impl;

import com.discordsrv.common.abstracted.PluginManager;
import net.md_5.bungee.api.ProxyServer;

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
        return getPlugin(name, caseInsensitive).isPresent();
    }

    @Override
    public <Plugin> Optional<Plugin> getPlugin(String name) {
        return getPlugin(name, true);
    }

    @Override
    public <Plugin> Optional<Plugin> getPlugin(String name, boolean caseInsensitive) {
        Optional<net.md_5.bungee.api.plugin.Plugin> optionalPlugin = ProxyServer.getInstance().getPluginManager().getPlugins().stream()
                .filter(p -> {
                    if (caseInsensitive) {
                        return p.getDescription().getName().equalsIgnoreCase(name);
                    } else {
                        return p.getDescription().getName().equals(name);
                    }
                })
                .findFirst();

        return optionalPlugin.map(plugin -> (Plugin) plugin);
    }

    @Override
    public Map<String, ?> getPlugins() {
        return ProxyServer.getInstance().getPluginManager().getPlugins().stream()
                .collect(Collectors.toMap(plugin -> plugin.getDescription().getName(), plugin -> plugin));
    }
}
