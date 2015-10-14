/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import static botsgame.Constants.CELL_SIZE;
import java.util.ListIterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author pavel.tretyakov
 */
public class Repair extends Obstacles {
    private final int repairRange = 3;
    private final int repairPower = 1;
    
    public Repair(float x, float y) {
        this.posX=x;
        this.posY=y;
    }

    public void doRepair(){
        ListIterator it = Bot.allBots.listIterator();
        while (it.hasNext())
        {
            Bot bot = (Bot)it.next();
            if(bot.isDead() == false)
            {
                float X1 = bot.posX+CELL_SIZE/2;
                float Y1 = bot.posY+CELL_SIZE/2;
                float X = this.posX+CELL_SIZE/2;
                float Y = this.posY+CELL_SIZE/2;
                float distance = (float)Math.sqrt((X1-X)*(X1-X)+(Y1-Y)*(Y1-Y));
                boolean damaged=false;
                if (bot.currentHealth < bot.maxHealth)
                {
                    damaged=true;
                }
                if(damaged==true && distance<=(repairRange*CELL_SIZE))
                {
                    bot.currentHealth+=repairPower;
                }
            }
        }
    }

    public void draw(Graphics g) {
        float range = repairRange*CELL_SIZE;
        float x = posX-(range/2)+CELL_SIZE/2;
        float y = posY-(range/2)+CELL_SIZE/2;
        
        Color c = new Color(0.5f,0,0,0.5f);
        g.setColor(c);
        g.fillOval(x, y, range, range);
//        g.setColor(Color.green);
//        g.fillOval(posX-CELL_SIZE/2, posY-CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
    }
}
