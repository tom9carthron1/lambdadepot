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

public class Predicate3Test {
    @Test
    public void testOf() {
        assertNotNull(Predicate3.of(TestPredicates::isEven3));

        assertThrows(NullPointerException.class, () ->
                Predicate3.of(null));
    }

    @Test
    public void testTest() {
        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .test(2, 4, 6));
        assertFalse(Predicate3.of(TestPredicates::isEven3)
                .test(1, 2, 3));
    }

    @Test
    public void testPartialApply() {
        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .partialApply(2)
                .test(4, 6));

        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .partialApply(2, 4)
                .test(6));

        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .partialApply(2, 4, 6)
                .test());
    }

    @Test
    public void testAnd() {
        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .and(TestPredicates::isGreaterThan100_3)
                .test(200, 300, 400));

        assertFalse(Predicate3.of(TestPredicates::isEven3)
                .and(TestPredicates::isGreaterThan100_3)
                .test(200, 300, 4));

        assertThrows(NullPointerException.class, () ->
                Predicate3.of(TestPredicates::isEven3)
                        .and(null));
    }

    @Test
    public void testNegate() {
        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .negate()
                .test(1, 3, 5));
    }

    @Test
    public void testOr() {
        assertTrue(Predicate3.of(TestPredicates::isEven3)
                .or(TestPredicates::isGreaterThan100_3)
                .test(2, 4, 6));

        assertFalse(Predicate3.of(TestPredicates::isEven3)
                .or(TestPredicates::isGreaterThan100_3)
                .test(3, 5, 7));

        assertThrows(NullPointerException.class, () ->
                Predicate3.of(TestPredicates::isEven3)
                        .or(null));
    }

    @Test
    public void testReverse() {
        assertTrue(Predicate3.of(TestPredicates::allEquals3)
                .test("2", 2, 2f));

        assertTrue(Predicate3.of(TestPredicates::allEquals3)
                .reverse()
                .test(2f, 2, "2"));
    }
}
