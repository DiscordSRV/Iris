package com.discordsrv.common;

import com.discordsrv.common.localization.Language;
import com.discordsrv.common.localization.Localized;

public class Main {

    public static void main(String... args) {
        System.out.println(Localized.CONFIG_DEBUG_MODE);
        Language.change(Language.GERMAN);
        System.out.println(Localized.CONFIG_DEBUG_MODE.value);
    }

}
