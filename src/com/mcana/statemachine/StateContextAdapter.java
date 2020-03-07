package com.mcana.statemachine;

public abstract class StateContextAdapter<S extends Enum<S>, E extends Enum<E>> implements StateContextListener<S, E>{
    @Override
    public void onVariableChange(Object key, Object prevValue, Object currentValue){
        return;
    }

    @Override
    public void onTransition(TransitionContext<S,E> context){
        return;
    }

    @Override
    public void onStart() {
        return;
    }

    @Override
    public void onEnd() {
        return;
    }
}