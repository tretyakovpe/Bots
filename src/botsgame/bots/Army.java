/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
public class Army{
    public Set<Bot> bots;  //список ботов команды
    private Iterator<Bot> iter;
    private ArrayList<Bot> targets; //список обнаруженных целей
    private Army enemies; //список вражеских ботов
    public String teamName;
    private int botNum = 0; //для сквозной нумерации ботов, 
    private final Color flagColor;
    private boolean logged;

    public Army(String teamName, int Count, Color flagColor) throws SlickException {
        this.bots = Collections.synchronizedSet(new HashSet());
        this.teamName=teamName;
        this.flagColor=flagColor;
        for(int i=0; i<Count; i++)
        {
            botNum++;
            bots.add(new Bot(this.teamName+botNum, this.flagColor, this.teamName, logged));
        }
    }

    public void setLogging(boolean flag){
        logged=flag;
    }
    
    public void setTargets(Army enemies){
        this.enemies = enemies;
    }
    
    private void addBot(Bot bot){
        botNum++;
        bot.setLogging(logged);
        bots.add(bot);
        enemies.setTargets(this); 
    }
    
    private void removeBot(Bot bot){
//        bots.remove(bot);
        this.iter = bots.iterator();
        while(iter.hasNext())
        {
            if(iter.next()==bot)
            {
                iter.remove();
            }
        }
        enemies.setTargets(this); 
    }
    
    public void execute() throws SlickException
    {
        for(Bot bot : bots)
        {
            if (bot.botMode==0)
            {
                addBot(new Bot(this.teamName+botNum, this.flagColor, this.teamName, logged));
                removeBot(bot);
                break;
            }
            iter = enemies.bots.iterator();
            bot.doReload();
            while(iter.hasNext())
            {
                bot.look(iter.next());
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
