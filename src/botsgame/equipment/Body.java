/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author pavel.tretyakov
 */
    public class Body extends Equipment{

        public int speed;
        private SpriteSheet sheet;
        
        public void truck() throws SlickException{
                this.name = "Гусеницы";
                this.durability = 20;
                this.speed = 3;
                sheet = new SpriteSheet("/assets/images/trucks.png",32,32); //спрайт игрока
                this.image = new Animation();        //создаем анимацию игрока
                this.image.setAutoUpdate(true); 
                for (int frame=0;frame<1;frame++) 
                { // покадровая анимация игрока
                    image.addFrame(sheet.getSprite(frame,0), 150);
                }                
                
        }
        public void wheel() throws SlickException{
                this.name = "Колеса";
                this.durability = 18;
                this.speed = 6;
                sheet = new SpriteSheet("/assets/images/wheels.png",32,32); //спрайт игрока
                this.image = new Animation();        //создаем анимацию игрока
                this.image.setAutoUpdate(true); 
                for (int frame=0;frame<1;frame++) 
                { // покадровая анимация игрока
                    image.addFrame(sheet.getSprite(frame,0), 150);
                }                
        }
        public void antigrav() throws SlickException{
                this.name = "Антигравы";
                this.durability = 15;
                this.speed = 9;
                sheet = new SpriteSheet("/assets/images/antigrav.png",32,32); //спрайт игрока
                this.image = new Animation();        //создаем анимацию игрока
                this.image.setAutoUpdate(true); 
                for (int frame=0;frame<1;frame++) 
                { // покадровая анимация игрока
                    image.addFrame(sheet.getSprite(frame,0), 150);
                }                
        }
    }
