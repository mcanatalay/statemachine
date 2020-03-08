package com.mcana.statemachine.impl;

import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

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

    private Timer periodTimer;
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
    public synchronized void sendEvent(E event, Payload payload) {
        if(context == null || !isWorking || !isVaild(event))
            return;

        TransitionType type = transitor.getType(context.getState(), event);
        if(type == TransitionType.UNKNOWN)
            return;

        endPeriodicTransition();
        if(type.isPeriodic()){
            startPeriodicTransition(event, payload);
        } else{
            handleEventTransition(event, payload);
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

    private synchronized void handleEventTransition(E event, Payload payload){
        TransitionType type = transitor.getType(context.getState(), event);
        S nextState = transitor.getTargetState(context.getState(), event);

        boolean guardEvaluation = !type.isGuarded() || transitor.getGuard(context.getState(), event).test(context);
        
        TransitionContext<S,E> transitionContext = new TransitionContextImpl<>(context.getState(), nextState, event, guardEvaluation, payload);
        context.startTransition(transitionContext);
        
        if(type.hasAction() && guardEvaluation)
            transitor.getAction(context.getState(), event).apply(context);

        context.endTransition();
    }

    private void startPeriodicTransition(E event, Payload payload){
        periodTimer = new Timer();
        periodTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                if(periodTimer != null && isWorking){
                    handleEventTransition(event, payload);
                }
            }
        }, 0L, transitor.getPeriod(context.getState(), event));
    }

    private void endPeriodicTransition(){
        if(periodTimer != null){
            periodTimer.cancel();
            periodTimer = null;
        }
    }

    private boolean isVaild(E event){
        return states != null && events != null && initialState != null && events.contains(event);
    }
}