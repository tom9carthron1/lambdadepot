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

class TestConsumers {

    static void printOutHelloWorld() {
        System.out.print("hello, world");
    }

    static void printErrHelloWorld() {
        System.err.print("hello, world");
    }

    static void printOut(String s) {
        System.out.print(s);
    }

    static void printErr(String s) {
        System.err.print(s);
    }

    static void printOut1(String template, String arg1) {
        System.out.print(String.format(template, arg1));
    }

    static void printErr1(String template, String arg1) {
        System.err.print(String.format(template, arg1));
    }

    static void printOut2(String template, String arg1, String arg2) {
        System.out.print(String.format(template, arg1, arg2));
    }

    static void printErr2(String template, String arg1, String arg2) {
        System.err.print(String.format(template, arg1, arg2));
    }

    static void printOut3(String template, String arg1, String arg2, String arg3) {
        System.out.print(String.format(template, arg1, arg2, arg3));
    }

    static void printErr3(String template, String arg1, String arg2, String arg3) {
        System.err.print(String.format(template, arg1, arg2, arg3));
    }
}
