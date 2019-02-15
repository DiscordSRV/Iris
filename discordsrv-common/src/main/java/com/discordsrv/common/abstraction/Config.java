package com.discordsrv.common.abstraction;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.joor.Reflect.on;

public class Config {

    private static CommentedConfigurationNode CONFIG;
    private static RootCategory ROOT_CATEGORY;
    private static final ConfigurationLoader<CommentedConfigurationNode> CONFIG_LOADER;
    private static final File DATA_DIRECTORY;
    private static final String HEADER = String.join("\n", Arrays.asList(
            "DiscordSRV Iris - The most powerful, configurable, open-source Discord to Minecraft bridging plugin available.",
            "https://discordsrv.com / https://scarsz.me/discord",
            "v2.0-SNAPSHOT"
    ));

    static {
        DATA_DIRECTORY = on(PluginManager.getPlugin()).call("getDataFolder").get();
        if (!DATA_DIRECTORY.exists() && !DATA_DIRECTORY.mkdir()) {
            Log.error("Failed to create data folder @ " + DATA_DIRECTORY.getPath() + "! Check your file permissions.");
            throw new RuntimeException();
        } else {
            CONFIG_LOADER = HoconConfigurationLoader.builder().setPath(Paths.get(DATA_DIRECTORY.getAbsolutePath(), "config.conf")).build();
            try {
                CONFIG = CONFIG_LOADER.load(ConfigurationOptions.defaults()
                        .setShouldCopyDefaults(true)
                        .setHeader(HEADER)
                );
                ROOT_CATEGORY = CONFIG.getValue(TypeToken.of(RootCategory.class), new RootCategory());
            } catch (IOException | ObjectMappingException e) {
                Log.error("Failed to load config...");
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try {
            CONFIG.setValue(TypeToken.of(RootCategory.class), ROOT_CATEGORY);
            CONFIG_LOADER.save(CONFIG);
        } catch (ObjectMappingException | IOException e) {
            Log.error("Failed to save config...");
            e.printStackTrace();
        }
    }

    @ConfigSerializable
    private static class RootCategory {

        @Setting(comment = "Debug options for DiscordSRV. Unless you have a reason to touch these, don't.")
        DebugCategory debug;

        @ConfigSerializable
        private static class DebugCategory {

            @Setting(comment = "Debug mode")
            int mode = 0;

        }


    }

}
