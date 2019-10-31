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

package com.discordsrv.common;

import github.scarsz.configuralize.Language;
import lombok.Getter;
import lombok.Setter;
import net.kyori.text.Component;
import net.kyori.text.KeybindComponent;
import net.kyori.text.TextComponent;
import net.kyori.text.TranslatableComponent;
import net.kyori.text.renderer.FriendlyComponentRenderer;
import net.kyori.text.serializer.plain.PlainComponentSerializer;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Text {

    @Getter private static final PlainComponentSerializer plainSerializer = new PlainComponentSerializer(KeybindComponent::keybind, TranslatableComponent::key);
    @Getter private static final FriendlyComponentRenderer<Language> renderer = FriendlyComponentRenderer.from((language, key) -> Text.get(key).getFormat(language));
    @Getter private static final Set<Definition> definitions = new HashSet<>();
    @Getter @Setter private static Language language = Language.EN;

    public static final Definition ASM_NEEDS_TO_BE_UPDATED = new Definition("asm")
            .localize(Language.EN, "Your server is using an older version of ASM. You must manually update it.")
            .localize(Language.FR, "hon hon baguette eiffel tower");
    public static final Definition KYORI_TEST_INDEX_SUBSTITUTION = new Definition("kyori.test.1", "{0} interacted with {1}");
    public static final Definition KYORI_TEST_NAME_SUBSTITUTION = new Definition("kyori.test.2", "{the first person} interacted with {the second person}");

    public static Definition get(String key) {
        return definitions.stream()
                .filter(definition -> definition.getKey().equalsIgnoreCase(key))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No definition exists for key " + key));
    }

    public static String asPlain(Component component) {
        return plainSerializer.serialize(component);
    }

    public static Locale languageAsLocale() {
        return Locale.forLanguageTag(Text.getLanguage().getCode().toLowerCase());
    }

    /**
     * Returns whether or not every {@link Text} message has definitions for every {@link Language}
     */
    public static boolean isCompletelyTranslated() {
        return definitions.stream().allMatch(Definition::isTranslated);
    }

    /**
     * Represents a single {@link String} whose value will change depending on {@link Text#getLanguage()}}
     */
    public static final class Definition {

        @Getter private final String key;
        @Getter private final Map<Language, String> rawFormats = new HashMap<>();
        @Getter private final Map<Language, MessageFormat> translations = new HashMap<>();
        @Getter private final Set<String> expectedArguments = new LinkedHashSet<>();

        public Definition(String key) {
            this.key = key;
            Text.definitions.add(this);
        }

        public Definition(String key, String english) {
            this(key);
            this.localize(Language.EN, english);
        }

        public Definition localize(Language language, String format) {
            StringBuilder reading = null;
            char[] chars = format.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (reading == null) {
                    if (c == '{') {
                        reading = new StringBuilder();
                    }
                } else if (c == '}') {
                    expectedArguments.add(reading.toString().toLowerCase());
                    reading = null;
                } else {
                    reading.append(c);
                }
            }

            String formatCleaned = format;
            int i = 0;
            for (String argument : expectedArguments) {
                formatCleaned = formatCleaned.replace("{" + argument + "}", "{" + i++ + "}");
            }

            rawFormats.put(language, format);
            translations.put(language, new MessageFormat(formatCleaned));
            return this;
        }

        public Component render(Object... args) {
            return render(Arrays.stream(args)
                    .map(o -> {
                        if (o instanceof Component) {
                            return (Component) o;
                        } else {
                            return TextComponent.of(o.toString());
                        }
                    })
                    .toArray(Component[]::new)
            );
        }
        public Component render(Component... args) {
            return render(Text.getLanguage(), args);
        }
        public Component renderNamedArgs(Object... args) {
            return renderNamedArgs(Text.getLanguage(), args);
        }
        public Component renderNamedArgs(Language language, Object... args) {
            if (args == null) return render(language);
            if (args.length % 2 != 0) throw new IllegalArgumentException("Amount of arguments must be even");

            Set<Component> builtArgs;
            if (expectedArguments.size() == 0) {
                builtArgs = Collections.emptySet();
            } else {
                builtArgs = new LinkedHashSet<>();

                for (String argument : expectedArguments) {
                    int indexOfArgument = ArrayUtils.indexOf(args, argument);
                    if (indexOfArgument == ArrayUtils.INDEX_NOT_FOUND) {
                        builtArgs.add(TextComponent.empty());
                    } else {
                        Object value = args[indexOfArgument + 1];
                        if (value instanceof Component) {
                            builtArgs.add((Component) value);
                        } else {
                            builtArgs.add(TextComponent.of(String.valueOf(value)));
                        }
                    }
                }
            }

            return render(language, builtArgs.toArray(new Component[0]));
        }
        public Component render(Language language, Component... args) {
            if (args == null || args.length == 0) {
                return Text.getRenderer().render(TranslatableComponent.of(this.key), language);
            } else {
                return Text.getRenderer().render(TranslatableComponent.of(this.key).args(args), language);
            }
        }

        private boolean isTranslated() {
            return Arrays.stream(Language.values()).allMatch(this::isTranslated);
        }
        private boolean isTranslated(Language language) {
            return translations.containsKey(language);
        }

        public MessageFormat getFormat() {
            return getFormat(Text.getLanguage());
        }
        public MessageFormat getFormat(Language language) {
            return translations.getOrDefault(language, translations.get(Language.EN));
        }

        public String getRawFormat() {
            return getRawFormat(Text.getLanguage());
        }
        public String getRawFormat(Language language) {
            return rawFormats.getOrDefault(language, rawFormats.get(Language.EN));
        }

        @Override
        public String toString() {
            return "Definition{" +
                    "format='" + getFormat() + '\'' +
                    ", languages=" + translations.keySet().stream()
                            .map(Language::getName)
                            .collect(Collectors.joining(", ")) +
                    '}';
        }

    }

}
