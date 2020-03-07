package com.mcana.statemachine;

import java.util.Map;

public interface Payload{
    void push(Object key, Object value);

    Object get(Object key);

    Map<Object, Object> getAll();
}