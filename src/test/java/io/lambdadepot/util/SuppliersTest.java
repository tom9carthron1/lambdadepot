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

import io.lambdadepot.function.TestFunctions;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SuppliersTest {

    @Test
    void testConstructor() {
        assertEquals(1, Suppliers.class.getDeclaredConstructors().length);
        Constructor constructor = Suppliers.class.getDeclaredConstructors()[0];
        assertFalse(constructor.isAccessible());
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    void testOf() {
        assertNotNull(Suppliers.of(TestFunctions::getString));
        assertThrows(NullPointerException.class, () -> Suppliers.of(null));
    }
}
