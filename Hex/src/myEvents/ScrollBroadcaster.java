/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEvents;

import javax.swing.event.EventListenerList;

/**
 *
 * @author faust
 */
public class ScrollBroadcaster {

    public static ScrollBroadcaster broadcaster = new ScrollBroadcaster();

    private ScrollBroadcaster() {
    }

    ;
    
    public static ScrollBroadcaster getInstance() {
        return broadcaster;
    }

    public synchronized void fireScrollEvent(ScrollEvent e) {
        System.out.println("Fire Event. " + this.toString());
        notifyAll(e);
    }
   

    private final EventListenerList listeners = new EventListenerList();

    public void addScrollListener(ScrollListener listener) {
        listeners.add(ScrollListener.class, listener);
    }

    public void removeScrollListener(ScrollListener listener) {
        listeners.remove(ScrollListener.class, listener);
    }

    protected synchronized void notifyAll(ScrollEvent event) {
        for (ScrollListener l : listeners.getListeners(ScrollListener.class)) {
            l.scrollUpdate(event);
            System.out.println(""+l);
        }
    }
}
