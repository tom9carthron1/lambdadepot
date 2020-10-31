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

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A tri-state value wrapper that is either:
 * <ul>
 * <li>success</li>
 * <li>failure</li>
 * <li>empty</li>
 * </ul>
 * 
 * <p>The success state contains a value but does not contain an exception.
 * The failure state does not contain a value but contains an exception.
 * The empty state contains neither a value or exception.
 *
 * @param <T> value type
 */
public abstract class Result<T> {
    Result() {
    }

    /**
     * Creates a new successful {@link Result} that wraps the given value.
     *
     * @param value the value to wrap
     * @param <T>   the value type
     * @return a successful {@link Result}
     */
    public static <T> Result<T> success(T value) {
        Objects.requireNonNull(value, "value");
        return new ResultSuccess<>(value);
    }

    /**
     * Creates a new successful {@link Result} that wraps the given value
     * if the value is non-null. If the given value is null, then an
     * empty {@link Result} is returned.
     *
     * @param value the value to wrap
     * @param <T>   the value type
     * @return a successful {@link Result} if the given value is non-null,
     * an empty {@link Result} otherwise
     */
    public static <T> Result<T> successOrEmpty(T value) {
        return Objects.nonNull(value) ? success(value) : empty();
    }

    /**
     * Creates a new failure {@link Result} that wraps the given exception.
     *
     * @param error the exception that occurred
     * @param <T>   the value type
     * @return a failure {@link Result}
     */
    public static <T> Result<T> failure(Throwable error) {
        Objects.requireNonNull(error, "error");
        return new ResultFailure<>(error);
    }

    /**
     * Creates a new empty {@link Result}.
     *
     * @param <T> the value type
     * @return an empty {@link Result}
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> empty() {
        return (Result<T>) ResultEmpty.EMPTY;
    }

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return a {@link Result} wrapping the value returned from the
     * operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @param <R>    the {@link Result} value type returned from {@code mapper}
     * @return the value returned from applying this {@link Result}'s value to
     * {@code mapper} wrapped as a {@link Result}
     * @throws NullPointerException if mapper is null
     */
    public abstract <R> Result<R> map(Function<T, R> mapper);

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return the {@link Result} returned from the operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @param <R>    the {@link Result} value type returned from {@code mapper}
     * @return the {@link Result} returned from applying this {@link Result}'s
     * value to {@code mapper}
     * @throws NullPointerException if mapper is null
     */
    public abstract <R> Result<R> flatMap(Function<T, Result<R>> mapper);

    /**
     * Applies this {@link Result} to {@code transformer} and returns the value
     * returned from the operation.
     *
     * @param transformer the function to apply this {@link Result} to
     * @param <R>         the output type returned from applying {@code transformer}
     * @return the output returned from applying {@code transformer} to this
     */
    public final <R> R transform(Function<? super Result<T>, R> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * If this {@link Result} is success, test the value and continue if
     * {@code predicate} returns true. Otherwise complete as empty.
     *
     * @param predicate the predicate to evaluate
     * @return a filtered {@link Result}
     * @throws NullPointerException if predicate is null
     */
    public abstract Result<T> filter(Predicate<T> predicate);

    /**
     * Transforms this {@link Result} into a {@link Result} with a boolean
     * value of whether this {@link Result} has a value or not.
     *
     * @return a {@link Result} with a boolean value of whether this
     * {@link Result} has a value or not.
     */
    public final Result<Boolean> hasValue() {
        return Result.success(isSuccess());
    }

    /**
     * Transforms this {@link Result} into a {@link Result} with a boolean
     * value of whether this {@link Result} has an error or not.
     *
     * @return a {@link Result} with a boolean value of whether this
     * {@link Result} has an error or not.
     */
    public final Result<Boolean> hasError() {
        return Result.success(isFailure());
    }

    /**
     * Transforms this {@link Result} into a {@link Result} with a boolean
     * value of whether this {@link Result} is empty or not.
     *
     * @return a {@link Result} with a boolean value of whether this
     * {@link Result} is empty or not.
     */
    public final Result<Boolean> hasNothing() {
        return Result.success(isEmpty());
    }

    /**
     * If this {@link Result} is failure, apply {@code mapper} to the error and
     * continue as failure with the mapped error returned from the operation.
     *
     * @param mapper the function to apply to the error if this {@link Result}
     *               is failure
     * @return the error returned from applying this {@link Result}'s error to
     * {@code mapper} wrapped in a {@link Result}
     * @throws NullPointerException if mapper is null
     */
    public abstract Result<T> ifFailureMap(Function<? super Throwable, ? extends Throwable> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given exception type, apply
     * {@code mapper} to the error and continue as failure with the mapped error returned from
     * the operation.
     *
     * @param exceptionType the exception type to map
     * @param mapper        the function to apply go the error if this {@link Result}
     *                      is failure and the error matches the given exception type
     * @param <E>           the type of the error to handle
     * @return the error returned from applying this {@link Result}'s error to
     * {@code mapper} wrapped in a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if mapper is null
     */
    public abstract <E extends Throwable> Result<T> ifFailureMap(Class<E> exceptionType, Function<? super E, ? extends Throwable> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given predicate, apply
     * {@code mapper} to the error and continue as failure with the mapped error returned from
     * the operation.
     *
     * @param predicate the matcher for errors to handle
     * @param mapper    the function to apply go the error if this {@link Result}
     *                  is failure and the error matches the given predicate
     * @return the error returned from applying this {@link Result}'s error to
     * {@code mapper} wrapped in a {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if mapper is null
     */
    public abstract Result<T> ifFailureMap(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Throwable> mapper);

    /**
     * If this {@link Result} is failure, apply {@code mapper} to the error and
     * continue with the {@link Result} returned from the operation.
     *
     * @param mapper the function to apply to the error if this {@link Result}
     *               is failure
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if mapper is null
     */
    public abstract Result<T> ifFailureResume(Function<? super Throwable, Result<T>> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * apply {@code mapper} to the error and continue with the {@link Result} returned
     * from the operation.
     *
     * @param exceptionType the exception type to map
     * @param mapper        the function to apply to the error if this {@link Result}
     *                      is failure and the error matches the given exception type
     * @param <E>           exception type
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if mapper is null
     */
    public abstract <E extends Throwable> Result<T> ifFailureResume(Class<E> exceptionType, Function<? super E, Result<T>> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * apply {@code mapper} to the error and continue with the {@link Result} returned
     * from the operation.
     *
     * @param predicate the matcher for errors to handle
     * @param mapper    the function to apply to the error if this {@link Result}
     *                  is failure and the error matches the given predicate
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if mapper is null
     */
    public abstract Result<T> ifFailureResume(Predicate<? super Throwable> predicate, Function<? super Throwable, Result<T>> mapper);

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue is null
     */
    public abstract Result<T> ifFailureReturn(T fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @param <E>           exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue is null
     */
    public abstract <E extends Throwable> Result<T> ifFailureReturn(Class<E> exceptionType, T fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param predicate     the matcher for errors to handle
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given predicate
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if defaultValue is null
     */
    public abstract Result<T> ifFailureReturn(Predicate<? super Throwable> predicate, T fallbackValue);

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue supplier is null
     */
    public abstract Result<T> ifFailureReturn(Supplier<T> fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @param <E>           exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue supplier is null
     */
    public abstract <E extends Throwable> Result<T> ifFailureReturn(Class<E> exceptionType, Supplier<T> fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param predicate     the matcher for errors to handle
     * @param fallbackValue the value supplier to use if this {@link Result} is failure and
     *                      the error matches the given predicate
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if defaultValue supplier is null
     */
    public abstract Result<T> ifFailureReturn(Predicate<? super Throwable> predicate, Supplier<T> fallbackValue);

    /**
     * If this {@link Result} is empty, return {@code defaultValue} as a {@link Result}.
     *
     * @param defaultValue the alternate value to use if this {@link Result} is empty
     * @return {@code defaultValue} as a {@link Result}
     * @throws NullPointerException if defaultValue is null
     */
    public abstract Result<T> ifEmptyReturn(T defaultValue);

    /**
     * If this {@link Result} is empty, return the value returned from
     * {@code defaultvalueSupplier} as a {@link Result}.
     *
     * @param defaultValueSupplier the alternate value supplier to use if this
     *                             {@link Result} is empty
     * @return the value returned from {@code defaultValueSupplier} as a {@link Result}
     * @throws NullPointerException if {@code defaultValueSupplier} is null
     */
    public abstract Result<T> ifEmptyReturn(Supplier<T> defaultValueSupplier);

    /**
     * If this {@link Result} is empty, return {@code alternative}.
     *
     * @param alternative the alternate {@link Result} to use if
     *                    this {@link Result} is empty
     * @return {@code alternative}
     * @throws NullPointerException if alternative is null
     */
    public abstract Result<T> ifEmptyResume(Result<T> alternative);

    /**
     * If this {@link Result} is empty, return the {@link Result} returned from
     * {@code alternativeSupplier}.
     *
     * @param alternativeSupplier the alternate {@link Result} supplier to use
     *                            if this {@link Result} is empty
     * @return the {@link Result} returned from {@code alternativeSupplier}
     * @throws NullPointerException if alternativeSupplier is null
     */
    public abstract Result<T> ifEmptyResume(Supplier<Result<T>> alternativeSupplier);

    /**
     * Add behavior triggered if the {@code Result} is successful.
     *
     * @param onSuccess the consumer to call if the {@link Result} is successful
     * @return an observed {@link Result}
     * @throws NullPointerException if onSuccess is null
     */
    public abstract Result<T> peekIfSuccess(Consumer<? super T> onSuccess);

    /**
     * Add behavior triggered if the {@code Result} is failure.
     *
     * @param onFailure the callback to call if the {@link Result} is failure
     * @return an observed {@link Result}
     * @throws NullPointerException if onFailure is null
     */
    public abstract Result<T> peekIfFailure(Consumer<? super Throwable> onFailure);

    /**
     * Add behavior triggered if the {@code Result} is failure with an error matching
     * the given exception type.
     *
     * @param exceptionType the exception type to handle
     * @param onFailure     the callback to call if the {@link Result} is failure and the
     *                      error matches the exception type
     * @param <E>           the type of the error to handle
     * @return an observed {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if onFailure is null
     */
    public abstract <E extends Throwable> Result<T> peekIfFailure(Class<E> exceptionType, Consumer<? super E> onFailure);

    /**
     * Add behavior triggered if the {@code Result} is failure with an error matching
     * the given predicate.
     *
     * @param predicate the matcher for errors to handle
     * @param onFailure the callback to call if the {@link Result} is failure and the
     *                  error matches the predicate
     * @return an observed {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if onFailure is null
     */
    public abstract Result<T> peekIfFailure(Predicate<? super Throwable> predicate, Consumer<? super Throwable> onFailure);

    /**
     * Add behavior triggered if the {@code Result} is empty.
     *
     * @param onEmpty the callback to call if the {@code Result} is empty
     * @return an observed {@link Result}
     * @throws NullPointerException if onEmpty is null
     */
    public abstract Result<T> peekIfEmpty(Runnable onEmpty);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     *
     * @param successAction {@link Consumer} of T
     * @throws NullPointerException if {@code successAction} is null
     */
    public abstract void ifSuccess(Consumer<T> successAction);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param successAction value consumer
     * @param failureAction error consumer
     */
    public abstract void ifSuccessOrFailure(Consumer<T> successAction, Consumer<Throwable> failureAction);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param successAction value consumer
     * @param emptyAction   empty consumer
     */
    public abstract void ifSuccessOrEmpty(Consumer<T> successAction, Runnable emptyAction);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param successAction value consumer
     * @param failureAction error consumer
     * @param emptyAction   empty consumer
     */
    public abstract void ifSuccessOrElse(Consumer<T> successAction, Consumer<Throwable> failureAction, Runnable emptyAction);

    /**
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param failureAction error consumer
     */
    public abstract void ifFailure(Consumer<Throwable> failureAction);

    /**
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param failureAction error consumer
     * @param emptyAction   empty consumer
     */
    public abstract void ifFailureOrEmpty(Consumer<Throwable> failureAction, Runnable emptyAction);

    /**
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param emptyAction empty consumer
     */
    public abstract void ifEmpty(Runnable emptyAction);

    /**
     * Returns if this {@link Result} is success or not.
     *
     * @return true if this {@link Result} is success
     */
    public abstract boolean isSuccess();

    /**
     * Returns if this {@link Result} is failure or not.
     *
     * @return true if this {@link Result} is failure
     */
    public abstract boolean isFailure();

    /**
     * Returns if this {@link Result} is empty or not.
     *
     * @return true if this {@link Result} is empty
     */
    public abstract boolean isEmpty();

    /**
     * If the {@link Result} is success, return the value.
     *
     * @return this {@link Result}'s value
     * @throws NoSuchElementException if this {@link Result} is not success
     */
    public abstract T getValue();

    /**
     * If this {@link Result} is failure, return the error.
     *
     * @return this {@link Result}'s error
     * @throws NoSuchElementException if this {@link Result} is not failure
     */
    public abstract Throwable getError();

    /**
     * Return the contained value if instance of {@code ResultSuccess},
     * but if instance of {@code ResultEmpty} throw the exception from the
     * provided supplier. If instance of {@code ResultFailure} the contained
     * error will be wrapped in a {@code RuntimeException} and then thrown.
     *
     * <p>Note: A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example, {@code IllegalStateException::new}.
     *
     * @param exceptionSupplier The supplier which will return the exception to
     *                          be thrown
     * @param <X>               exception type to throw
     * @return the present value
     * @throws X                    if there is no value present
     * @throws NullPointerException if no value is present and
     *                              {@code exceptionSupplier} is null
     */
    public abstract <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * Return the contained value if instance of {@code ResultSuccess},
     * but if instance of {@code ResultEmpty} throw the exception from the
     * provided supplier. If instance of {@code ResultFailure} the contained
     * error will be wrapped in a {@code RuntimeException} and then thrown.
     *
     * <p>Note: A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example, {@code IllegalStateException::new}
     *
     * @param emptySupplier   The supplier which will return the exception to
     *                        be thrown from the {@code ResultEmpty} state
     * @param failureSupplier The supplier which will return the exception to
     *                        be thrown from the {@code ResultFailure} state
     * @param <X1>             exception type to throw for empty scenario
     * @param <X2>             exception type to throw for failure scenario
     * @return the present value
     * @throws X1                    if there is no value present
     * @throws X2                    if there is an error present
     * @throws NullPointerException if no value is present and either
     *                              {@code Supplier} is null
     */
    public abstract <X1 extends Throwable, X2 extends Throwable> T orElseThrow(Supplier<? extends X1> emptySupplier, Supplier<? extends X2> failureSupplier) throws X1, X2;
}
