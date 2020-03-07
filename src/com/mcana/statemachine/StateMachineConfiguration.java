package com.mcana.statemachine;

import java.util.EnumSet;

public interface StateMachineConfiguration<S extends Enum<S>, E extends Enum<E>>{
    void configure(EnumSet<S> states, EnumSet<E> events, S initial, S end);

    void configure(EnumSet<S> states, EnumSet<E> events, S initial);
}