package com.discordsrv.common;

import com.discordsrv.common.abstraction.Log;

public enum Platform {

    BUKKIT,
    BUNGEE,
    SPONGE;

    public static final Platform platform;

    static {
        platform = isBukkit()
                ? Platform.BUKKIT
                : isBungee()
                    ? Platform.BUNGEE
                    : isSponge()
                        ? Platform.SPONGE
                        : null;
        if (platform == null) throw new IllegalStateException("Unable to determine platform type");
        Log.debug("Platform = " + platform);
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
