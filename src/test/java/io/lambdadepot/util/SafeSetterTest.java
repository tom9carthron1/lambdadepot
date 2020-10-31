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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SafeSetterTest {
    private static final SafeSetter<Foo, Bar, String> SAFE_SETTER = SafeGetter.of(Foo::getBar).setWith(Bar::setMessage);

    @Test
    void set() {
        Foo foo = new Foo();
        assertFalse(SAFE_SETTER.set(foo, "Foo barred"));
        foo.setBar(new Bar());
        assertTrue(SAFE_SETTER.set(foo, "Foo barred"));
        assertEquals("Foo barred", foo.getBar().getMessage());
    }
}