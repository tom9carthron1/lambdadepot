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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListsTest {
    private final List<Integer> oneToFive = Arrays.asList(1, 2, 3, 4, 5);
    private final List<Integer> sixToTen = Arrays.asList(6, 7, 8, 9, 10);

    private List<Foo> getFooList() {
        List<Foo> foos = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Foo a = new Foo(new Bar(String.valueOf(i)));
            foos.add(a);
        }
        return foos;
    }

    @Test
    void map() {
        List<Bar> bars = Lists.map(Foo::getBar).apply(getFooList());
        assertEquals(25, bars.size());
        List<String> manchus = Lists.map(Bar::getManchu).apply(bars);
        assertEquals(25, manchus.size());
    }

    @Test
    void flatMap() {
        List<Integer> oneToFives = Lists.flatMap(Foo::getOneToFive)
                .apply(getFooList());
        assertEquals(125, oneToFives.size());
    }

    @Test
    void filter() {
        Predicate<Foo> predicate = foo -> foo.getOneToFive().size() > 5;
        List<Foo> foos = Lists.filter(predicate).apply(getFooList());
        assertTrue(foos::isEmpty);
    }

    @Test
    void getHead() {
        assertEquals(Optional.empty(), Lists.getHead(Collections.emptyList()));
        assertEquals(1, Lists.getHead(Arrays.asList(1, 2)).get());
    }

    @Test
    void getTail() {
        assertEquals(Optional.empty(), Lists.getTail(Collections.emptyList()));
        assertEquals(2, Lists.getTail(Arrays.asList(1, 2)).get());
    }

    @Test
    void get() {
        assertEquals(Optional.empty(), Lists.get(Collections.emptyList(), 1));
        assertEquals(Optional.empty(), Lists.get(Arrays.asList(1, 2), 2));
        assertEquals(1, Lists.get(Arrays.asList(1, 2), 0).get());
        assertEquals(2, Lists.get(Arrays.asList(1, 2), 1).get());
    }

    @Test
    void append() {
        List<Integer> result = Lists.append(6, 7, 8, 9, 10).apply(oneToFive);
        assertEquals(10, result.size());
        assertTrue(result.containsAll(oneToFive));
        assertTrue(result.containsAll(sixToTen));
        // verify order of elements
        for (int i = 0; i < 10; i++) {
            int val = i + 1;
            assertEquals(val, result.get(i));
        }
    }

    @Test
    void testAppend() {
        List<Integer> result = Lists.append(sixToTen).apply(oneToFive);
        assertEquals(10, result.size());
        assertTrue(result.containsAll(oneToFive));
        assertTrue(result.containsAll(sixToTen));
        // verify order of elements
        for (int i = 0; i < 10; i++) {
            int val = i + 1;
            assertEquals(val, result.get(i));
        }
    }

    @Test
    void prepend() {
        List<Integer> result = Lists.prepend(1, 2, 3, 4, 5).apply(sixToTen);
        assertEquals(10, result.size());
        assertTrue(result.containsAll(oneToFive));
        assertTrue(result.containsAll(sixToTen));
        // verify order of elements
        for (int i = 0; i < 10; i++) {
            int val = i + 1;
            assertEquals(val, result.get(i));
        }
    }

    @Test
    void testPrepend() {
        List<Integer> result = Lists.prepend(oneToFive).apply(sixToTen);
        assertEquals(10, result.size());
        assertTrue(result.containsAll(oneToFive));
        assertTrue(result.containsAll(sixToTen));
        // verify order of elements
        for (int i = 0; i < 10; i++) {
            int val = i + 1;
            assertEquals(val, result.get(i));
        }
    }

    private static class Foo {
        private Bar bar;
        private List<Integer> oneToFive;

        Foo(Bar bar) {
            this.bar = bar;
            this.oneToFive = Arrays.asList(1, 2, 3, 4, 5);
        }

        Bar getBar() {
            return bar;
        }

        List<Integer> getOneToFive() {
            return oneToFive;
        }
    }

    private static class Bar {
        private String manchu;

        Bar(String manchu) {
            this.manchu = manchu;
        }

        String getManchu() {
            return manchu;
        }
    }
}