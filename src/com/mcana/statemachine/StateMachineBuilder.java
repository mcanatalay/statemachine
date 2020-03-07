package com.mcana.statemachine;

import com.mcana.statemachine.impl.StateMachineImpl;
import com.mcana.statemachine.impl.TransitorImpl;
import com.mcana.statemachine.transitor.Transitor;

public class StateMachineBuilder<S extends Enum<S>, E extends Enum<E>>{
    private StateMachineImpl<S,E> stateMachine;
    private TransitorImpl<S,E> transitor;

    public StateMachineBuilder(){
        this.transitor = new TransitorImpl<>();
        this.stateMachine = new StateMachineImpl<>(transitor);
    }

    public Transitor<S,E> getTransitor(){
        return transitor;
    }

    public StateMachineConfiguration<S,E> getConfiguration(){
        return stateMachine;
    }

    public StateMachine<S,E> getStateMachine(){
        return stateMachine;
    }
}