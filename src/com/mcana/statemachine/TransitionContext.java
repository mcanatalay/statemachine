package com.mcana.statemachine;

public interface TransitionContext<S extends Enum<S>, E extends Enum<E>>{
    S getCurrentState();

    S getNextState();

    E getEvent();

    boolean getGuardEvaluation();
    
    boolean isPayloadExist();

    Payload getPayload();
}