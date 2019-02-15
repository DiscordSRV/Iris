package com.discordsrv.sponge;

import com.google.inject.Inject;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(
        id = "discordsrv",
        name = "Discordsrv",
        description = "The most powerful, configurable, open-source Discord to Minecraft bridging plugin available.",
        url = "https://discordsrv.com",
        authors = {
                "Scarsz"
        }
)
public class SpongePlugin {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

    }

    public File getDataFolder() {
        return dataFolder;
    }

}
