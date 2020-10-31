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

package io.lambdadepot.collection;

import io.lambdadepot.function.Function1;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A collection of methods to assist operating on {@link java.util.Set}s
 * in a functional manner.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Sets {
    private Sets() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a set consisting of the results of applying the given
     * function to the elements of this set.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link Set} of mapped result type
     */
    public static <T, R> Function1<Set<T>, Set<R>> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return set -> {
            Objects.requireNonNull(set, "set");
            return set.stream()
                .map(mapper)
                .collect(Collectors.toSet());
        };
    }

    /**
     * Returns a set consisting of the results of replacing each element of
     * this set with the contents of a mapped set produced by applying
     * the provided mapping function to each element.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link Set} of mapped result type
     */
    public static <T, R> Function1<Set<T>, Set<R>> flatMap(Function<? super T, Set<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return set -> {
            Objects.requireNonNull(set, "set");
            return set.stream()
                .map(mapper)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        };
    }

    /**
     * Returns a set consisting of the elements of this set that match
     * the given predicate.
     *
     * @param predicate predicate to apply to each element to determine if it
     *                  should be included
     * @param <T>       element type of the set
     * @return set containing only the elements that match the provided predicate
     */
    public static <T> Function1<Set<T>, Set<T>> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return set -> Objects.requireNonNull(set, "set").stream()
            .filter(predicate)
            .collect(Collectors.toSet());
    }
}
