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
        public float range;
        public int damage;

        public void cannon() throws SlickException{
            this.name = "Пушка";
            this.speed = 3;
            this.range = 200f;
            this.damage = 15;
            image = new Image("/assets/images/cannon.png");
        }
        public void laser() throws SlickException{
            this.name = "Вулкан";
            this.speed = 5;
            this.range = 300f;
            this.damage = 5;
            image = new Image("/assets/images/volcano.png");
        }
        public void plasma() throws SlickException{
            this.name = "Ракеты";
            this.speed = 1;
            this.range = 100f;
            this.damage = 30;
            image = new Image("/assets/images/missiles.png");
        }
    }
