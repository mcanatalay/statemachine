package com.mcana.statemachine.impl;

import java.util.EnumSet;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;
import com.mcana.statemachine.Payload;
import com.mcana.statemachine.StateContext;
import com.mcana.statemachine.StateMachine;
import com.mcana.statemachine.StateMachineConfiguration;
import com.mcana.statemachine.TransitionContext;
import com.mcana.statemachine.transitor.TransitionType;

public class StateMachineImpl<S extends Enum<S>, E extends Enum<E>> implements StateMachine<S, E>, StateMachineConfiguration<S,E> {
    private StateContextImpl<S,E> context;
    private TransitorImpl<S,E> transitor;

    private EnumSet<S> states;
    private EnumSet<E> events;
    private S initialState;
    private S finalState;
    private boolean isWorking;

    public StateMachineImpl(TransitorImpl<S,E> transitor){
        this.transitor = transitor;
    }

    @Override
    public void start() {
        if(context == null || isWorking)
            return;

        this.isWorking = true;
        context.start();
    }

    @Override
    public void sendEvent(E event) {
        sendEvent(event, null);
    }

    @Override
    public void sendEvent(E event, Payload payload) {
        if(context == null)
            return;

        if(finalState != null && context.getState() == finalState)
            return;

        if(!isVaild(event))
            return;

        TransitionType type = transitor.getType(context.getState(), event);

        if(type == TransitionType.SIMPLE){
            S nextState = transitor.getTargetState(context.getState(), event);
            TransitionContext<S,E> transitionContext = new TransitionContextImpl<>(context.getState(), nextState, event, payload);
            context.startTransition(transitionContext);
            context.endTransition();
        } else if(type == TransitionType.GUARDED_SIMPLE){
            S nextState = transitor.getTargetState(context.getState(), event);
            Guard<S,E> guard = transitor.getGuard(context.getState(), event);
            TransitionContext<S,E> transitionContext = new TransitionContextImpl<>(context.getState(), nextState, event, guard.test(context), payload);
            context.startTransition(transitionContext);
            context.endTransition();
        } else if(type == TransitionType.ACTION){
            S nextState = transitor.getTargetState(context.getState(), event);
            TransitionContext<S,E> transitionContext = new TransitionContextImpl<>(context.getState(), nextState, event, payload);
            context.startTransition(transitionContext);
            Action<S,E> action = transitor.getAction(context.getState(), event);
            action.apply(context);
            context.endTransition();
        } else if(type == TransitionType.GUARDED_ACTION){
            S nextState = transitor.getTargetState(context.getState(), event);
            Guard<S,E> guard = transitor.getGuard(context.getState(), event);
            TransitionContext<S,E> transitionContext = new TransitionContextImpl<>(context.getState(), nextState, event, guard.test(context), payload);
            context.startTransition(transitionContext);
            if(transitionContext.getGuardEvaluation()){
                Action<S,E> action = transitor.getAction(context.getState(), event);
                action.apply(context);
            }
            context.endTransition();
        } else{
            return;
        }

        if(context.getState() == finalState){
            isWorking = false;
            context.stop();
        }
    }

    @Override
    public void configure(EnumSet<S> states, EnumSet<E> events, S initial, S end) {
        this.states = states;
        this.events = events;
        this.initialState = initial;
        this.finalState = end;
        this.context = new StateContextImpl<>(initialState);
    }

    @Override
    public void configure(EnumSet<S> states, EnumSet<E> events, S initial) {
        configure(states, events, initial, null);
    }

    @Override
    public StateContext<S, E> getContext() {
        return context;
    }

    private boolean isVaild(E event){
        return states != null && events != null && initialState != null && events.contains(event);
    }

}