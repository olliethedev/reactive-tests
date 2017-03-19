package com.beastpotato.reactivetests.events;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Oleksiy on 3/18/2017.
 */

public class RXBus {
    private final PublishSubject<Object> rxBusObservable = PublishSubject.create();
    private static RXBus instance;

    private RXBus() {
    }

    public static RXBus getInstance() {
        if (instance == null)
            instance = new RXBus();
        return instance;
    }

    public PublishSubject<Object> subscribe() {
        return rxBusObservable;
    }

    public void post(Object event) {
        rxBusObservable.onNext(event);
    }
}
