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
