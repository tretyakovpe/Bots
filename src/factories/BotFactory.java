package factories;

import static botsgame.Constants.RESPAWN_TIMER;
import botsgame.bots.Bot;

public class BotFactory {
    
    private int tick; //отсчёт для выпуска нового бота. 

    public BotFactory() {
        this.tick = RESPAWN_TIMER;
    }

    public void update() {
        tick--;
        if(tick==0)
        {
            Bot.allBots.add(new Bot("Bot", true));
            tick = RESPAWN_TIMER;
        }
    }
    
}
