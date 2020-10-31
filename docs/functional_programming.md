### Functional Programming Interfaces and Utilities

Index                                         |
----------------------------------------------|
[Overview](#overview)                         |
[Functions](#functions)                       |
[Predicates](#predicates)                     |
[Consumers](#consumers)                       |
[Suppliers](#suppliers)                       |
[Additional Resources](#additional-resources) |

# Overview
`lambdadepot` adds interfaces that extend the existing Java functional interfaces as well as utility methods for common predicates that are frequently encountered.


# Functions

Arity | Java                                                                                                  | lambdadepot                |
------|-------------------------------------------------------------------------------------------------------|----------------------------|
0     | [`Supplier<R>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html)           | `Function0<R>`             |
1     | [`Function<T, R>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)        | `Function1<T, R>`          |
2     | [`BiFunction<T, U, R>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html) | `Function2<T, U, R>`       |
3     |                                                                                                       | `Function3<T, U, V, R>`    |
4     |                                                                                                       | `Function4<T, U, V, W, R>` |

# Predicates

Arity | Java                                                                                                 | lambdadepot              |
------|------------------------------------------------------------------------------------------------------|--------------------------|
0     | [`Supplier<Boolean>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html)    | `Predicate0`             |
1     | [`Predicate<T>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)        | `Predicate1<T>`          |
2     | [`BiPredicate<T, U>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiPredicate.html) | `Predicate2<T, U>`       |
3     |                                                                                                      | `Predicate3<T, U, V>`    |
4     |                                                                                                      | `Predicate4<T, U, V, W>` |

# Consumers
Arity | Java                                                                                               | lambdadepot             |
------|----------------------------------------------------------------------------------------------------|-------------------------|
0     | [`Runnable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)                    | `Consumer0`             |
1     | [`Consumer<T>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)        | `Consumer1<T>`          |
2     | [`BiConsumer<T, U>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiConsumer.html) | `Consumer2<T, U>`       |
3     |                                                                                                    | `Consumer3<T, U, V>`    |
4     |                                                                                                    | `Consumer4<T, U, V, W>` |

# Suppliers

# Additional Resources
* [Microsoft - Functional Programming vs. Imperative Programming](https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/concepts/linq/functional-programming-vs-imperative-programming)
* [JetBrainsTV (Venkat Subramaniam) - Functional Programming with Java 8](https://www.youtube.com/watch?v=Ee5t_EGjv0A)
* [Manning Books - Functional Programming in Java, by Pierre-Yves Saumont](https://www.manning.com/books/functional-programming-in-java)
