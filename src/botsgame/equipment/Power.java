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
    public class Power extends Equipment{
        public String name;
        public int power;
        private SpriteSheet sheet;

        public void nuclearReactor() throws SlickException {
            this.name = "Ядерный реактор";
            this.power = 100;
            this.durability = 10;
            sheet = new SpriteSheet("/assets/images/nuclear.png",32,32); //спрайт игрока
            this.image = new Animation();        //создаем анимацию игрока
            this.image.setAutoUpdate(true); 
            for (int frame=0;frame<1;frame++) 
            { // покадровая анимация игрока
                image.addFrame(sheet.getSprite(frame,0), 150);
            }                
            
        }
        public void dieselEngine() throws SlickException{
            this.name = "Дизель-генератор";
            this.power = 10;
            this.durability = 80;
            sheet = new SpriteSheet("/assets/images/diesel.png",32,32); //спрайт игрока
            this.image = new Animation();        //создаем анимацию игрока
            this.image.setAutoUpdate(true); 
            for (int frame=0;frame<1;frame++) 
            { // покадровая анимация игрока
                image.addFrame(sheet.getSprite(frame,0), 150);
            }                
        }
    }
