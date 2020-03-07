package com.mcana.statemachine;

import java.util.Objects;

public interface Guard<S extends Enum<S>, E extends Enum<E>>{
    boolean test(StateContext<S, E> context);

    default Guard<S, E> negate() {
        return context -> !test(context);
    }

    default Guard<S, E> and(Guard<S, E> other) {
        Objects.requireNonNull(other);
        return context -> test(context) && other.test(context);
    }

    default Guard<S, E> or(Guard<S, E> other) {
        Objects.requireNonNull(other);
        return context -> test(context) || other.test(context);
    }
}