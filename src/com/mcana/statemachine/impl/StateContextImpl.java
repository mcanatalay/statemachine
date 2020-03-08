package com.mcana.statemachine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mcana.statemachine.StateContext;
import com.mcana.statemachine.StateContextListener;
import com.mcana.statemachine.TransitionContext;
import com.mcana.statemachine.transitor.TransitionWorker;

public class StateContextImpl<S extends Enum<S>, E extends Enum<E>> implements StateContext<S, E>, TransitionWorker<S, E> {
    private S initialState;

    private S currentState;
    private TransitionContext<S,E> activeTransitionContext;

    private Map<Object, Object> variables;
    private List<StateContextListener<S,E>> listeners;

    public StateContextImpl(S initialState){
        this.initialState = initialState;

        this.currentState = initialState;
        this.variables = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void start(){
        this.currentState = initialState;
        this.variables = new HashMap<>();
        listeners.forEach(listener -> listener.onStart());
    }

    public void stop(){
        listeners.forEach(listener -> listener.onEnd());
    }

    @Override
    public void startTransition(TransitionContext<S,E> context){
        if(activeTransitionContext == null){
            this.activeTransitionContext = context;
            listeners.forEach(listener -> listener.onTransition(context));
        }
    }

    @Override
    public void endTransition(){
        if(activeTransitionContext != null){
            this.currentState = activeTransitionContext.getNextState();
            this.activeTransitionContext = null;
        }
    }

    @Override
    public S getState() {
        return currentState;
    }

    @Override
    public boolean isTransiting() {
        return activeTransitionContext != null;
    }

    @Override
    public TransitionContext<S, E> getActiveTransition() {
        return activeTransitionContext;
    }

    @Override
    public List<Object> getVariableKeys() {
        return variables.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public boolean isVariableExist(Object key) {
        return variables.containsKey(key);
    }

    @Override
    public Object getVariable(Object key) {
        return variables.get(key);
    }

    @Override
    public void setVariable(Object key, Object value) {
        Object prevValue = variables.get(key);
        boolean isChanged = prevValue != value;

        variables.put(key, value);

        if(isChanged)
            listeners.forEach(listener -> listener.onVariableChange(key, prevValue, value));
    }

    @Override
    public Object removeVariable(Object key) {
        Object prevValue = variables.get(key);

        variables.remove(key);

        if(prevValue != null)
        listeners.forEach(listener -> listener.onVariableChange(key, prevValue, null));

        return prevValue;
    }

    @Override
    public void addListener(StateContextListener<S, E> listener) {
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public StateContextListener<S, E> removeListener(StateContextListener<S,E> listener) {
        if(listeners.remove(listener))
            return listener;
        else
            return null;
    }

    @Override
    public List<StateContextListener<S, E>> getListeners() {
        return listeners;
    }

}