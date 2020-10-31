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

package io.lambdadepot.util;

import io.lambdadepot.function.Function1;
import io.lambdadepot.function.Predicate1;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicatesTest {
    private static final Predicate1<String> p1 = s -> s.contains("hello");
    private static final Predicate1<String> p2 = s -> s.contains("world");

    @Test
    void testConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        assertEquals(1, Predicates.class.getDeclaredConstructors().length);
        Constructor constructor = Predicates.class.getDeclaredConstructors()[0];
        assertFalse(constructor.isAccessible());
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAllOf() {
        assertTrue(Predicates.allOf(p1, p2).test("hello world"));
        assertFalse(Predicates.allOf(p1, p2).test("hello earth"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAnyOf() {
        assertTrue(Predicates.anyOf(p1, p2).test("hello world"));
        assertTrue(Predicates.anyOf(p1, p2).test("hello earth"));
        assertFalse(Predicates.anyOf(p1, p2).test("goodbye earth"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testNoneOf() {
        assertTrue(Predicates.noneOf(p1, p2).test("goodbye earth"));
        assertFalse(Predicates.noneOf(p1, p2).test("hello"));
    }

    @Test
    void testINotNull() {
        assertTrue(Predicates.isNotNull().test(""));
        assertFalse(Predicates.isNotNull().test(null));
    }

    @Test
    void testIsNull() {
        assertTrue(Predicates.isNull().test(null));
        assertFalse(Predicates.isNull().test(""));
    }

    @Test
    void testNot() {
        assertTrue(Predicates.not(Objects::isNull).test(""));
        assertFalse(Predicates.not(Objects::isNull).test(null));
    }

    @Test
    void testIsGreaterThan() {
        assertTrue(Predicates.isGreaterThan(10).test(11));
        assertFalse(Predicates.isGreaterThan(10).test(9));
        assertFalse(Predicates.isGreaterThan(10).test(10));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertTrue(Predicates.isGreaterThan(10, comparator).test(11));
        assertFalse(Predicates.isGreaterThan(10, comparator).test(9));
        assertFalse(Predicates.isGreaterThan(10, comparator).test(10));
    }

    @Test
    void testIsGreaterThanOrEqualTo() {
        assertTrue(Predicates.isGreaterThanOrEqualTo(10).test(11));
        assertTrue(Predicates.isGreaterThanOrEqualTo(10).test(10));
        assertFalse(Predicates.isGreaterThanOrEqualTo(10).test(9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertTrue(Predicates.isGreaterThanOrEqualTo(10, comparator).test(11));
        assertTrue(Predicates.isGreaterThanOrEqualTo(10, comparator).test(10));
        assertFalse(Predicates.isGreaterThanOrEqualTo(10, comparator).test(9));
    }

    @Test
    void testIsLessThan() {
        assertFalse(Predicates.isLessThan(10).test(11));
        assertTrue(Predicates.isLessThan(10).test(9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertFalse(Predicates.isLessThan(10, comparator).test(11));
        assertTrue(Predicates.isLessThan(10, comparator).test(9));
    }

    @Test
    void testIsLessThanOrEqualTo() {
        assertFalse(Predicates.isLessThanOrEqualTo(10).test(11));
        assertTrue(Predicates.isLessThanOrEqualTo(10).test(10));
        assertTrue(Predicates.isLessThanOrEqualTo(10).test(9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertFalse(Predicates.isLessThanOrEqualTo(10, comparator).test(11));
        assertTrue(Predicates.isLessThanOrEqualTo(10, comparator).test(10));
        assertTrue(Predicates.isLessThanOrEqualTo(10, comparator).test(9));
    }

    @Test
    void testIsEqualTo() {
        assertTrue(Predicates.isEqualTo(10).test(10));
        assertFalse(Predicates.isEqualTo(10).test(9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertTrue(Predicates.isEqualTo(10, comparator).test(10));
        assertFalse(Predicates.isEqualTo(10, comparator).test(9));
    }

    @Test
    void testIsNotEqualTo() {
        assertFalse(Predicates.isNotEqualTo(10).test(10));
        assertTrue(Predicates.isNotEqualTo(10).test(9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertFalse(Predicates.isNotEqualTo(10, comparator).test(10));
        assertTrue(Predicates.isNotEqualTo(10, comparator).test(9));
    }

    @Test
    void testValidatePredicatesThrowsIllegalArgumentException1() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Predicates.validatePredicates(p1, null, p2));
        assertEquals("null Predicate supplied", exception.getMessage());
    }

    @Test
    void testValidatePredicatesThrowsIllegalArgumentException2() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Predicates.validatePredicates());
        assertEquals("no Predicate supplied to evaluate", exception.getMessage());
    }

    @Test
    void testInstanceOf() {

        class A {
        }

        class B {
        }

        class C extends A {
        }

        A a = new A();
        B b = new B();
        C c = new C();
        assertTrue(Predicates.instanceOf(A.class).test(a));
        assertFalse(Predicates.instanceOf(A.class).test(b));
        assertTrue(Predicates.instanceOf(A.class).test(c));
    }

    @Test
    void testTestProperty() {
        Predicate1<String> isFirstCharacterH = Predicates.testProperty(s -> s.charAt(0), c -> c == 'H');
        assertNotNull(isFirstCharacterH);
        assertTrue(isFirstCharacterH.test("Hello world"));
        assertFalse(isFirstCharacterH.test("hello world"));

        SafeGetter<String, Character> firstCharacter = SafeGetter.of(s -> s.charAt(0));
        isFirstCharacterH = Predicates.testProperty(firstCharacter, c -> c == 'H');
        assertNotNull(isFirstCharacterH);
        assertTrue(isFirstCharacterH.test("Hello world"));
        assertFalse(isFirstCharacterH.test("hello world"));
    }

    @Test
    void testTestPropertyThrowsNullPointerException1() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty((Function1<Object, Character>) null, c -> c == 'H'));
    }

    @Test
    void testTestPropertyThrowsNullPointerException2() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty((String s) -> s.charAt(0), null));
    }

    @Test
    void testTestPropertyThrowsNullPointerException3() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty((Function1<Object, Character>) null, null));
    }

    @Test
    void testTestPropertyThrowsNullPointerException4() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty((SafeGetter<Object, Character>) null, c -> c == 'H'));
    }

    @Test
    void testTestPropertyThrowsNullPointerException5() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty(SafeGetter.of((String s) -> s.charAt(0)), null));
    }

    @Test
    void testTestPropertyThrowsNullPointerException6() {
        assertThrows(NullPointerException.class, () ->
                Predicates.testProperty((SafeGetter<Object, Character>) null, null));
    }
}
