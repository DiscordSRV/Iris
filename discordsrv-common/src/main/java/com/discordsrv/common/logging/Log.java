/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
                    System.out.println("[DiscordSRV] [" + level.name() + "]" + message);
                case ERROR:
                    System.err.println("[DiscordSRV] [ERROR]" + message);
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
