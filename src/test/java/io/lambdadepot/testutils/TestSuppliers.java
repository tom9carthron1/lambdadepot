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

package io.lambdadepot.testutils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

import static java.util.stream.Collectors.toList;

public class TestSuppliers {

    private static Random random;

    public static synchronized Random getRandom() {
        if (Objects.isNull(random)) {
            random = new Random();
        }
        return random;
    }

    public static List<Integer> generateIntList(int size) {
        return getRandom().ints(size, 0, Integer.MAX_VALUE)
                .boxed()
                .collect(toList());
    }

    public static List<Integer> generateIntList(int size, int lowerBound, int upperBound) {
        return getRandom().ints(size, lowerBound, upperBound >= lowerBound ? (upperBound + 1) : lowerBound)
                .boxed()
                .collect(toList());
    }

    public static List<String> generateStringList(int size, int charLength) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add(RandomStringUtils.randomAlphabetic(charLength));
        }
        return strings;
    }

    public static String loadResource(String fileName) throws URISyntaxException, IOException {
        URI uri = TestSuppliers.class.getClassLoader().getResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri)));
    }
}
