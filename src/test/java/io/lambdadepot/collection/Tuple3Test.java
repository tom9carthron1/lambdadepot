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

import java.util.Iterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tuple3Test {
    private final Tuple3<String, String, String> tuple = Tuple3.of("1", "2", "3");

    @Test
    void iterator() {
        Iterator iterator = tuple.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count += 1;
            assertEquals(count, Integer.parseInt((String) iterator.next()));
        }
        assertEquals(count, tuple.getSize());
    }

    @Test
    void mapT1() {
        assertEquals(1, tuple.mapT1(Integer::parseInt).getT1());
    }

    @Test
    void mapT2() {
        assertEquals(2, tuple.mapT2(Integer::parseInt).getT2());
    }

    @Test
    void mapT3() {
        assertEquals(3, tuple.mapT3(Integer::parseInt).getT3());
    }

    @Test
    void transform() {
        assertEquals(Tuple3.of(1, 2, 3).hashCode(), tuple.transform(t -> t.mapT1(Integer::parseInt)
                .mapT2(Integer::parseInt)
                .mapT3(Integer::parseInt))
                .hashCode());
    }

    @Test
    void reversed() {
        Tuple3<String, String, String> reversed = tuple.reversed();
        assertEquals("3", reversed.getT1());
        assertEquals("2", reversed.getT2());
        assertEquals("1", reversed.getT3());
    }

    @Test
    void getSize() {
        assertEquals(3, tuple.getSize());
    }

    @Test
    void getT1() {
        assertEquals("1", tuple.getT1());
    }

    @Test
    void getT2() {
        assertEquals("2", tuple.getT2());
    }

    @Test
    void getT3() {
        assertEquals("3", tuple.getT3());
    }
}