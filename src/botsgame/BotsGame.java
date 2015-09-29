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

public class BotsGame extends BasicGame
{
    public Army armyBlue;
    public Army armyRed;
    public Landscape land;
    public Repair repairStation;
    
    public BotsGame(String gamename) throws SlickException
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        land = new Landscape("/assets/maps/map1.tmx");
        repairStation = new Repair(400,400);
        Bot.terrain=land;
        armyBlue = new Army("Blue", ARMY_SIZE, Color.blue);
        armyRed = new Army("Red", ARMY_SIZE, Color.red);
        
        armyBlue.setTargets(armyRed);
        armyRed.setTargets(armyBlue);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        armyBlue.execute();
        repairStation.doRepair(armyRed);
        armyRed.execute();
        repairStation.doRepair(armyBlue);
        
        
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
        repairStation.draw(g);
        armyBlue.drawArmy(g);
        armyRed.drawArmy(g);
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