package com.beastpotato.reactivetests.events;

/**
 * Created by Oleksiy on 3/18/2017.
 */

public class RXEvent<T> {
    private T data;

    public RXEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
