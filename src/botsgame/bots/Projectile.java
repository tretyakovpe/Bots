/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import static botsgame.Constants.CELL_SIZE;
import static botsgame.Constants.WINDOW_HEIGHT;
import static botsgame.Constants.WINDOW_WIDTH;
import botsgame.Landscape.Wall;
import java.util.ListIterator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;



/**
 *
 * @author pavel.tretyakov
 */
public class Projectile {
    private final static Animation hitAnimation;
    private final static SpriteSheet hitSprite; 

    public Bot bot;
    public float posX;
    public float posY;
    public float destX;
    public float destY;
    public float angle;
    public float speed;
    public boolean hit;
    
    private final float diffX;
    private final float diffY;
    
    public int damage;
    
    static {
        try {
            hitSprite = new SpriteSheet("/assets/images/hit.png",32,32);
            hitAnimation = new Animation(hitSprite,0,0,23,0,true,20,true);
        } catch (SlickException ex) {
            System.out.println("Ошибка при загрузке изображения попадания");
        }
    
    }
    Projectile(Bot b, float posX, float posY, float posX0, float posY0, int i, int i0) 
    {
        this.bot = b;
        this.posX = posX;
        this.posY = posY;
        this.destX = posX0;
        this.destY = posY0;
        this.angle=bot.targetDirection;
        this.speed = i;
        this.damage = i0;
        this.hit = false;
        diffX = (destX-posX)/(speed);
        diffY = (destY-posY)/(speed);
    }
    
    public void update(ListIterator iter)
    {
        posX+=diffX;
        posY+=diffY;
        
        //попадание в танк
        ListIterator it = Bot.allBots.listIterator();
        while (it.hasNext())
        {
            Bot target = (Bot)it.next();
            if(target!=bot && target.isDead() == false)
            {
                if (posX+CELL_SIZE/2 > target.posX && posX+CELL_SIZE/2 < target.posX+CELL_SIZE &&
                    posY+CELL_SIZE/2 > target.posY && posY+CELL_SIZE/2 < target.posY+CELL_SIZE)
                {
                    target.doDamage(damage);
                    if(target.currentHealth<=0)
                    {
                        target.botMode=0;
                        bot.score++;
                        iter.remove();
                        return;
                    }
                    hit = true;
                    iter.remove();
                    return;
                }
            }
        }
        
        //попадание в стену
        ListIterator wallIter = Bot.terrain.walls.listIterator();
        while (wallIter.hasNext())
        {
            Wall w = (Wall) wallIter.next();
            float x = posX+CELL_SIZE/2;
            float y = posY+CELL_SIZE/2;
            if(x>w.x && x<w.x+w.w && y>w.y && y<w.y+w.h)
            {
                hit = true;
                iter.remove();
                return;
            }
            
        }
        
        //выход за пределы экрана
        if (posX<0 || posX > WINDOW_WIDTH ||
                posY>WINDOW_HEIGHT || posY < 0)
        {
            iter.remove();
        }
    }
    
    public void draw(Graphics g){
        if(hit)
        {
            g.drawAnimation(hitAnimation, posX, posY);
        }
        else
        {
            bot.weapon.bulletImage.setRotation(angle);
            g.drawImage(bot.weapon.bulletImage, posX, posY);
        }
    }
            
}
