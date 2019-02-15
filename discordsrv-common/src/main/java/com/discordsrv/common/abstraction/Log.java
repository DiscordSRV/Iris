package com.discordsrv.common.abstraction;

import static org.joor.Reflect.on;

public class Log {

    public static Object getLogger() {
        return on(PluginManager.getPlugin()).call("getLogger").get();
    }

    public static void log(String level, String message) {
        on(getLogger()).call(level, message);
    }

    public static void info(String message) {
        on(getLogger()).call("info", message);
    }

    public static void warn(String message) {
        on(getLogger()).call("warn", message);
    }

    public static void error(String message) {
        on(getLogger()).call("error", message);
    }

    public static void debug(String message) {
//        if (Config.DEBUG_MODE)
    }

}
