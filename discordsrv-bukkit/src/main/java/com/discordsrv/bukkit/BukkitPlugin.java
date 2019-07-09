package com.discordsrv.bukkit;

import com.discordsrv.bukkit.impl.PluginManagerImpl;
import com.discordsrv.bukkit.impl.ServerImpl;
import com.discordsrv.common.Builder;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.common.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin implements Logger {

    private DiscordSRV srv;

    @Override
    public void onEnable() {
        Log.use(this);

        srv = new Builder()
                .usingPluginManager(new PluginManagerImpl())
                .usingServer(new ServerImpl())
                .build();
    }

    @Override
    public void onDisable() {
        srv.shutdown();
    }

    @Override
    public void log(Log.LogLevel level, String message) {
        switch (level) {
            case INFO:
                getLogger().info(message);
                break;
            case WARN:
                getLogger().warning(message);
                break;
            case ERROR:
                getLogger().severe(message);
                break;
            case DEBUG:
                getLogger().info("[DEBUG] " + message);
                break;
        }
    }

}
