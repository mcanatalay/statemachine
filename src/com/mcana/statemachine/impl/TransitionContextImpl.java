package com.mcana.statemachine.impl;

import com.mcana.statemachine.Payload;
import com.mcana.statemachine.TransitionContext;

public class TransitionContextImpl<S extends Enum<S>, E extends Enum<E>> implements TransitionContext<S, E> {
    private S currentState;
    private S nextState;
    private E event;
    private boolean guardEvaluation;
    private Payload payload;

    public TransitionContextImpl(S currentState, S nextState, E event, boolean guardEvaluation, Payload payload){
        this.currentState = currentState;
        this.nextState = nextState;
        this.event = event;

        this.guardEvaluation = guardEvaluation;
        this.payload = payload;
    }

    public TransitionContextImpl(S currentState, S nextState, E event, boolean guardEvaluation){
        this(currentState, nextState, event, guardEvaluation, null);
    }

    public TransitionContextImpl(S currentState, S nextState, E event, Payload payload){
        this(currentState, nextState, event, true, payload);
    }

    public TransitionContextImpl(S currentState, S nextState, E event){
        this(currentState, nextState, event, true, null);
    }

    @Override
    public S getCurrentState() {
        return currentState;
    }

    @Override
    public S getNextState() {
        return nextState;
    }

    @Override
    public E getEvent() {
        return event;
    }

    @Override
    public boolean getGuardEvaluation() {
        return guardEvaluation;
    }

    @Override
    public boolean isPayloadExist() {
        return payload != null;
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

}