package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;

public interface TimeTransitor<S extends Enum<S>, E extends Enum<E>>{
    void setExternalTimeTransition(S current, S next, long delay, Guard<S,E> guard, Action<S, E> action);

    void setExternalTimeTransition(S current, S next, long delay, Guard<S,E> guard);

    void setExternalTimeTransition(S current, S next, long delay, Action<S, E> action);

    void setExternalTimeTransition(S current, S next, long delay);

    void setInternalTimeTransition(S current, long delay, Guard<S,E> guard, Action<S, E> action);

    void setInternalTimeTransition(S current, long delay, Action<S, E> action);

}