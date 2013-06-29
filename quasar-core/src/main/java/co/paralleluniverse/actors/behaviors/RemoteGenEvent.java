/*
 * Quasar: lightweight threads and actors for the JVM.
 * Copyright (C) 2013, Parallel Universe Software Co. All rights reserved.
 * 
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU Lesser General Public License version 3.0
 * as published by the Free Software Foundation.
 */
package co.paralleluniverse.actors.behaviors;

import co.paralleluniverse.actors.RemoteActor;
import static co.paralleluniverse.actors.behaviors.RequestReplyHelper.call;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 *
 * @author pron
 */
public class RemoteGenEvent<Event> extends RemoteBasicGenBehavior implements GenEvent<Event> {
    public RemoteGenEvent(RemoteActor<Object> actor) {
        super(actor);
    }

    @Override
    public boolean addHandler(EventHandler<Event> handler) throws SuspendExecution, InterruptedException {
        final GenResponseMessage res = call(this, new LocalGenEvent.HandlerMessage(RequestReplyHelper.from(), null, handler, true));
        return ((GenValueResponseMessage<Boolean>) res).getValue();
    }

    @Override
    public boolean removeHandler(EventHandler<Event> handler) throws SuspendExecution, InterruptedException {
        final GenResponseMessage res = call(this, new LocalGenEvent.HandlerMessage(RequestReplyHelper.from(), null, handler, false));
        return ((GenValueResponseMessage<Boolean>) res).getValue();
    }

    @Override
    public void notify(Event event) throws SuspendExecution {
        send(event);
    }
}