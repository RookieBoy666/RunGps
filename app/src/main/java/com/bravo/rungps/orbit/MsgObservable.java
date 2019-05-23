package com.bravo.rungps.orbit;

import java.util.Observable;

/**

 */
public class MsgObservable extends Observable {

    static class Inner {
        static MsgObservable instance = new MsgObservable();
    }

    public static MsgObservable getInstance() {
        return Inner.instance;
    }


    public synchronized void sendMsgs(Object o) {
        setChanged();
        notifyObservers(o);
    }
}
