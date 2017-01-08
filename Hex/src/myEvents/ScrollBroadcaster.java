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
    public static ScrollBroadcaster broadcaster =  new ScrollBroadcaster();
    private ScrollBroadcaster(){
        
    };
    public static ScrollBroadcaster getInstance(){
        return broadcaster;
    }
    
    public synchronized void fire(ScrollEvent e){
        System.out.println("fire");
        notieAll(e);
    }
    private EventListenerList listeners = new EventListenerList();
    
    public void addScrollListener( ScrollListener listener )
  {
    listeners.add( ScrollListener.class, listener );
  }

  public void removeScrollListener( ScrollListener listener )
  {
    listeners.remove( ScrollListener.class, listener );
  }
  
  protected synchronized void notieAll( ScrollEvent event )
  {
    for ( ScrollListener l : listeners.getListeners( ScrollListener.class ) )
      l.scrollUpdate(event );
  }
}
/*
public class Radio
{
  private EventListenerList listeners = new EventListenerList();

  private List<String> ads = Arrays.asList( "Jetzt explodiert auch der Haarknoten",
                                            "Red Fish verleiht Flossen",
                                            "Bom Chia Wowo",
                                            "Wunder Whip. Iss milder." );

  public Radio()
  {
    new Timer().schedule( new TimerTask()
    {
      @Override public void run()
      {
          Collections.shuffle( ads );
          notifyAdvertisement( new AdEvent( this, ads.get(0) ) );
      }
    }, 0, 500 );
  }

  

*/