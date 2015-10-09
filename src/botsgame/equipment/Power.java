/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
    public class Power extends Equipment{
        public String name;
        public int power;

        public void nuclearReactor() throws SlickException {
            this.name = "РИТЭГ";
            this.power = 5;
            this.durability = 20;
        }
        public void dieselEngine() throws SlickException{
            this.name = "Дизель-генератор";
            this.power = 3;
            this.durability = 30;
        }
        public void solarPlate() throws SlickException{
            this.name = "Солнечная батарея";
            this.power = 1;
            this.durability = 10;
        }
    }
