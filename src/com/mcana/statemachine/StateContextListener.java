package com.mcana.statemachine;

public interface StateContextListener<S extends Enum<S>, E extends Enum<E>>{
    void onVariableChange(Object key, Object prevValue, Object currentValue);

    void onTransition(TransitionContext<S,E> context);

    void onStart();

    void onEnd();
}