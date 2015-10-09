/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
    public class Body extends Equipment{

        public float speed;
        
        public void truck() throws SlickException{
                this.name = "Гусеницы";
                this.durability = 120;
                this.speed = 16f;
                image = new Image("/assets/images/wheels.png");        
        }

        public void wheel() throws SlickException{
                this.name = "Колеса";
                this.durability = 70;
                this.speed = 12f;
                image = new Image("/assets/images/wheels.png");        }

        public void antigrav() throws SlickException{
                this.name = "Антигравы";
                this.durability = 50;
                this.speed = 6f;
                image = new Image("/assets/images/wheels.png");        
        }
    }
