/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author faust
 *
 * in GameWindow: keyManager = new KeyManager();
 * this.addKeyListener(keyManager);
 */
public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, enter, escape, space;

    public KeyManager() {
        keys = new boolean[256];
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        this.tick();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        this.tick();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void tick() {
        
        up = keys[KeyEvent.VK_UP]       | keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN]   | keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT]   | keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] | keys[KeyEvent.VK_D];
        enter = keys[KeyEvent.VK_ENTER];
        escape = keys[KeyEvent.VK_ESCAPE];
        space = keys[KeyEvent.VK_SPACE];

    }
}
