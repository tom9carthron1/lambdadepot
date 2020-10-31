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
 * Empty {@link Result} implementation.
 *
 * <p>Empty {@link Result}s have no value and no error.
 *
 * @param <T> value type
 */
public final class ResultEmpty<T> extends Result<T> {
    /**
     * Cached empty reference.
     */
    static final Result<?> EMPTY = new ResultEmpty<>();

    ResultEmpty() {
    }

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return a {@link Result} wrapping the value returned from the
     * operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @return the value returned from applying this {@link Result}'s value to
     * {@code mapper} wrapped as a {@link Result}
     * @throws NullPointerException if mapper is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R> Result<R> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return (Result<R>) this;
    }

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return the {@link Result} returned from the operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @return the {@link Result} returned from applying this {@link Result}'s
     * value to {@code mapper}
     * @throws NullPointerException if mapper is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return (Result<R>) this;
    }

    /**
     * If this {@link Result} is success, test the value and continue if
     * {@code predicate} returns true. Otherwise complete as empty.
     *
     * @param predicate the predicate to evaluate
     * @return a filtered {@link Result}
     * @throws NullPointerException if predicate is null
     */
    @Override
    public Result<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return this;
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
    @Override
    public Result<T> ifFailureMap(Function<? super Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

    /**
     * If this {@link Result} is failure and the error matches the given exception type, apply
     * {@code mapper} to the error and continue as failure with the mapped error returned from
     * the operation.
     *
     * @param exceptionType the exception type to map
     * @param mapper        the function to apply go the error if this {@link Result}
     *                      is failure and the error matches the given exception type
     * @return the error returned from applying this {@link Result}'s error to
     * {@code mapper} wrapped in a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if mapper is null
     */
    @Override
    public <E extends Throwable> Result<T> ifFailureMap(Class<E> exceptionType, Function<? super E, ? extends Throwable> mapper) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

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
    @Override
    public Result<T> ifFailureMap(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

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
    @Override
    public Result<T> ifFailureResume(Function<? super Throwable, Result<T>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * apply {@code mapper} to the error and continue with the {@link Result} returned
     * from the operation.
     *
     * @param exceptionType the exception type to map
     * @param mapper        the function to apply to the error if this {@link Result}
     *                      is failure and the error matches the given exception type
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if mapper is null
     */
    @Override
    public <E extends Throwable> Result<T> ifFailureResume(Class<E> exceptionType, Function<? super E, Result<T>> mapper) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

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
    @Override
    public Result<T> ifFailureResume(Predicate<? super Throwable> predicate, Function<? super Throwable, Result<T>> mapper) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(mapper, "mapper");
        return this;
    }

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue is null
     */
    @Override
    public Result<T> ifFailureReturn(T fallbackValue) {
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue is null
     */
    @Override
    public <E extends Throwable> Result<T> ifFailureReturn(Class<E> exceptionType, T fallbackValue) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

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
    @Override
    public Result<T> ifFailureReturn(Predicate<? super Throwable> predicate, T fallbackValue) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue supplier is null
     */
    @Override
    public Result<T> ifFailureReturn(Supplier<T> fallbackValue) {
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue supplier is null
     */
    @Override
    public <E extends Throwable> Result<T> ifFailureReturn(Class<E> exceptionType, Supplier<T> fallbackValue) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

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
    @Override
    public Result<T> ifFailureReturn(Predicate<? super Throwable> predicate, Supplier<T> fallbackValue) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return this;
    }

    /**
     * If this {@link Result} is empty, return {@code defaultValue} as a {@link Result}.
     *
     * @param defaultValue the alternate value to use if this {@link Result} is empty
     * @return {@code defaultValue} as a {@link Result}
     * @throws NullPointerException if defaultValue is null
     */
    @Override
    public Result<T> ifEmptyReturn(T defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        return success(defaultValue);
    }

    /**
     * If this {@link Result} is empty, return the value returned from
     * {@code defaultvalueSupplier} as a {@link Result}.
     *
     * @param defaultValueSupplier the alternate value supplier to use if this
     *                             {@link Result} is empty
     * @return the value returned from {@code defaultValueSupplier} as a {@link Result}
     * @throws NullPointerException if {@code defaultValueSupplier} is null
     */
    @Override
    public Result<T> ifEmptyReturn(Supplier<T> defaultValueSupplier) {
        Objects.requireNonNull(defaultValueSupplier, "defaultValueSupplier");
        return ifEmptyReturn(defaultValueSupplier.get());
    }

    /**
     * If this {@link Result} is empty, return {@code alternative}.
     *
     * @param alternative the alternate {@link Result} to use if
     *                    this {@link Result} is empty
     * @return {@code alternative}
     * @throws NullPointerException if alternative is null
     */
    @Override
    public Result<T> ifEmptyResume(Result<T> alternative) {
        Objects.requireNonNull(alternative, "alternative");
        return alternative;
    }

    /**
     * If this {@link Result} is empty, return the {@link Result} returned from
     * {@code alternativeSupplier}.
     *
     * @param alternativeSupplier the alternate {@link Result} supplier to use
     *                            if this {@link Result} is empty
     * @return the {@link Result} returned from {@code alternativeSupplier}
     * @throws NullPointerException if alternativeSupplier is null
     */
    @Override
    public Result<T> ifEmptyResume(Supplier<Result<T>> alternativeSupplier) {
        Objects.requireNonNull(alternativeSupplier, "alternativeSupplier");
        return ifEmptyResume(alternativeSupplier.get());
    }

    /**
     * Add behavior triggered if the {@code Result} is successful.
     *
     * @param onSuccess the consumer to call if the {@link Result} is successful
     * @return an observed {@link Result}
     * @throws NullPointerException if onSuccess is null
     */
    @Override
    public Result<T> peekIfSuccess(Consumer<? super T> onSuccess) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        return this;
    }

    /**
     * Add behavior triggered if the {@code Result} is failure.
     *
     * @param onFailure the callback to call if the {@link Result} is failure
     * @return an observed {@link Result}
     * @throws NullPointerException if onFailure is null
     */
    @Override
    public Result<T> peekIfFailure(Consumer<? super Throwable> onFailure) {
        Objects.requireNonNull(onFailure, "onFailure");
        return this;
    }

    /**
     * Add behavior triggered if the {@code Result} is failure with an error matching
     * the given exception type.
     *
     * @param exceptionType the exception type to handle
     * @param onFailure     the callback to call if the {@link Result} is failure and the
     *                      error matches the exception type
     * @return an observed {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if onFailure is null
     */
    @Override
    public <E extends Throwable> Result<T> peekIfFailure(Class<E> exceptionType, Consumer<? super E> onFailure) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(onFailure, "onFailure");
        return this;
    }

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
    @Override
    public Result<T> peekIfFailure(Predicate<? super Throwable> predicate, Consumer<? super Throwable> onFailure) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(onFailure, "onFailure");
        return this;
    }

    /**
     * Add behavior triggered if the {@code Result} is empty.
     *
     * @param onEmpty the callback to call if the {@code Result} is empty
     * @return an observed {@link Result}
     * @throws NullPointerException if onEmpty is null
     */
    @Override
    public Result<T> peekIfEmpty(Runnable onEmpty) {
        Objects.requireNonNull(onEmpty, "onEmpty");
        onEmpty.run();
        return this;
    }

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     *
     * @param successAction value consumer
     * @throws NullPointerException if {@code successAction} is null
     */
    @Override
    public void ifSuccess(Consumer<T> successAction) {
        Objects.requireNonNull(successAction, "successAction");
    }

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param successAction value consumer
     * @param failureAction error consumer
     */
    @Override
    public void ifSuccessOrFailure(Consumer<T> successAction, Consumer<Throwable> failureAction) {
        Objects.requireNonNull(successAction, "successAction");
        Objects.requireNonNull(failureAction, "failureAction");
    }

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param successAction value consumer
     * @param emptyAction   empty consumer
     */
    @Override
    public void ifSuccessOrEmpty(Consumer<T> successAction, Runnable emptyAction) {
        Objects.requireNonNull(successAction, "successAction");
        Objects.requireNonNull(emptyAction, "emptyAction");
        emptyAction.run();
    }

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param successAction value consumer
     * @param failureAction error consumer
     * @param emptyAction   empty consumer
     */
    @Override
    public void ifSuccessOrElse(Consumer<T> successAction, Consumer<Throwable> failureAction, Runnable emptyAction) {
        Objects.requireNonNull(successAction, "successAction");
        Objects.requireNonNull(failureAction, "failureAction");
        Objects.requireNonNull(emptyAction, "emptyAction");
        emptyAction.run();
    }

    /**
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param failureAction error consumer
     */
    @Override
    public void ifFailure(Consumer<Throwable> failureAction) {
        Objects.requireNonNull(failureAction, "failureAction");
    }

    /**
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param failureAction error consumer
     * @param emptyAction   empty consumer
     */
    @Override
    public void ifFailureOrEmpty(Consumer<Throwable> failureAction, Runnable emptyAction) {
        Objects.requireNonNull(failureAction, "failureAction");
        Objects.requireNonNull(emptyAction, "emptyAction");
        emptyAction.run();
    }

    /**
     * If this {@link Result} is empty, consume {@code emptyAction}.
     *
     * @param emptyAction empty consumer
     */
    @Override
    public void ifEmpty(Runnable emptyAction) {
        Objects.requireNonNull(emptyAction, "emptyAction");
        emptyAction.run();
    }

    /**
     * Returns if this {@link Result} is success or not.
     *
     * @return true if this {@link Result} is success
     */
    @Override
    public boolean isSuccess() {
        return false;
    }

    /**
     * Returns if this {@link Result} is failure or not.
     *
     * @return true if this {@link Result} is failure
     */
    @Override
    public boolean isFailure() {
        return false;
    }

    /**
     * Returns if this {@link Result} is empty or not.
     *
     * @return true if this {@link Result} is empty
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * If the {@link Result} is success, return the value.
     *
     * @return this {@link Result}'s value
     * @throws NoSuchElementException if this {@link Result} is not success
     */
    @Override
    public T getValue() {
        throw new NoSuchElementException("No value present");
    }

    /**
     * If this {@link Result} is failure, return the error.
     *
     * @return this {@link Result}'s error
     * @throws NoSuchElementException if this {@link Result} is not failure
     */
    @Override
    public Throwable getError() {
        throw new NoSuchElementException("No error present");
    }

    /**
     * Return the contained value if instance of {@code ResultSuccess},
     * but if instance of {@code ResultEmpty} throw the exception from the
     * provided supplier. If instance of {@code ResultFailure} the contained
     * error will be wrapped in a {@code RuntimeException} and then thrown.
     *
     * <p>Note: A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example, {@code IllegalStateException::new}
     *
     * @param exceptionSupplier The supplier which will return the exception to
     *                          be thrown
     * @return the present value
     * @throws X                    if there is no value present
     * @throws NullPointerException if no value is present and
     *                              {@code exceptionSupplier} is null
     */
    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        throw exceptionSupplier.get();
    }

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
     * @return the present value
     * @throws X                    if there is no value present
     * @throws Y                    if there is an error present
     * @throws NullPointerException if no value is present and either
     *                              {@code Supplier} is null
     */
    @Override
    public <X extends Throwable, Y extends Throwable> T orElseThrow(Supplier<? extends X> emptySupplier, Supplier<? extends Y> failureSupplier) throws X, Y {
        Objects.requireNonNull(emptySupplier, "emptySupplier");
        Objects.requireNonNull(failureSupplier, "failureSupplier");
        throw emptySupplier.get();
    }
}
