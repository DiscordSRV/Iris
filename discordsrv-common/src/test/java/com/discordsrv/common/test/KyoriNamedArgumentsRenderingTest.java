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

package com.discordsrv.common.test;

import com.discordsrv.common.Text;
import org.junit.Assert;
import org.junit.Test;

public class KyoriNamedArgumentsRenderingTest {

    @Test
    public void test() {
        String testOneRendered = Text.asPlain(Text.KYORI_TEST_INDEX_SUBSTITUTION.render("Person 1", "Person 2"));
        String testTwoRendered = Text.asPlain(Text.KYORI_TEST_NAME_SUBSTITUTION.renderNamedArgs(
                "the first person", "Person 1",
                "the second person", "Person 2"
        ));

        System.out.println("Index-based substitution: " + Text.KYORI_TEST_INDEX_SUBSTITUTION.getRawFormat() + "\n-> " + testOneRendered);
        System.out.println("Name-based substitution: " + Text.KYORI_TEST_NAME_SUBSTITUTION.getRawFormat() + "\n-> " + testTwoRendered);

        Assert.assertEquals(testOneRendered, testTwoRendered);
        System.out.println("Results match");
    }

}
