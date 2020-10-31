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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Predicate4Test {
    @Test
    void testOf() {
        assertNotNull(Predicate4.of(TestPredicates::isEven4));

        assertThrows(NullPointerException.class, () ->
                Predicate4.of(null));
    }

    @Test
    void testTest() {
        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .test(2, 4, 6, 8));
        assertFalse(Predicate4.of(TestPredicates::isEven4)
                .test(1, 2, 3, 4));
    }

    @Test
    void testPartialApply() {
        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .partialApply(2)
                .test(4, 6, 8));

        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .partialApply(2, 4)
                .test(6, 8));

        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .partialApply(2, 4, 6)
                .test(8));

        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .partialApply(2, 4, 6, 8)
                .test());
    }

    @Test
    void testAnd() {
        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .and(TestPredicates::isGreaterThan100_4)
                .test(200, 300, 400, 500));

        assertFalse(Predicate4.of(TestPredicates::isEven4)
                .and(TestPredicates::isGreaterThan100_4)
                .test(200, 300, 400, 5));

        assertThrows(NullPointerException.class, () ->
                Predicate4.of(TestPredicates::isEven4)
                        .and(null));
    }

    @Test
    void testNegate() {
        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .negate()
                .test(1, 3, 5, 7));
    }

    @Test
    void testOr() {
        assertTrue(Predicate4.of(TestPredicates::isEven4)
                .or(TestPredicates::isGreaterThan100_4)
                .test(2, 4, 6, 8));

        assertFalse(Predicate4.of(TestPredicates::isEven4)
                .or(TestPredicates::isGreaterThan100_4)
                .test(3, 5, 7, 9));

        assertThrows(NullPointerException.class, () ->
                Predicate3.of(TestPredicates::isEven3)
                        .or(null));
    }

    @Test
    void testReverse() {
        assertTrue(Predicate4.of(TestPredicates::allEquals4)
                .test("2", 2, 2f, 2.0));

        assertTrue(Predicate4.of(TestPredicates::allEquals4)
                .reverse()
                .test(2.0, 2f, 2, "2"));
    }
}
