/*
 * Copyright 2020 The Home Depot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lambdadepot.function;

import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Consumer2Test extends ConsumerBaseTest {

    @Test
    void testOf() {
        assertNotNull(Consumer2.of(TestConsumers::printOut1));

        assertThrows(NullPointerException.class, () -> Consumer2.of(null));
    }

    @Test
    void testAsConsumer2() {
        BiConsumer<String, String> javaBiConsumer = TestConsumers::printOut1;
        assertNotNull(Consumer2.asConsumer2(javaBiConsumer));
        assertThrows(NullPointerException.class, () ->
                Consumer2.asConsumer2(null));
    }

    @Test
    void testPartialApply() {
        Consumer2.of(TestConsumers::printOut1)
                .partialApply("hello, %s")
                .accept("world");

        assertEquals("hello, world", outContent.toString());

        setup();
        Consumer2.of(TestConsumers::printOut1)
                .partialApply("hello, %s", "world")
                .accept();
        assertEquals("hello, world", outContent.toString());
    }

    @Test
    void testAndThen() {
        Consumer2.of(TestConsumers::printOut1)
                .andThen(TestConsumers::printErr1)
                .accept("hello, %s", "world");
        assertEquals("hello, world", outContent.toString());
        assertEquals("hello, world", errContent.toString());

        assertThrows(NullPointerException.class, () -> Consumer2.of(TestConsumers::printOut1)
                .andThen(null));
    }

    @Test
    void testReverse() {
        Consumer2.of(TestConsumers::printOut1).reverse()
                .accept("hello", "%s, world");
        assertEquals("hello, world", outContent.toString());
    }
}
