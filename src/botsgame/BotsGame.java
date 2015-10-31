package botsgame;

import static botsgame.Constants.*;
import botsgame.bots.*;
import factories.BotFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BotsGame extends BasicGame
{
//    public Army armyBlue;
//    public Army armyRed;

    private BotFactory botFactory;
    private Landscape land;
    private Repair repairStation;
    public static List<Projectile> bullets;
    private static SpriteSheet hitSprite; 
    public static Animation hitAnimation;
    
    
    public BotsGame(String gamename) throws SlickException
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc){
        try {
            hitSprite = new SpriteSheet("/assets/images/hitting.png",64,64);
        } catch (SlickException ex) {
            Logger.getLogger(BotsGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        hitAnimation = new Animation(hitSprite,0,0,9,4,true,3,true);
        
        try {
            land = new Landscape("/assets/maps/map2.tmx");
            land.findWalls();
        } catch (SlickException ex) {
                System.out.println("Ошибка при загрузке карты");
        }

        botFactory = new BotFactory();
        
        int repairX = 512;
        int repairY=320;
        repairStation = new Repair(repairX,repairY);
        land.setTileId(repairX/CELL_SIZE, repairY/CELL_SIZE, 0, 2);

        Bot.terrain=land;

        bullets = new ArrayList();
        
        for(int i=0; i<ARMY_SIZE; i++)
        {
            Bot.allBots.add(new Bot("Bot "+i, true));
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
        
        ListIterator bullIter = bullets.listIterator();
        while (bullIter.hasNext())
        {
            Projectile bullet = (Projectile)bullIter.next();
            bullet.update(bullIter);
        }
        
        botFactory.update();
        
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
        land.render(0,0,2);
        land.render(0,0,3);
//        land.render(g);
        drawBots(g);
//        repairStation.draw(g);
        drawBullets(g);
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

    private void drawBullets(Graphics g)
    {
        Iterator it;
        it = bullets.iterator();
        while (it.hasNext())
        {
            Projectile bul = (Projectile)it.next();
            bul.draw(g);        
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
//            appgc.setMaximumLogicUpdateInterval(TIMER);
//            appgc.setMinimumLogicUpdateInterval(TIMER);

            appgc.start();
        }
        catch (SlickException ex)
        {
            System.out.println("Случилось страшное!");
        }
    }
}