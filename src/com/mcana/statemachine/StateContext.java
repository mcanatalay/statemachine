package com.mcana.statemachine;

import java.util.List;

public interface StateContext<S extends Enum<S>, E extends Enum<E>>{
    S getState();

    boolean isTransiting();

    TransitionContext<S,E> getActiveTransition();

    List<Object> getVariableKeys();

    boolean isVariableExist(Object key);

    Object getVariable(Object key);

    void setVariable(Object key, Object value);

    Object removeVariable(Object key);

    void addListener(StateContextListener<S,E> listener);

    StateContextListener<S,E> removeListener(StateContextListener<S,E> listener);

    List<StateContextListener<S, E>> getListeners();
}