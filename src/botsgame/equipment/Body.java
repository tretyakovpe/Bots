/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

import java.awt.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author pavel.tretyakov
 */
    public class Body extends Equipment{

        public int speed;
        public Color color;

        public void truck() throws SlickException{
                this.name = "Гусеницы";
                this.durability = 20;
                this.speed = 3;
                this.color=Color.PINK;
                SpriteSheet sheet = new SpriteSheet("/assets/images/karbonator.png",32,32); //спрайт игрока
                this.image = new Animation();        //создаем анимацию игрока
                this.image.setAutoUpdate(false); 
                for (int frame=0;frame<3;frame++) 
                { // покадровая анимация игрока
                    image.addFrame(sheet.getSprite(frame,0), 150);
                }                
                
        }
        public void wheel(){
                this.name = "Колеса";
                this.durability = 18;
                this.speed = 6;
                this.color=Color.black;
/*                try {                
                   this.image = ImageIO.read(new File("assets/wheels.gif"));
                } catch (IOException ex) {
                     // handle exception...
                }*/
        }
        public void antigrav(){
                this.name = "Антигравы";
                this.durability = 15;
                this.speed = 9;
                this.color=Color.cyan;
/*                try {                
                   this.image = ImageIO.read(new File("assets/antigrav.gif"));
                } catch (IOException ex) {
                     // handle exception...
                }*/
        }
    }
