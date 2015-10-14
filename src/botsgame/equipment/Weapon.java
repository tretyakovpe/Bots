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

        public void cannon(){
            this.name = "Пушка";
            this.speed = 3;
            this.range = 200f;
            this.damage = 15;
            try {
                image = new Image("/assets/images/64x64/cannon.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void laser(){
            this.name = "Вулкан";
            this.speed = 5;
            this.range = 300f;
            this.damage = 5;
            try {
                image = new Image("/assets/images/64x64/volcano.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void plasma(){
            this.name = "Ракеты";
            this.speed = 1;
            this.range = 100f;
            this.damage = 30;
            try {
                image = new Image("/assets/images/64x64/missiles.png");
            } catch (SlickException ex) {
                Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
