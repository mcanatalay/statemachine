package com.mcana.statemachine.builder;

public interface MainPayloadBuilder<S extends Enum<S>, E extends Enum<E>>{
    MainPayloadBuilder<S,E> push(Object key, Object message);
    void end();
}