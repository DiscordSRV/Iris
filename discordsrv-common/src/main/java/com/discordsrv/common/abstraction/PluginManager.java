package com.discordsrv.common.abstraction;

import com.discordsrv.common.Platform;

import java.util.Optional;

import static com.discordsrv.common.Platform.platform;
import static org.joor.Reflect.on;

public class PluginManager {

    private static final Object manager;

    static {
        manager = platform == Platform.BUKKIT
                ? on("org.bukkit.Bukkit").call("getPluginManager").get()
                : platform == Platform.BUNGEE
                    ? on("org.md_5.bungee.api.ProxyServer").call("getPluginManager").get()
                    : platform == Platform.SPONGE
                        ? on("org.spongepowered.api.Sponge").call("getPluginManager").get()
                        : null;
        if (manager instanceof Optional) {
            on(PluginManager.class).set("manager", ((Optional) manager).isPresent()
                    ? ((Optional) manager).get()
                    : null
            );
        }
    }

    /**
     * Get the current platform's plugin manager instance
     */
    public static Object getManager() {
        return manager;
    }

    /**
     * Convenience method for obtaining the DiscordSRV plugin instance via {@link #getPlugin(String)}
     */
    public static Object getPlugin() {
        return getPlugin("DiscordSRV");
    }

    /**
     * Get the DiscordSRV plugin from this platform's plugin manager instance
     */
    public static Object getPlugin(String plugin) {
        return on(manager).call("getPlugin", platform != Platform.SPONGE ? plugin : plugin.toLowerCase()).get();
    }

}
