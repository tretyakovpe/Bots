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
        
        public void truck(){
                this.name = "Гусеницы";
                this.durability = 120;
                this.speed = 32f;
            try {        
                image = new Image("/assets/images/64x64/wheels.png");
            } catch (SlickException ex) {
                System.out.println("При загрузке картинки гусениц косяк");
            }
        }

        public void wheel(){
                this.name = "Колеса";
                this.durability = 70;
                this.speed = 32f;
            try {
                image = new Image("/assets/images/64x64/wheels.png");
            } catch (SlickException ex) {
                System.out.println("При загрузке картинки колес косяк");
            }
 }

        public void antigrav(){
                this.name = "Антигравы";
                this.durability = 50;
                this.speed = 32f;
            try {        
                image = new Image("/assets/images/64x64/wheels.png");
            } catch (SlickException ex) {
                System.out.println("При загрузке картинки антигравов косяк");
            }
        }
    }
