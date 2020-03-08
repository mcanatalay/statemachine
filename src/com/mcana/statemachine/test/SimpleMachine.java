package com.mcana.statemachine.test;

import java.util.EnumSet;

import com.mcana.statemachine.Action;
import com.mcana.statemachine.Guard;
import com.mcana.statemachine.StateContextListener;
import com.mcana.statemachine.StateMachine;
import com.mcana.statemachine.StateMachineBuilder;
import com.mcana.statemachine.TransitionContext;
import com.mcana.statemachine.transitor.Transitor;

public final class SimpleMachine implements StateContextListener<SimpleStates, SimpleEvents> {
    private StateMachine<SimpleStates, SimpleEvents> stateMachine;

    public SimpleMachine() {
        StateMachineBuilder<SimpleStates, SimpleEvents> builder = new StateMachineBuilder<>();
        builder.getConfiguration().configure(EnumSet.allOf(SimpleStates.class), EnumSet.allOf(SimpleEvents.class),
                SimpleStates.START, SimpleStates.END);
        Transitor<SimpleStates, SimpleEvents> transitor = builder.getTransitor();

        transitor.setExternalTransition(SimpleStates.START, SimpleStates.HELLO, SimpleEvents.NEXT, printHello());
        transitor.setInternalTransition(SimpleStates.HELLO, SimpleEvents.LOOP, printHello());
        transitor.setInternalPeriodicTransition(SimpleStates.HELLO, SimpleEvents.LOOP_PERIODIC, 1000L, printHello());
        transitor.setExternalTransition(SimpleStates.HELLO, SimpleStates.WORLD, SimpleEvents.NEXT, printWorld());
        transitor.setInternalTransition(SimpleStates.WORLD, SimpleEvents.LOOP, guardWorld(), printWorld());
        transitor.setExternalTransition(SimpleStates.WORLD, SimpleStates.END, SimpleEvents.NEXT);

        this.stateMachine = builder.getStateMachine();
        stateMachine.getContext().addListener(this);
        stateMachine.start();
    }

    public void next() {
        stateMachine.sendEvent(SimpleEvents.NEXT);
    }

    public void loop() {
        stateMachine.sendEvent(SimpleEvents.LOOP);
    }

    public void loopPeriodic() {
        stateMachine.sendEvent(SimpleEvents.LOOP_PERIODIC);
    }

    public Action<SimpleStates, SimpleEvents> printHello() {
        return context -> System.out.println("HELLO");
    }

    public Guard<SimpleStates, SimpleEvents> guardWorld() {
        return context -> {
            return !context.isVariableExist("WORLD") || (Integer) context.getVariable("WORLD") < 3;
        };
    }

    public Action<SimpleStates, SimpleEvents> printWorld() {
        return context -> {
            if (!context.isVariableExist("WORLD"))
                context.setVariable("WORLD", 1);
            else
                context.setVariable("WORLD", ((Integer) context.getVariable("WORLD")) + 1);
            System.out.println("WORLD");
        };
    }

    @Override
    public void onVariableChange(Object key, Object prevValue, Object currentValue) {
        System.out.println("Variable Key: " + key + " Prev. Value: " + prevValue + " Cur. Value: " + currentValue);
    }

    @Override
    public void onTransition(TransitionContext<SimpleStates, SimpleEvents> context) {
        System.out.println("Current: " + context.getCurrentState() + " Next: " + context.getNextState() + " Event: " + context.getEvent() + " Guard Ev: " + context.getGuardEvaluation());
    }

    @Override
    public void onStart() {
        System.out.println("Context Start");
    }

    @Override
    public void onEnd() {
        System.out.println("Context End");
    }

    public static void main(String[] args) {
        SimpleMachine machine = new SimpleMachine();

        machine.next();
        machine.loop();
        machine.loopPeriodic();
        try{
            Thread.sleep(5000L);
        } catch(Exception e){
            e.printStackTrace();
        }
        machine.loop();
        machine.next();
        machine.loop();
        machine.loop();
        machine.loop();
        machine.loop();
        machine.loop();
        machine.next();
        machine.next();
    }
}