package botsgame;

import static botsgame.Constants.*;
import botsgame.bots.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Path.Step;

public class BotsGame extends BasicGame
{
    Animation explosionAnimation;
    Animation hitAnimation;
    SpriteSheet explosionSprite;
    SpriteSheet hitSprite;
    public int optionArmySize = 1; //кол-во ботов в команде	

    public Army armyBlue;
    public Army armyRed;

    public Landscape land;
    
    public BotsGame(String gamename) throws SlickException
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
    
        hitSprite = new SpriteSheet("/assets/images/hit.png",32,32); 
        hitAnimation = new Animation(hitSprite,0,0,23,0,true,20,true);
        explosionSprite = new SpriteSheet("/assets/images/explosion.png",64,64); 
        explosionAnimation = new Animation(explosionSprite,0,0,3,3,true,150,true);   
        
        
        land = new Landscape("/assets/maps/map1.tmx",1);
        Bot.terrain=land;
        armyBlue = new Army("Blue", optionArmySize, Color.blue);
        armyRed = new Army("Red", optionArmySize, Color.red);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        armyBlue.execute(armyRed);
        armyRed.execute(armyBlue);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.setDrawMode(1);
        land.render(0,0);

        for(Bot bot: armyBlue.bots)
        {
            drawBot(g, bot);
        }
        for(Bot bot: armyRed.bots)
        {
            drawBot(g, bot);
        }
    }

    private void drawBot(Graphics g, Bot bot) throws SlickException
    {
        float x=bot.posX;
        float y=bot.posY;
        bot.body.image.draw(x, y);
        bot.flagImage.draw(x, y);
        bot.weapon.image.draw(x, y);
        switch (bot.botMode)
        {
            case 0:
                g.drawAnimation(explosionAnimation,  x-16,  y-16);
                break;
            case 1:
                break;
            case 2: 
                break;
            case 3:
                break;
            case 4: 
                g.setColor(bot.flagColor);
                g.drawLine(x+16, y+16, bot.target.posX+16,  bot.target.posY+16);
                hitAnimation.draw(bot.target.posX,  bot.target.posY);
                break;
            case 5: 
                break;
            case 6: 
                break;
        }
        
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc = new AppGameContainer(new BotsGame("Bots"));
            appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            appgc.setAlwaysRender(false);
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