package botsgame.bots;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pavel.tretyakov
 */
public class Army{
    private Set<Bot> friendlyBots;  //список ботов команды
    private Set<Bot> enemyBots;  //список ботов врагов
    private Iterator<Bot> iter;
    
    public String teamName;
    private int botNum = 0; //для сквозной нумерации ботов, 
    private final Color flagColor;
    private boolean logged;

    public Army(String teamName, int Count, Color flagColor) throws SlickException {
        this.friendlyBots = Collections.synchronizedSet(new HashSet());
        this.enemyBots = Collections.synchronizedSet(new HashSet());
        this.teamName=teamName;
        this.flagColor=flagColor;
        for(int i=0; i<Count; i++)
        {
            botNum++;
            Bot bot = new Bot(this.teamName+botNum, this.flagColor, this.teamName, logged);
            Bot.allBots.add(bot);
            friendlyBots.add(bot);
        }
    }

    public void setLogging(boolean flag){
        logged=flag;
    }
    
    
    private void addBot(Bot bot){
        botNum++;
        bot.setLogging(logged);
        Bot.allBots.add(bot);
//        friendlyBots.add(bot);
    }
    
    private void removeBot(Bot bot){
//        bots.remove(bot);
        this.iter = friendlyBots.iterator();
        while(iter.hasNext())
        {
            if(iter.next()==bot)
            {
                iter.remove();
            }
        }
    }
    
}
