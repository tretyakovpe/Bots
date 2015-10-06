/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author pavel.tretyakov
 */
public class Repair extends Obstacles {
    private float repairRange = 128f;
    private int repairPower = 1;
    
    public Repair(float x, float y) {
        this.posX=x;
        this.posY=y;
    }

    public void doRepair(Army army){
        for(Bot bot: army.bots)
        {
            float X1 = bot.posX+16;
            float Y1 = bot.posY+16;
            float X = this.posX+16;
            float Y = this.posY+16;
            float distance = (float)Math.sqrt((X1-X)*(X1-X)+(Y1-Y)*(Y1-Y));
            boolean damaged=false;
            if (bot.currentHealth < bot.maxHealth)
            {
                damaged=true;
            }
            if(damaged==true && distance<=repairRange)
            {
                bot.currentHealth+=repairPower;
            }
        }
    }

    public void draw(Graphics g) {
        Color c = Color.yellow;
        c.a = 0.5f;
        g.setColor(c);
        g.fillOval(posX-repairRange/2, posY-repairRange/2, repairRange, repairRange);
        g.setColor(Color.green);
        g.fillOval(posX, posY, 32, 32);
    }
}
