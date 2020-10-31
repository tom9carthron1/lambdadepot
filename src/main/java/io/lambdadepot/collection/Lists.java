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
import io.lambdadepot.util.Predicates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A collection of methods to assist operating on {@link java.util.List}s
 * in a functional manner.
 */
public final class Lists {
    private Lists() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a list consisting of the results of applying the given
     * function to the elements of this list.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link List} of mapped result type
     */
    public static <T, R> Function1<List<T>, List<R>> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return list -> {
            Objects.requireNonNull(list, "list");
            return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
        };
    }

    /**
     * Returns a list consisting of the results of replacing each element of
     * this list with the contents of a mapped list produced by applying
     * the provided mapping function to each element.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link List} of mapped result types
     */
    public static <T, R> Function1<List<T>, List<R>> flatMap(Function<? super T, List<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return list -> {
            Objects.requireNonNull(list, "list");
            return list.stream()
                .map(mapper)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        };
    }

    /**
     * Filters out any Objects in the list that do not return true from the {@code predicate} test.
     *
     * @param predicate Supplied {@link Predicate} used to filter Objects in the list
     * @param <T>       type
     * @return {@link List} of T that test true from the supplied {@code predicate}.
     * @throws NullPointerException if the {@code predicate} is null or if the applied list is null.
     *                              <ul>
     *                              <li>Ex. filterOnList(myValidPredicate).apply(null) = NPE</li>
     *                              <li>Ex. filterOnList(null).apply(notNullList) = NPE</li>
     *                              <li>Ex. filterOnList(null).apply(null) = NPE</li>
     *                              </ul>
     */
    public static <T> Function1<List<T>, List<T>> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return list -> {
            Objects.requireNonNull(list, "list");
            return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        };
    }

    /**
     * Attempts to get the first {@code T} from the {@code List<T>}.
     * A null input list is acceptable, and will just return empty.
     * <ul>
     * <li>getFirst(null) = Optional.empty()</li>
     * <li>getFirst(new ArrayList()) = Optional.empty()</li>
     * <li>getFirst(listOfNullTees) = Optional.empty()</li>
     * <li>getFirst(listOfNonNullTees) = Optional.of(teeFromIndexZero)</li>
     * </ul>
     *
     * @param list for getting {@code List::get(0)}
     * @param <T>  type of elements in the list
     * @return {@code Optional#of(T)} or {@code Optional#empty()}
     */
    public static <T> Optional<T> getHead(List<T> list) {
        return Optional.ofNullable(list)
            .filter(Predicates.not(List::isEmpty))
            .map(l -> l.get(0));
    }

    /**
     * Attempts to get the last {@code T} from the {@code List<T>}.
     * A null input list is acceptable, and will just return empty.
     * <ul>
     * <li>getTail(null) = Optional.empty()</li>
     * <li>getTail(new ArrayList()) = Optional.empty()</li>
     * <li>getTail(listOfNullTees) = Optional.empty()</li>
     * <li>getTail(listOfNonNullTees) = Optional.of(teeFromIndexN)</li>
     * </ul>
     *
     * @param list for getting {@code List::get(size - 1)}
     * @param <T>  type of elements in the list
     * @return {@code Optional#of(T)} or {@code Optional#empty()}
     */
    public static <T> Optional<T> getTail(List<T> list) {
        return Optional.ofNullable(list)
            .filter(Predicates.not(List::isEmpty))
            .map(l -> l.get(l.size() - 1));
    }

    /**
     * Attempts to get {@code T} from the {@code List<T>} at {@code index}.
     * A null input list is acceptable, and will just return empty.
     * <ul>
     * <li>get(null, n) = Optional.empty()</li>
     * <li>get(new ArrayList(), n) = Optional.empty()</li>
     * <li>get(listOfNullTees, n) = Optional.empty()</li>
     * <li>get(listOfNonNullTees, n) = Optional.of(teeFromIndexN)</li>
     * </ul>
     *
     * @param list  for getting {@code List::get(n)}
     * @param index index of the element to return
     * @param <T>   type of elements in the list
     * @return {@code Optional#of(T)} or {@code Optional#empty()}
     */
    public static <T> Optional<T> get(List<T> list, int index) {
        return Optional.ofNullable(list)
            .filter(l -> l.size() > index)
            .map(l -> l.get(index));
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements array containing elements to be added to this list
     * @param <T>      element type
     * @return a function that appends the given elements to a list
     */
    @SafeVarargs
    public static <T> Function1<List<T>, List<T>> append(T... elements) {
        return append(Arrays.asList(elements));
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements collection containing elements to be added to this list
     * @param <T>      element type
     * @return a function that appends the given elements to a list
     */
    public static <T> Function1<List<T>, List<T>> append(Collection<T> elements) {
        Objects.requireNonNull(elements, "elements");
        return list -> {
            Objects.requireNonNull(list, "list");
            List<T> newList = new ArrayList<>(list);
            newList.addAll(elements);
            return Collections.unmodifiableList(newList);
        };
    }

    /**
     * Prepends all of the elements in the specified collection to the beginning of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements array containing elements to be added to this list
     * @param <T>      element type
     * @return a function that prepends the given elements to a list
     */
    @SafeVarargs
    public static <T> Function1<List<T>, List<T>> prepend(T... elements) {
        return prepend(Arrays.asList(elements));
    }

    /**
     * Prepends all of the elements in the specified collection to the beginning of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements collection containing elements to be added to this list
     * @param <T>      element type
     * @return a function that prepends the given elements to a list
     */
    public static <T> Function1<List<T>, List<T>> prepend(Collection<T> elements) {
        Objects.requireNonNull(elements, "elements");
        return list -> {
            Objects.requireNonNull(list, "list");
            List<T> newList = new ArrayList<>(list);
            newList.addAll(0, elements);
            return Collections.unmodifiableList(newList);
        };
    }
}
