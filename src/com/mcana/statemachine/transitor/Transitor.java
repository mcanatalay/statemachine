package com.mcana.statemachine.transitor;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;
import com.mcana.statemachine.transitor.ExternalTransitor;
import com.mcana.statemachine.transitor.InternalTransitor;

public abstract class Transitor<S extends Enum<S>, E extends Enum<E>> implements InternalTransitor<S, E>, ExternalTransitor<S,E>, PeriodicTransitor<S,E> {  
    @Override
    public void setInternalTransition(S current, E event, Guard<S, E> guard, Action<S, E> action) {
        return;
    }

    @Override
    public void setInternalTransition(S current, E event, Action<S, E> action) {
        return;
    }
  
    @Override
    public void setExternalTransition(S current, S next, E event, Guard<S, E> guard, Action<S, E> action) {
        return;
    }

    @Override
    public void setExternalTransition(S current, S next, E event, Guard<S, E> guard) {
        return;
    }

    @Override
    public void setExternalTransition(S current, S next, E event, Action<S, E> action) {
        return;
    }

    @Override
    public void setExternalTransition(S current, S next, E event) {
        return;
    }

    @Override
    public void setInternalPeriodicTransition(S current, E event, Long period, Action<S, E> action) {
        return;
    }

    @Override
    public void setInternalPeriodicTransition(S current, E event, Long period, Guard<S, E> guard, Action<S, E> action) {
        return;
    }
}