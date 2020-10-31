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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapsTest {

    @Test
    void getOrPut() {
        Map<Integer, String> map = new HashMap<>();
        Maps.getOrPut(10, () -> "ten").apply(map);
        assertFalse(map::isEmpty);
        assertTrue(map.containsKey(10));
        assertEquals("ten", map.get(10));
        assertEquals("ten", Maps.getOrPut(10, () -> "not ten").apply(map));
    }

    @Test
    void getOrDefault() {
        Map<Integer, String> map = new HashMap<>();
        assertEquals("ten", Maps.getOrDefault(10, () -> "ten").apply(map));
        assertTrue(map::isEmpty);
        map.put(10, "ten");
        assertEquals("ten", Maps.getOrDefault(10, () -> "not ten").apply(map));
    }

    @Test
    void get() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        assertEquals(Optional.empty(), Maps.get(map, 0));
        assertEquals("one", Maps.get(map, 1).get());
    }
}