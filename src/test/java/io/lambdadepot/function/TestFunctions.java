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

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class TestFunctions {
    static final Function1<String, String> STRING_INT_STRING = s -> String.valueOf(Integer.valueOf(s));

    public static String getString() {
        return "hello, world";
    }

    public static String toUpperCase(String s) {
        return s.toUpperCase();
    }

    public static String reverse(String s) {
        return StringUtils.reverse(s);
    }

    public static String concat2(String s1, String s2) {
        Objects.requireNonNull(s1, "s1");
        Objects.requireNonNull(s2, "s2");
        return s1 + s2;
    }

    public static String concat3(String s1, String s2, String s3) {
        Objects.requireNonNull(s1, "s1");
        Objects.requireNonNull(s2, "s2");
        Objects.requireNonNull(s3, "s3");
        return s1 + s2 + s3;
    }

    public static String concat4(String s1, String s2, String s3, String s4) {
        Objects.requireNonNull(s1, "s1");
        Objects.requireNonNull(s2, "s2");
        Objects.requireNonNull(s3, "s3");
        Objects.requireNonNull(s4, "s4");
        return s1 + s2 + s3 + s4;
    }

    public static String concat3Strings(String a, String b, String c) {
        return a.concat(b).concat(c);
    }

    public static String concat4Strings(String a, String b, String c, String d) {
        return a.concat(b).concat(c).concat(d);
    }
}

