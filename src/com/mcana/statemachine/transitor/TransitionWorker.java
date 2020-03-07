package com.mcana.statemachine.transitor;

import com.mcana.statemachine.TransitionContext;

public interface TransitionWorker<S extends Enum<S>, E extends Enum<E>>{
    void startTransition(TransitionContext<S,E> context);
    
    void endTransition();
}