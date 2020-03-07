package com.mcana.statemachine;

import java.util.Objects;

public interface Action<S extends Enum<S>, E extends Enum<E>> {
    void apply(StateContext<S, E> context);

    default Action<S, E> andThen(Action<S,E> after) {
        Objects.requireNonNull(after);
        return context -> {
            apply(context);
            after.apply(context);
        };
    }
}