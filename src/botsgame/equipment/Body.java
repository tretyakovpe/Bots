/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author pavel.tretyakov
 */
    public class Body extends Equipment{

        public float speed;
        private SpriteSheet sheet;
        public Image[] imageBody = new Image[4];
        
        public void truck() throws SlickException{
                this.name = "Гусеницы";
                this.durability = 120;
                this.speed = 16f;
                
                sheet = new SpriteSheet("/assets/images/trucks.png",32,32); //спрайт игрока
                for(int i=0; i<4; i++)
                {
                    imageBody[i]=sheet.getSprite(i,0);
                }
                this.image = this.imageBody[1];
                
        }
        public void wheel() throws SlickException{
                this.name = "Колеса";
                this.durability = 70;
                this.speed = 12f;
                sheet = new SpriteSheet("/assets/images/wheels.png",32,32); //спрайт игрока
                for(int i=0; i<4; i++)
                {
                    imageBody[i]=sheet.getSprite(i,0);
                }
                this.image = this.imageBody[1];
        }
        public void antigrav() throws SlickException{
                this.name = "Антигравы";
                this.durability = 50;
                this.speed = 6f;
                sheet = new SpriteSheet("/assets/images/antigrav.png",32,32); //спрайт игрока
                for(int i=0; i<4; i++)
                {
                    imageBody[i]=sheet.getSprite(i,0);
                }
                this.image = this.imageBody[1];
        }
    }
