/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        public int bulletSpeed;
        public Image bulletImage;
        

        public void cannon(){
            this.name = "Пушка";
            this.speed = 3;
            this.range = 192f;
            this.damage = 15;
            this.bulletSpeed = 5;
            try {
                image = new Image("/assets/images/64x64/cannon.png");
                bulletImage = new Image("/assets/images/cannonball.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void volcano(){
            this.name = "Вулкан";
            this.speed = 10;
            this.range = 128f;
            this.damage = 5;
            this.bulletSpeed = 5;
            try {
                image = new Image("/assets/images/64x64/volcano.png");
                bulletImage = new Image("/assets/images/bullet.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void missiles(){
            this.name = "Ракеты";
            this.speed = 1;
            this.range = 256f;
            this.damage = 30;
            this.bulletSpeed = 20;
            try {
                image = new Image("/assets/images/64x64/missiles.png");
                bulletImage = new Image("/assets/images/missile.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
