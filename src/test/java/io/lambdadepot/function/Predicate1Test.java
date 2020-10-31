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

import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Predicate1Test {

    @Test
    public void testOf() {
        assertNotNull(Predicate1.of(TestPredicates::isEven));

        assertThrows(NullPointerException.class, () ->
                Predicate1.of(null));
    }

    @Test
    public void testAsPredicate1() {
        Predicate<Integer> javaPredicate = TestPredicates::isEven;
        assertNotNull(Predicate1.asPredicate1(javaPredicate));

        assertThrows(NullPointerException.class, () -> Predicate1.asPredicate1(null));
    }

    @Test
    public void testPartialApply() {
        assertTrue(Predicate1.of(TestPredicates::isEven)
                .partialApply(2)
                .test());
        assertFalse(Predicate1.of(TestPredicates::isEven)
                .partialApply(1)
                .test());
    }

    @Test
    public void testAnd() {
        assertTrue(Predicate1.of(TestPredicates::isEven)
                .and(TestPredicates::isGreaterThan100)
                .test(200));

        assertFalse(Predicate1.of(TestPredicates::isEven)
                .and(TestPredicates::isGreaterThan100)
                .test(90));

        assertThrows(NullPointerException.class, () -> Predicate1.of(StringUtils::isEmpty)
                .and(null));
    }

    @Test
    public void testNegate() {
        assertTrue(Predicate1.of(TestPredicates::isEven)
                .test(2));
        assertFalse(Predicate1.of(TestPredicates::isEven)
                .negate()
                .test(2));
    }

    @Test
    public void testOr() {
        assertTrue(Predicate1.of(TestPredicates::isEven)
                .or(TestPredicates::isGreaterThan100)
                .test(2));

        assertTrue(Predicate1.of(TestPredicates::isEven)
                .or(TestPredicates::isGreaterThan100)
                .test(103));

        assertFalse(Predicate1.of(TestPredicates::isEven)
                .or(TestPredicates::isGreaterThan100)
                .test(3));

        assertThrows(NullPointerException.class, () -> Predicate1.of(StringUtils::isEmpty)
                .or(null));
    }
}
