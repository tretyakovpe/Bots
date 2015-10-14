package botsgame;

import static botsgame.Constants.*;
import botsgame.bots.*;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BotsGame extends BasicGame
{
//    public Army armyBlue;
//    public Army armyRed;

    public Landscape land;
    public Repair repairStation;
    
    public BotsGame(String gamename) throws SlickException
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc){
        try {
            land = new Landscape("/assets/maps/map1.tmx");
        } catch (SlickException ex) {
                System.out.println("Ошибка при загрузке карты");
        }
        land.findWalls();
        int repairX = 512;
        int repairY=320;
        repairStation = new Repair(repairX,repairY);
        land.setTileId(repairX/CELL_SIZE, repairY/CELL_SIZE, 0, 2);
        Bot.terrain=land;
        
        for(int i=0; i<ARMY_SIZE; i++)
        {
            Bot.allBots.add(new Bot("Bot "+i, Color.blue, "blue", true));
        }
    }

    @Override
    public void update(GameContainer gc, int i)
    {
        ListIterator it = Bot.allBots.listIterator();
        while (it.hasNext())
        {
            Bot bot = (Bot)it.next();
            if(bot.isDead() == false)
            {
                bot.execute(it);
            }
        }
        repairStation.doRepair();
        
        
        if (Mouse.isButtonDown(0)) {
            
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
        
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
        
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
        
        }

        
    }

    @Override
    public void render(GameContainer gc, Graphics g)
    {
        g.setDrawMode(1);
        land.render(0,0,0);
        land.render(0,0,1);
//        land.render(g);
        drawBots(g);
        land.render(0,0,2);
        repairStation.draw(g);

    }

    private void drawBots(Graphics g)
    {
        Iterator it;
        it = Bot.allBots.iterator();
        while (it.hasNext())
        {
            Bot bot = (Bot)it.next();
            if(bot.isDead() == false)
            {
                bot.drawBot(g);        
            }
        }        
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc = new AppGameContainer(new BotsGame("Bots"));
            appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            appgc.setAlwaysRender(true);
            appgc.setVSync(true); //включаем вертикальную синхронизацию
            appgc.setMinimumLogicUpdateInterval(TIMER);

            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(BotsGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}