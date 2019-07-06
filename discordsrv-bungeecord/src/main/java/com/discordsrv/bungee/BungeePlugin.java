package com.discordsrv.bungee;

import com.discordsrv.common.logging.Log;
import com.discordsrv.common.logging.Logger;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeePlugin extends Plugin implements Logger {

    @Override
    public void onEnable() {
        Log.use(this);
    }

    @Override
    public void onDisable() {

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
