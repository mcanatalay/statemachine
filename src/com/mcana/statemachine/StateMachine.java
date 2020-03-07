package com.mcana.statemachine;

public interface StateMachine<S extends Enum<S>, E extends Enum<E>>{
    void start();

    void sendEvent(E event);

    void sendEvent(E event, Payload payload);

    StateContext<S,E> getContext();
}