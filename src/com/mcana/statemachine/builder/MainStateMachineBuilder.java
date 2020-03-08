package com.mcana.statemachine.builder;

import java.util.EnumSet;

public interface MainStateMachineBuilder<S extends Enum<S>, E extends Enum<E>>{
    StatesStateMachineBuilder<S,E> states(EnumSet<S> states);

    public interface StatesStateMachineBuilder<S extends Enum<S>, E extends Enum<E>>{
        EventsStateMachineBuilder<S,E> events(EnumSet<E> events);
    }

    public interface EventsStateMachineBuilder<S extends Enum<S>, E extends Enum<E>>{
        InitialStateMachineBuilder<S,E> initial(S initial);
    }

    public interface InitialStateMachineBuilder<S extends Enum<S>, E extends Enum<E>>{
        void end(S end);
    }
}