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

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Predicate0Test {

    private static final Predicate0 TRUE = Boolean.TRUE::booleanValue;
    private static final Predicate0 FALSE = Boolean.FALSE::booleanValue;

    @Test
    void testOf() {
        assertNotNull(Predicate0.of(Boolean.TRUE::booleanValue));
        assertTrue(TRUE.test());
        assertNotNull(Predicate0.of(Boolean.FALSE::booleanValue));
        assertFalse(FALSE.test());

        assertThrows(NullPointerException.class, () -> Predicate0.of(null));
    }

    @Test
    void testAsPredicate0() {
        Supplier<Boolean> javaSupplier = Boolean.TRUE::booleanValue;
        assertNotNull(Predicate0.asPredicate0(javaSupplier));

        assertThrows(NullPointerException.class, () -> Predicate0.asPredicate0(null));
    }

    @Test
    void testGet() {
        assertTrue(TRUE.get());
        assertFalse(FALSE.get());
    }

    @Test
    void testAnd() {
        assertFalse(TRUE.and(FALSE).test());
        assertTrue(TRUE.and(FALSE.negate()).test());

        assertThrows(NullPointerException.class, () -> TRUE.and(null));
    }

    @Test
    void testNegate() {
        assertFalse(TRUE.negate().test());
        assertTrue(FALSE.negate().test());
    }

    @Test
    void testOr() {
        assertTrue(TRUE.or(FALSE).test());
        assertFalse(FALSE.or(TRUE.negate()).test());
        assertThrows(NullPointerException.class, () -> TRUE.or(null));
    }
}
