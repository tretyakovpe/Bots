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
    public class Weapon extends Equipment{
        public int speed;
        public int range;
        public int damage;
        private SpriteSheet sheet;

        public void cannon() throws SlickException{
            this.name = "Пушка";
            this.speed = 5;
            this.range = 5;
            this.damage = 3;
            sheet = new SpriteSheet("/assets/images/cannon.png",32,32); //спрайт игрока
            this.image = new Animation();        //создаем анимацию игрока
            this.image.setAutoUpdate(true); 
            for (int frame=0;frame<1;frame++) 
            { // покадровая анимация игрока
                image.addFrame(sheet.getSprite(frame,0), 150);
            }                
        }
        public void laser() throws SlickException{
            this.name = "Лазер";
            this.speed = 5;
            this.range = 10;
            this.damage = 1;
            sheet = new SpriteSheet("/assets/images/laser.png",32,32); //спрайт игрока
            this.image = new Animation();        //создаем анимацию игрока
            this.image.setAutoUpdate(true); 
            for (int frame=0;frame<1;frame++) 
            { // покадровая анимация игрока
                image.addFrame(sheet.getSprite(frame,0), 150);
            }                
        }
        public void plasma() throws SlickException{
            this.name = "Плазма";
            this.speed = 1;
            this.range = 4;
            this.damage = 5;
            sheet = new SpriteSheet("/assets/images/plasma.png",32,32); //спрайт игрока
            this.image = new Animation();        //создаем анимацию игрока
            this.image.setAutoUpdate(true); 
            for (int frame=0;frame<1;frame++) 
            { // покадровая анимация игрока
                image.addFrame(sheet.getSprite(frame,0), 150);
            }                
        }
    }
