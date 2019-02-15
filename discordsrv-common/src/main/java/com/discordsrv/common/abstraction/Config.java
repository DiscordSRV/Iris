package com.discordsrv.common.abstraction;

import java.io.File;

import static org.joor.Reflect.on;

public class Config {

    private static final File dataDirectory;

    static {
        dataDirectory = on(PluginManager.getPlugin()).call("getDataFolder").get();
        if (!dataDirectory.exists() && !dataDirectory.mkdir()) {
            Log.error("Failed to create data folder @ " + dataDirectory.getPath() + "! Check your file permissions.");
        }

        //TODO figure out how Configurate works
    }

}
