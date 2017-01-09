/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEvents;

import java.util.EventObject;

/**
 *
 * @author faust
 */
public class ScrollEvent extends EventObject{
   private int a;

  public ScrollEvent( Object source, int newA )
  {
    super( source );
    this.a = newA;
  }

  public int getA()
  {
    return a;
  }
    
}
