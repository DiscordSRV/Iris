package com.discordsrv.common;

import com.discordsrv.common.logging.Log;

public enum Platform {

    BUKKIT,
    BUNGEE,
    SPONGE;

    public static final Platform PLATFORM;

    static {
        PLATFORM = isBukkit()
                ? Platform.BUKKIT
                : isBungee()
                    ? Platform.BUNGEE
                    : isSponge()
                        ? Platform.SPONGE
                        : null;
        if (PLATFORM == null) throw new IllegalStateException("Unable to determine platform type");
        Log.debug("Platform = " + PLATFORM);
    }

    private static boolean isBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isBungee() {
        try {
            Class.forName("net.md_5.bungee.api.ProxyServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isSponge() {
        try {
            Class.forName("org.spongepowered.api.Sponge");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
