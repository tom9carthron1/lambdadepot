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

import java.util.stream.IntStream;

public class TestPredicates {

    public static boolean isEven(int i) {
        return i % 2 == 0;
    }

    public static boolean isEven2(int i1, int i2) {
        return IntStream.of(i1, i2)
                .allMatch(TestPredicates::isEven);
    }

    public static boolean isEven3(int i1, int i2, int i3) {
        return IntStream.of(i1, i2, i3)
                .allMatch(TestPredicates::isEven);
    }

    public static boolean isEven4(int i1, int i2, int i3, int i4) {
        return IntStream.of(i1, i2, i3, i4)
                .allMatch(TestPredicates::isEven);
    }

    public static boolean isGreaterThan100(int i) {
        return i > 100;
    }

    public static boolean isGreaterThan100_2(int i1, int i2) {
        return IntStream.of(i1, i2)
                .allMatch(TestPredicates::isGreaterThan100);
    }

    public static boolean isGreaterThan100_3(int i1, int i2, int i3) {
        return IntStream.of(i1, i2, i3)
                .allMatch(TestPredicates::isGreaterThan100);
    }

    public static boolean isGreaterThan100_4(int i1, int i2, int i3, int i4) {
        return IntStream.of(i1, i2, i3, i4)
                .allMatch(TestPredicates::isGreaterThan100);
    }

    public static boolean allEquals2(String s, int i) {
        return isInteger(s) && Integer.parseInt(s) == i;
    }

    public static boolean allEquals3(String s, int i, float f) {
        return isInteger(s) && Integer.parseInt(s) == i && i == f;
    }

    public static boolean allEquals4(String s, int i, float f, double d) {
        return isInteger(s) && Integer.parseInt(s) == i && i == f && i == d;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
