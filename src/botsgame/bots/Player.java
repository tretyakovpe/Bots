/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
public class Player extends Bot {

    public Player(String name, Color color, String flagColor) throws SlickException {
        super(name, color, flagColor, true);
    }
    
}
