package botsgame;

import static botsgame.Constants.*;
import botsgame.bots.*;
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

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Path.Step;

public class BotsGame extends BasicGame
{
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
    
        
        
        land = new Landscape("/assets/maps/map1.tmx");
        Bot.terrain=land;
        armyBlue = new Army("Blue", optionArmySize, Color.blue);
        armyRed = new Army("Red", optionArmySize, Color.red);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        armyBlue.execute(armyRed);
        armyRed.execute(armyBlue);
        
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
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.setDrawMode(0);
        land.render(0,0);
        armyBlue.drawArmy(g);
        armyRed.drawArmy(g);
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