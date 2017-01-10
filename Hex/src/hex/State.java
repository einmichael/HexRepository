/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.util.ArrayList;

/**
 *
 * @author faust
 */
public class State {

    public boolean PlInit;

    private String name;

    public State(String name) {
        length++;
        this.name = name;
    }

    private static int length;
    private final static ArrayList<State> LIST = new ArrayList<State>();

    public static final State PREP = new State(
            "Preparation");
}
