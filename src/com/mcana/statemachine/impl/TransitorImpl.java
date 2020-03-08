package com.mcana.statemachine.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;
import com.mcana.statemachine.transitor.StateTransitor;
import com.mcana.statemachine.transitor.TransitionType;
import com.mcana.statemachine.transitor.Transitor;

public class TransitorImpl<S extends Enum<S>, E extends Enum<E>> extends Transitor<S,E> implements StateTransitor<S, E> {
    private Map<Integer, S> stateMap;
    private Map<Integer, Guard<S, E>> guardMap;
    private Map<Integer, Action<S, E>> actionMap;
    private Map<Integer, Long> periodMap;

    public TransitorImpl(){
        this.stateMap = new HashMap<>();
        this.guardMap = new HashMap<>();
        this.actionMap = new HashMap<>();
        this.periodMap = new HashMap<>();
    }

    @Override
    public TransitionType getType(S state, E event){
        Integer key = getKey(state, event);

        if(!stateMap.containsKey(key))
            return TransitionType.UNKNOWN;
        else{
            boolean isGuarded = guardMap.get(key) != null;
            boolean isAction = actionMap.get(key) != null;
            boolean isPeriodic = periodMap.get(key) != null;

            if(isGuarded){
                if(isAction)
                    return isPeriodic ? TransitionType.PERIODIC_GUARDED_ACTION : TransitionType.GUARDED_ACTION;
                else
                    return TransitionType.GUARDED_SIMPLE;
            } else{
                if(isAction)
                    return isPeriodic ? TransitionType.PERIODIC_ACTION : TransitionType.ACTION;
                else
                    return TransitionType.SIMPLE;
            }
        }
    }

    @Override
    public S getTargetState(S state, E event){
        return stateMap.get(getKey(state, event));
    }

    @Override
    public Guard<S, E> getGuard(S state, E event){
        return guardMap.get(getKey(state, event));
    }

    @Override
    public Action<S, E> getAction(S state, E event){
        return actionMap.get(getKey(state, event));
    }

    @Override
    public Long getPeriod(S state, E event) {
        return periodMap.get(getKey(state, event));
    }

    @Override
    public void setInternalTransition(S current, E event, Guard<S, E> guard, Action<S, E> action) {
        setTransition(current, current, event, null, guard, action);
    }

    @Override
    public void setInternalTransition(S current, E event, Action<S, E> action) {
        setTransition(current, current, event, null, null, action);
    }

    @Override
    public void setExternalTransition(S current, S next, E event, Guard<S, E> guard, Action<S, E> action) {
        setTransition(current, next, event, null, guard, action);
    }

    @Override
    public void setExternalTransition(S current, S next, E event, Guard<S, E> guard) {
        setTransition(current, next, event, null, guard, null);
    }

    @Override
    public void setExternalTransition(S current, S next, E event, Action<S, E> action) {
        setTransition(current, next, event, null, null, action);
    }

    @Override
    public void setExternalTransition(S current, S next, E event) {
        setTransition(current, next, event, null, null, null);
    }

    @Override
    public void setInternalPeriodicTransition(S current, E event, Long period, Action<S, E> action) {
        setTransition(current, current, event, period, null, action);
    }

    @Override
    public void setInternalPeriodicTransition(S current, E event, Long period, Guard<S, E> guard, Action<S, E> action) {
        setTransition(current, current, event, period, guard, action);
    }

    private void setTransition(S current, S next, E event, Long period, Guard<S, E> guard, Action<S, E> action){
        Integer key = getKey(current, event);

        stateMap.put(key, next);
        guardMap.put(key, guard);
        actionMap.put(key, action);
        periodMap.put(key, period);
    }

    private Integer getKey(S state, E event){
        return Objects.hash(state, event);
    }
}