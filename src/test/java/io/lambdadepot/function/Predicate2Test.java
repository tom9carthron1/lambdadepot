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

import java.util.function.BiPredicate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Predicate2Test {

    @Test
    void testOf() {
        assertNotNull(Predicate2.of(TestPredicates::isEven2));
        assertThrows(NullPointerException.class, () ->
                Predicate2.of(null));
    }

    @Test
    void testAsPredicate2() {
        BiPredicate<Integer, Integer> javaBiPredicate = TestPredicates::isEven2;
        assertNotNull(Predicate2.asPredicate2(javaBiPredicate));
        assertThrows(NullPointerException.class, () ->
                Predicate2.asPredicate2(null));
    }

    @Test
    void testPartialApply() {
        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .partialApply(2)
                .test(4));

        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .partialApply(2)
                .test(3));

        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .partialApply(2, 4)
                .test());

        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .partialApply(2, 3)
                .test());
    }

    @Test
    void testAnd() {
        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .and(TestPredicates::isGreaterThan100_2)
                .test(104, 200));

        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .and(TestPredicates::isGreaterThan100_2)
                .test(103, 200));

        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .and(TestPredicates::isGreaterThan100_2)
                .test(104, 2));

        assertThrows(NullPointerException.class, () ->
                Predicate2.of(TestPredicates::isEven2)
                        .and(null));
    }

    @Test
    void testNegate() {
        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .test(2, 4));
        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .negate()
                .test(2, 4));
    }

    @Test
    void testOr() {
        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .or(TestPredicates::isGreaterThan100_2)
                .test(103, 105));

        assertTrue(Predicate2.of(TestPredicates::isEven2)
                .or(TestPredicates::isGreaterThan100_2)
                .test(2, 4));

        assertFalse(Predicate2.of(TestPredicates::isEven2)
                .and(TestPredicates::isGreaterThan100_2)
                .test(3, 5));

        assertThrows(NullPointerException.class, () ->
                Predicate2.of(TestPredicates::isEven2)
                        .or(null));
    }

    @Test
    void testReverse() {
        assertTrue(Predicate2.of(TestPredicates::allEquals2)
                .test("2", 2));

        assertTrue(Predicate2.of(TestPredicates::allEquals2)
                .reverse()
                .test(2, "2"));
    }
}
