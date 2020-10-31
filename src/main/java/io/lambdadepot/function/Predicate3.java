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

/**
 * Represents a predicate (boolean-valued function) of three arguments. This is
 * the three-arity specialization of {@link Predicate1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, Object, Object)}.</p>
 *
 * @param <T1> the type of the first argument to the predicate
 * @param <T2> the type of the second argument to the predicate
 * @param <T3> the type of the third argument to the predicate
 * @see Predicate1
 */
@FunctionalInterface
public interface Predicate3<T1, T2, T3> {

    /**
     * Gets a method reference/lambda expression as a Predicate3 instance.
     *
     * @param reference Predicate reference
     * @param <T>       the type of the input to the predicate
     * @param <U>       the type of the second argument to the predicate
     * @param <V>       the type of the third argument to the predicate
     * @return method reference/lambda expression as a Predicate3 instance
     * @throws NullPointerException if reference is null
     */
    static <T, U, V> Predicate3<T, U, V> of(Predicate3<T, U, V> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t1 the first input argument
     * @param t2 the second input argument
     * @param t3 the third input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(T1 t1, T2 t2, T3 t3);

    /**
     * A partial application of {@code t1} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @return Predicate2 instance expecting the second and third arguments to the predicate
     */
    default Predicate2<T2, T3> partialApply(T1 t1) {
        return (t2, t3) -> test(t1, t2, t3);
    }

    /**
     * A partial application of {@code t1} and {@code t2} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @param t2 the second argument to test with the predicate
     * @return Predicate1 instance expecting the third argument to the predicate
     */
    default Predicate1<T3> partialApply(T1 t1, T2 t2) {
        return t3 -> test(t1, t2, t3);
    }

    /**
     * A partial application of {@code t1}, {@code t2} and {@code t3} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @param t2 the second argument to test with the predicate
     * @param t3 the third argument to test with the predicate
     * @return Predicate0 instance expecting no arguments
     */
    default Predicate0 partialApply(T1 t1, T2 t2, T3 t3) {
        return () -> test(t1, t2, t3);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate3<T1, T2, T3> and(Predicate3<T1, T2, T3> other) {
        Objects.requireNonNull(other, "other");
        return (t1, t2, t3) -> test(t1, t2, t3) && other.test(t1, t2, t3);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default Predicate3<T1, T2, T3> negate() {
        return (t1, t2, t3) -> !test(t1, t2, t3);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate3<T1, T2, T3> or(Predicate3<T1, T2, T3> other) {
        Objects.requireNonNull(other, "other");
        return (t1, t2, t3) -> test(t1, t2, t3) || other.test(t1, t2, t3);
    }

    /**
     * Returns a predicate that accepts the arguments to this predicate
     * in reversed order.
     *
     * @return a predicate that accepts the arguments to this predicate
     * in reversed order
     */
    default Predicate3<T3, T2, T1> reverse() {
        return (t3, t2, t1) -> test(t1, t2, t3);
    }
}
