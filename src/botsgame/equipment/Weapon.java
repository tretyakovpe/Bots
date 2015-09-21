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
    public class Weapon extends Equipment{
        public int speed;
        public int range;
        public int damage;

        public void cannon() throws SlickException{
            this.name = "Пушка";
            this.speed = 3;
            this.range = 150;
            this.damage = 20;
            image = new Image("/assets/images/cannon.png");
        }
        public void laser() throws SlickException{
            this.name = "Лазер";
            this.speed = 5;
            this.range = 300;
            this.damage = 10;
            image = new Image("/assets/images/laser.png");
        }
        public void plasma() throws SlickException{
            this.name = "Плазма";
            this.speed = 1;
            this.range = 75;
            this.damage = 30;
            image = new Image("/assets/images/plasma.png");
        }
    }
