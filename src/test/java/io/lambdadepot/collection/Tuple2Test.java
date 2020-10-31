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

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tuple2Test {

    @Test
    void mapT1() {
        Assertions.assertEquals(1, Tuple2.of("1", 2).mapT1(Integer::parseInt).getT1());
    }

    @Test
    void mapT2() {
        Assertions.assertEquals(2, Tuple2.of(1, "2").mapT2(Integer::parseInt).getT2());
    }

    @Test
    void transform() {
        Tuple2<Integer, Integer> integerTuple2 = Tuple2.of("1", "2")
                .transform(t -> t.mapT1(Integer::parseInt).mapT2(Integer::parseInt));
        assertEquals(1, integerTuple2.getT1());
        assertEquals(2, integerTuple2.getT2());
    }

    @Test
    void reversed() {
        Tuple2<Integer, Integer> tuple2 = Tuple2.of(1, 2).reversed();
        assertEquals(2, tuple2.getT1());
        assertEquals(1, tuple2.getT2());
    }

    @Test
    void getSize() {
        assertEquals(2, Tuple2.of(1, 2).getSize());
    }

    @Test
    void getT1() {
        assertEquals(1, Tuple2.of(1, 2).getT1());
    }

    @Test
    void getT2() {
        assertEquals(2, Tuple2.of(1, 2).getT2());
    }
}