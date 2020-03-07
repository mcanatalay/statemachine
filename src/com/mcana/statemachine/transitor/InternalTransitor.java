package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface InternalTransitor<S extends Enum<S>, E extends Enum<E>>{
    void setInternalTransition(S current, E event, Guard<S,E> guard, Action<S, E> action);

    void setInternalTransition(S current, E event, Action<S, E> action);
}