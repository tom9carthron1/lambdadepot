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

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Consumer1Test extends ConsumerBaseTest {

    @Test
    void testOf() {
        assertNotNull(Consumer1.of(TestConsumers::printOut));
        assertThrows(NullPointerException.class, () -> Consumer1.of(null));
    }

    @Test
    void testAsConsumer1() {
        Consumer<String> javaConsumer = TestConsumers::printOut;
        Consumer1<String> consumer1 = Consumer1.asConsumer1(javaConsumer);
        assertNotNull(consumer1);
        assertThrows(NullPointerException.class, () -> Consumer1.asConsumer1(null));
    }

    @Test
    void testPartialApply() {
        Consumer1.of(TestConsumers::printOut)
                .partialApply("hello, world")
                .accept();
        assertEquals("hello, world", outContent.toString());
    }

    @Test
    void testAndThen() {
        assertEquals("", outContent.toString());
        assertEquals("", errContent.toString());
        Consumer1.of(TestConsumers::printOut)
                .andThen(TestConsumers::printErr)
                .accept("hello, world");
        assertEquals("hello, world", outContent.toString());
        assertEquals("hello, world", errContent.toString());
        assertThrows(NullPointerException.class, () ->
                Consumer1.of(TestConsumers::printOut)
                        .andThen(null));
    }
}
