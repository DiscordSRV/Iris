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

package com.discordsrv.common.dynamic;

import github.scarsz.configuralize.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.joor.Reflect.on;

public class Localized {

    public static final Definition ASM_NEEDS_TO_BE_UPDATED = new Definition()
            .localize(Language.EN, "Your server is using an older version of ASM. You must manually update it.")
            .localize(Language.FR, "hon hon baguette eiffel tower");

    @Getter @Setter private static Language language = Language.EN;

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
     * Represents a single {@link String} whose value will change depending on {@link Localized#getLanguage()}}
     */
    public static final class Definition {

        private final Map<Language, String> definitions = new HashMap<>();

        private Definition localize(Language language, String definition) {
            definitions.put(language, definition);
            return this;
        }

        private boolean isTranslated() {
            return Arrays.stream(Language.values()).allMatch(this::isTranslated);
        }

        private boolean isTranslated(Language language) {
            return definitions.containsKey(language);
        }

        public String value() {
            return definitions.getOrDefault(Localized.getLanguage(), definitions.get(Language.EN));
        }

        @Override
        public String toString() {
            return "Definition{" +
                    "value='" + value() + '\'' +
                    ", languages=" + definitions.keySet().stream()
                            .map(Language::getCode)
                            .collect(Collectors.joining(", ")) +
                    '}';
        }

    }

}
