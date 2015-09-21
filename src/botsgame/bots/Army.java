/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
public class Army {
    public ArrayList<Bot> bots;  //список ботов команды
    private static ArrayList<Bot> targets; //список обнаруженных целей
    public String teamName;
    private int botNum = 0; //для сквозной нумерации ботов, 
    private final Color flagColor;

    public Army(String teamName, int Count, Color flagColor) throws SlickException {
        this.bots = new ArrayList();
        this.teamName=teamName;
        this.flagColor=flagColor;
        for(int i=0; i<Count; i++)
        {
            botNum++;
            bots.add(new Bot(this.teamName+" Bot-"+botNum, this.flagColor, this.teamName));
        }
    }

    public void execute(Army enemies) throws SlickException
    {
        for(Bot bot : bots)
        {
            bot.doReload();
            switch (bot.botMode)
            {
                case 0: //смерть
                    bot.die();
                    botNum++;
                    bots.set(bots.indexOf(bot), new Bot(this.teamName+"Bot-"+botNum, this.flagColor,  this.teamName));
                    break;
                case 1: //просмотр целей
                    bot.see(enemies);
                    break;
                case 2: //прицеливание
                    bot.aim();
                    break;
                case 3: //движение
                    bot.move();
                    break;
                case 4: //стрельба
                    bot.shoot();
                    break;
                case 5: //поиск целей, патрулирование
                    break;
                case 6: //поворот
                    bot.rotate();
                    break;
            }
        }
    }
    
    public void drawArmy(Graphics g) throws SlickException{
        for(Bot bot : bots)
        {
            bot.drawBot(g);        
        }        
    }

}
