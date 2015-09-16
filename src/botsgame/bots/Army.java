/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
public class Army {
    public ArrayList<Bot> bots;
    public String teamName;
    private int numberOfBot = 0;
    private final Color flagColor;

    public Army(String teamName, int Count, Color flagColor) throws SlickException {
        this.bots = new ArrayList();
        this.teamName=teamName;
        this.flagColor=flagColor;
        for(int i=0; i<Count; i++)
        {
            numberOfBot++;
            bots.add(new Bot(this.teamName+"Bot-"+numberOfBot, this.flagColor));
        }
    }

    public void execute(Army enemies) throws SlickException
    {
        for(Bot bot: this.bots)
        {
            switch (bot.botMode)
            {
                case 0:
                    bot.die();
                    int pos = bots.indexOf(bot);
                    bots.remove(bot);
                    numberOfBot++;
                    bots.add(pos, new Bot(this.teamName+"Bot-"+numberOfBot, this.flagColor));
                    break;
                case 1: 
                    bot.see(enemies);
                    break;
                case 2: 
                    bot.aim();
                    break;
                case 3: 
                    bot.move();
                    break;
                case 4: 
                    bot.shoot();
                    break;
                case 5: 
                    break;
            }
        }
    }

}
