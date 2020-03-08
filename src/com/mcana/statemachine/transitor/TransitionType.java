package com.mcana.statemachine.transitor;

public enum TransitionType{
    UNKNOWN(false,false, false),
    SIMPLE(false, false, false),
    GUARDED_SIMPLE(false, true, false),
    ACTION(true, false, false),
    GUARDED_ACTION(true, true, false),
    PERIODIC_ACTION(true, false, true),
    PERIODIC_GUARDED_ACTION(true, true, true);

    private boolean isGuarded;
    private boolean hasAction;
    private boolean isPeriodic;

    TransitionType(boolean hasAction, boolean isGuarded, boolean isPeriodic){
        this.hasAction = hasAction;
        this.isGuarded = isGuarded;
        this.isPeriodic = isPeriodic;
    }

    public boolean isGuarded(){
        return isGuarded;
    }

    public boolean hasAction(){
        return hasAction;
    }

    public boolean isPeriodic(){
        return isPeriodic;
    }
}