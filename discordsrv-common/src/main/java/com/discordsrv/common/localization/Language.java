package com.discordsrv.common.localization;

import java.util.Map;

import static org.joor.Reflect.on;

public enum Language {

    ENGLISH,
    GERMAN;

    static {
        // default to English
        change(Language.ENGLISH);
    }

    private static Language selected;

    /**
     * Get the currently selected plugin language
     */
    public static Language getSelected() {
        return selected;
    }

    public static void change(Language language) {
        selected = language;
        on(Localized.class).fields().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(reflect -> reflect.type() == Localized.Definition.class)
                .map(reflect -> ((Localized.Definition) reflect.get()))
                .forEach(Localized.Definition::update);
    }

}
