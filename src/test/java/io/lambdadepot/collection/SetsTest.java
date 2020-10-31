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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetsTest {
    private Set<Foo> getFooSet() {
        Set<Foo> foos = new HashSet<>();
        for (int i = 0; i < 25; i++) {
            Foo foo = new Foo(new Bar(String.valueOf(i)));
            foos.add(foo);
        }
        return foos;
    }

    @Test
    void map() {
        Set<Bar> result = Sets.map(Foo::getBar).apply(getFooSet());
        assertFalse(result.isEmpty());
        assertEquals(25, result.size());
    }

    @Test
    void flatMap() {
        Set<Integer> result = Sets.flatMap(Foo::getOneToFive).apply(getFooSet());
        assertFalse(result.isEmpty());
        assertEquals(5, result.size());
    }

    @Test
    void filter() {
        Set<Integer> set = getFooSet().stream().map(Foo::getOneToFive)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Predicate<Integer> isLessThanThree = integer -> integer < 3;
        Set<Integer> result = Sets.filter(isLessThanThree).apply(set);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 2)));
    }

    private static class Foo {
        private Bar bar;
        private Set<Integer> oneToFive;

        Foo(Bar bar) {
            this.bar = bar;
            this.oneToFive = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        }

        Bar getBar() {
            return bar;
        }

        Set<Integer> getOneToFive() {
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