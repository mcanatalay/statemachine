package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface ExternalTransitor<S extends Enum<S>, E extends Enum<E>>{
    void setExternalTransition(S current, S next, E event, Guard<S,E> guard, Action<S, E> action);

    void setExternalTransition(S current, S next, E event, Guard<S,E> guard);

    void setExternalTransition(S current, S next, E event, Action<S, E> action);

    void setExternalTransition(S current, S next, E event);
}