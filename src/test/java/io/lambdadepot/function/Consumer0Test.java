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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Consumer0Test extends ConsumerBaseTest {

    @Test
    void testOf() {
        assertNotNull(Consumer0.of(TestConsumers::printOutHelloWorld));
        assertThrows(NullPointerException.class, () -> Consumer0.of(null));
    }

    @Test
    void testAccept() {
        assertEquals("", outContent.toString());
        Consumer0.of(TestConsumers::printOutHelloWorld)
                .accept();
        assertEquals("hello, world", outContent.toString());
    }

    @Test
    void testAndThen() {
        assertEquals("", outContent.toString());
        assertEquals("", errContent.toString());
        Consumer0.of(TestConsumers::printOutHelloWorld)
                .andThen(TestConsumers::printErrHelloWorld)
                .accept();
        assertEquals("hello, world", outContent.toString());
        assertEquals("hello, world", errContent.toString());
        assertThrows(NullPointerException.class, () -> Consumer0.of(TestConsumers::printOutHelloWorld).andThen(null));
    }
}
