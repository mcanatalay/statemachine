package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface StateTransitor<S extends Enum<S>, E extends Enum<E>>{
    TransitionType getType(S state, E event);

    S getTargetState(S state, E event);

    Guard<S, E> getGuard(S state, E event);

    Action<S, E> getAction(S state, E event);

    Long getPeriod(S state, E event);
}