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
import org.newdawn.slick.util.pathfinding.Path.Step;

public class BotsGame extends BasicGame
{
    public int optionArmySize = 2; //кол-во ботов в команде	

    public Army armyBlue;
    public Army armyRed;

    public Landscape land;
    
    public BotsGame(String gamename) throws SlickException
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        land = new Landscape("/assets/maps/map1.tmx",1);
        Bot.terrain=land;
        armyBlue = new Army("Blue", optionArmySize,Color.blue);
        armyRed = new Army("Red", optionArmySize,Color.red);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if(armyBlue.bots.isEmpty()){System.out.println("Синие сдохли");}
        if(armyRed.bots.isEmpty()){System.out.println("Красные сдохли");}
        armyBlue.execute(armyRed);
        armyRed.execute(armyBlue);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.setDrawMode(1);
        land.render(0,0);
        for(Bot blueBot: armyBlue.bots)
        {
            drawBot(g, blueBot);
        }
        for(Bot redBot: armyRed.bots)
        {
            drawBot(g, redBot);
        }
    }

    private void drawBot(Graphics g, Bot bot)
    {
        int x=bot.posX*CELL_SIZE;
        int y=bot.posY*CELL_SIZE;
        g.setColor(bot.flagColor);
        g.drawRect((float) x, (float) y, CELL_SIZE, CELL_SIZE);
        g.drawAnimation(bot.body.image, (float) x, (float) y);
        g.drawAnimation(bot.power.image, (float) x, (float) y);
        g.drawAnimation(bot.weapon.image, (float) x, (float) y);



        /*if(bot.botMode==3)
        {
            for(int i=0;i<bot.path.getLength();i++)
            {
                step=bot.path.getStep(i);
                x=step.getX();
                y=step.getY();
                g.setColor(bot.flagColor);
                g.fillOval(x*CELL_SIZE+16, y*CELL_SIZE+16, 16, 16);
            }
        }*/

        if(bot.botMode==4)
        {
            g.setColor(bot.flagColor);
            g.drawLine(x+16, y+5, bot.target.posX*CELL_SIZE,  bot.target.posY*CELL_SIZE);
            g.fillOval(bot.target.posX*CELL_SIZE,  bot.target.posY*CELL_SIZE,10,10);
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
//            Logger.getLogger(BotsGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}