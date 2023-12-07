package org.terasology.nui.core;

import com.google.common.collect.Maps;
import org.terasology.nui.core.bind.BindingPair;
import org.terasology.nui.core.bind.BindingType;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public class EventDispatcher {
    Map<Long, Queue<Runnable>> threadQueue = Maps.newConcurrentMap();

    private ThreadLocal<Queue<Runnable>> localQueue = new ThreadLocal<>();

    public void dispatch(BindingPair pair, Runnable runnable) {
        BindingType type = pair.getBindingType();
        Optional<UIObject> slotWidgetOptional = pair.getSignalWidget();
        Optional<UIObject> signalWidgetOptional = pair.getSignalWidget();
        if (!slotWidgetOptional.isPresent() || !signalWidgetOptional.isPresent()) {
            return;
        }

        UIObject signalWidget = signalWidgetOptional.get();


        runnable.run();
//
//        switch (type) {
//            case Auto:
//                break;
//            case Queue:
//                break;
//            case Direct:
//                runnable.run();
//                break;
//            case BlockedQueue:
//                break;
//        }
    }

    public void dispatchQueue(Runnable runnable) {

    }

    public void dispatch() {

    }
}
