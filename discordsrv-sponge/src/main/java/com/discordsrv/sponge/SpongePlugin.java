package com.discordsrv.sponge;

import com.discordsrv.common.Builder;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.logging.Log;
import com.discordsrv.sponge.impl.PluginManagerImpl;
import com.discordsrv.sponge.impl.ServerImpl;
import com.discordsrv.sponge.listener.ChatListener;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(
        id = "discordsrv",
        name = "DiscordSRV",
        description = "The most powerful, configurable, open-source Discord to Minecraft bridging plugin available.",
        url = "https://discordsrv.com",
        authors = {
                "Scarsz"
        }
)
public class SpongePlugin implements com.discordsrv.common.logging.Logger {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Inject
    private Logger logger;

    private DiscordSRV srv;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Log.use(this);

        srv = new Builder()
                .usingPluginManager(new PluginManagerImpl())
                .usingServer(new ServerImpl())
                .build();

        Sponge.getEventManager().registerListeners(this, new ChatListener());
    }

    public SpongePlugin get() {
        return (SpongePlugin) Sponge.getPluginManager()
                .getPlugin("discordsrv").orElseThrow(RuntimeException::new)
                .getInstance().orElseThrow(RuntimeException::new);
    }
    public File getDataFolder() {
        return dataFolder;
    }
    public DiscordSRV getSrv() {
        return srv;
    }

    @Override
    public void log(Log.LogLevel level, String message) {
        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case DEBUG:
                logger.info("[DEBUG] " + message);
                break;
        }
    }

}
