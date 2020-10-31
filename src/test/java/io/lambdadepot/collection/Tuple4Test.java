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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tuple4Test {
    Tuple4<String, String, String, String> tuple = Tuple4.of("1", "2", "3", "4");

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
        Assertions.assertEquals(1, tuple.mapT1(Integer::parseInt).getT1());
    }

    @Test
    void mapT2() {
        Assertions.assertEquals(2, tuple.mapT2(Integer::parseInt).getT2());
    }

    @Test
    void mapT3() {
        Assertions.assertEquals(3, tuple.mapT3(Integer::parseInt).getT3());
    }

    @Test
    void mapT4() {
        Assertions.assertEquals(4, tuple.mapT4(Integer::parseInt).getT4());
    }

    @Test
    void transform() {
        assertEquals(Tuple4.of(1, 2, 3, 4).hashCode(), tuple.transform(t -> t.mapT1(Integer::parseInt)
                .mapT2(Integer::parseInt)
                .mapT3(Integer::parseInt)
                .mapT4(Integer::parseInt))
                .hashCode());
    }

    @Test
    void reversed() {
        Tuple4<String, String, String, String> reversed = tuple.reversed();
        assertEquals("4", reversed.getT1());
        assertEquals("3", reversed.getT2());
        assertEquals("2", reversed.getT3());
        assertEquals("1", reversed.getT4());
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

    @Test
    void getT4() {
        assertEquals("4", tuple.getT4());
    }
}