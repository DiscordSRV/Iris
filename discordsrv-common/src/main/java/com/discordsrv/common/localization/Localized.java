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

package com.discordsrv.common.localization;

import java.util.HashMap;
import java.util.Map;

import static org.joor.Reflect.on;

/**
 * The configuration library that DiscordSRV uses, Configurate, does not support localization for config comments.
 * This class uses reflection on {@link String}s declared as final to change their values when a new language is loaded.
 * If you are attempting to learn how DiscordSRV works to learn from it, please note that the preferred solution
 * is to have proper globalization in the config library.
 */
public class Localized {

    public static final Definition CONFIG_DEBUG_CATEGORY = new Definition()
            .localize(Language.ENGLISH, "Debug options. Unless you have a reason to touch these, don't.");
    public static final Definition CONFIG_DEBUG_MODE = new Definition()
            .localize(Language.ENGLISH, "Mode of debug messages for DiscordSRV. 0=none, 1=all, 2=all+stacktraces")
            .localize(Language.GERMAN, "hon hon baguette");

    /**
     * Returns whether or not every {@link Localized} message has definitions for every {@link Language}
     */
    public static boolean isCompletelyTranslated() {
        return on(Localized.class).fields().values().stream()
                .filter(r -> r.type() == Definition.class)
                .map(r -> (Definition) r.get())
                .allMatch(Definition::isTranslated);
    }

    /**
     * Represents a single {@link String} whose value will change depending on {@link Language#getSelected()}
     */
    public static final class Definition {

        private final Map<Language, String> definitions = new HashMap<>();
        public final String value = "[Language not loaded]";

        private Definition localize(Language language, String definition) {
            definitions.put(language, definition);
            if (language == Language.getSelected()) update();
            return this;
        }

        private boolean isTranslated() {
            for (Language language : Language.values()) {
                if (!isTranslated(language)) {
                    return false;
                }
            }

            return true;
        }

        private boolean isTranslated(Language language) {
            return definitions.containsKey(language);
        }

        void update() {
//            on(this).set("value", definitions.getOrDefault(Language.getSelected(), "[" + Language.getSelected().name() + " contains no definition]"));
        }

        @Override
        public String toString() {
            return "Definition{" +
                    "definitions=" + definitions +
                    ", value='" + value + '\'' +
                    '}';
        }

    }

}
