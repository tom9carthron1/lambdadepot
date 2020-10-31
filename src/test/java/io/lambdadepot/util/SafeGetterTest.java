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

import io.lambdadepot.pojo.Bar;
import io.lambdadepot.pojo.Foo;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SafeGetterTest {
    private static final String FOO_BARRED = "Foo barred";
    private static final Predicate<Bar> IS_FOO_BARRED = bar -> bar.getMessage().contains("Foo barred");
    private Foo foo;
    private Bar bar;

    @BeforeEach
    void setup() {
        foo = new Foo();
        bar = new Bar();
        bar.setMessage("Foo barred");
        foo.setBar(bar);
    }

    @Test
    @DisplayName("Of throws NPE when a null getter function is provided")
    void of_0() {
        Function<Foo, Bar> nullFunction = null;
        NullPointerException npe = assertThrows(NullPointerException.class, () -> SafeGetter.of(nullFunction));
        assertEquals("getter", npe.getMessage());
    }

    @Test
    @DisplayName("Of returns a SafeGetter instance")
    void of_1() {
        SafeGetter<Foo, Bar> barGetter = SafeGetter.of(Foo::getBar);
        assertNotNull(barGetter);
    }

    @Test
    void when() {
        SafeGetter<Foo, Bar> getter = SafeGetter.of(Foo::getBar);
        assertTrue(getter.when(IS_FOO_BARRED).get(foo).isPresent());
        assertFalse(getter.when(IS_FOO_BARRED.negate()).get(foo).isPresent());
    }

    @Test
    @DisplayName("SafeGetter then using Function parameter")
    void then_0() {
        SafeGetter<Foo, String> getter = SafeGetter.of(Foo::getBar).then(Bar::getMessage);
        assertTrue(getter.get(foo).isPresent());
        foo.getBar().setMessage(null);
        assertFalse(getter.get(foo).isPresent());
        foo.setBar(null);
        assertFalse(getter.get(foo).isPresent());
    }

    @Test
    @DisplayName("SafeGetter then using SafeGetter parameter")
    void then_1() {
        SafeGetter<Bar, String> messageGetter = SafeGetter.of(Bar::getMessage);
        SafeGetter<Foo, String> getter = SafeGetter.of(Foo::getBar).then(messageGetter);
        assertTrue(getter.get(foo).isPresent());
        foo.getBar().setMessage(null);
        assertFalse(getter.get(foo).isPresent());
        foo.setBar(null);
        assertFalse(getter.get(foo).isPresent());
    }

    @Test
    void setWith() {
        BiConsumer<Bar, String> biConsumer = Bar::setMessage;
        SafeSetter<Foo, Bar, String> setter = SafeGetter.of(Foo::getBar).setWith(biConsumer);
        assertNotNull(setter);
        Foo f = new Foo();
        setter.set(f, FOO_BARRED);
        assertNull(f.getBar());
        f.setBar(new Bar());
        setter.set(f, FOO_BARRED);
        assertEquals(FOO_BARRED, f.getBar().getMessage());
    }
}