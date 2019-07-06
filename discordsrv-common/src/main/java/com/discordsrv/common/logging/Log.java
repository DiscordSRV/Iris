package com.discordsrv.common.logging;

public class Log {

    public enum LogLevel {

        INFO,
        WARN,
        ERROR,
        DEBUG

    }

    static {
        // default to sout/serr until a platform changes the log manually
        use((level, message) -> {
            switch (level) {
                case INFO:
                case WARN:
                    System.out.println("[DiscordSRV] " + message);
                case ERROR:
                    System.err.println("[DiscordSRV] " + message);
                    break;
                case DEBUG:
                    System.out.println("[DiscordSRV] [DEBUG] " + message);
                    break;
            }
        });
    }

    private static Logger logger;

    public static void use(Logger logger) {
        Log.logger = logger;
    }

    public static void log(LogLevel level, String message) {
        logger.log(level, message);
    }

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

}
