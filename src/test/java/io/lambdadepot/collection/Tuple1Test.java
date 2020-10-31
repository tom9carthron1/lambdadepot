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

package io.lambdadepot.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Tuple1Test {

    @Test
    void iterator() {
        Tuple1<String> tuple1 = Tuple1.of("t-one");
        assertAll("Iterator", () -> {
            assertTrue(tuple1.iterator().hasNext());
            assertEquals("t-one", tuple1.iterator().next());
        });
    }

    @Test
    void mapT1() {
        Tuple1<Integer> tuple1 = Tuple1.of("10").mapT1(Integer::parseInt);
        assertEquals(10, tuple1.getT1());
    }

    @Test
    void transform() {
        Tuple1<Integer> tuple1 = Tuple1.of(10);
        Assertions.assertEquals("10", tuple1.transform(t -> t.mapT1(String::valueOf)).getT1());
    }

    @Test
    void getSize() {
        assertEquals(1, Tuple1.of(10).getSize());
    }

    @Test
    void getT1() {
        assertEquals(10, Tuple1.of(10).getT1());
    }
}