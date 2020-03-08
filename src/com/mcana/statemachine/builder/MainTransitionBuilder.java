package com.mcana.statemachine.builder;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface MainTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
    InternalTransitionBuilder<S,E> internal();
    ExternalTransitionBuilder<S,E> external();
    void end();

    public interface InternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        InitStateInternalTransitionBuilder<S,E> state(S state);
    }

    public interface InitStateInternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        EventInternalTransitionBuilder<S,E> event(E event);
    }

    public interface EventInternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        ActionTransitionBuilder<S,E> action(Action<S,E> action);
        GuardTransitionBuilder<S,E> guard(Guard<S,E> guard);
        PeriodTransitionBuilder<S,E> period(Long period);
    }

    public interface ExternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        InitStateExternalTransitionBuilder<S,E> init(S init);
    }

    public interface InitStateExternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        NextStateExternalTransitionBuilder<S,E> next(S next);
    }

    public interface NextStateExternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        EventExternalTransitionBuilder<S,E> event(E event);
    }

    public interface EventExternalTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        MainTransitionBuilder<S,E> and();
        ActionTransitionBuilder<S,E> action(Action<S,E> action);
        GuardTransitionBuilder<S,E> guard(Guard<S,E> guard);
    }

    public interface PeriodTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        ActionTransitionBuilder<S,E> action(Action<S,E> action);
        GuardTransitionBuilder<S,E> guard(Guard<S,E> guard);
    }

    public interface GuardTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        MainTransitionBuilder<S,E> and();
        ActionTransitionBuilder<S,E> action(Action<S,E> action);
    }

    public interface ActionTransitionBuilder<S extends Enum<S>, E extends Enum<E>>{
        MainTransitionBuilder<S,E> and();
    }
}