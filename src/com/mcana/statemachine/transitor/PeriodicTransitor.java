package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface PeriodicTransitor<S extends Enum<S>, E extends Enum<E>>{
    void setInternalPeriodicTransition(S current, E event, Long period, Guard<S,E> guard, Action<S, E> action);

    void setInternalPeriodicTransition(S current, E event, Long period, Action<S, E> action);

}