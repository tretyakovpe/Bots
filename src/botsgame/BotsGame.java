package BotsGame;

import static botsgame.Constants.*;
import botsgame.bots.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BotsGame extends BasicGame
{
    private boolean running;

    public int optionArmySize = 1; //кол-во ботов в команде	

    public Bot[] armyBlue = new Bot[optionArmySize];
    public Bot[] armyRed = new Bot[optionArmySize];

    public Landscape land = new Landscape(WORLD_SIZE,WORLD_SIZE);

    public int respawnBlueX=1,respawnBlueY=1; //позиция для респа синих
    public int respawnRedX=WORLD_SIZE-2,respawnRedY=WORLD_SIZE-2; //позиция для респа красных

        public BotsGame(String gamename)
	{
            super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
            landscapeInit();
            Bot.terrain = land;                    
            for (int i=0; i<optionArmySize; i++)
            {
                armyBlue[i] = new BlueBot();
                armyBlue[i].spawn(i);
                armyRed[i] = new RedBot();
                armyRed[i].spawn(i);
            }
}
        private void landscapeInit(){
            Random random = new Random();
            for(int y=0; y<WORLD_SIZE; y++)
            {
                for(int x=0; x<WORLD_SIZE; x++)
                {
                    int r=random.nextInt(5)+1;
                    land.setSurface(x, y, r);
                }
            }
        }
        
	@Override
	public void update(GameContainer gc, int i) throws SlickException {
            for(int j=0; j<optionArmySize; j++)
            {
                armyBlue[j].doAction(armyRed, j);
                armyRed[j].doAction(armyBlue, j);
            }
}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
            for(int i=0; i<optionArmySize; i++)
            {
                drawBot(g, armyBlue[i]);
                drawBot(g,armyRed[i]);
            }
	}

        private void drawBot(Graphics g, Bot bot)
        {
            g.drawAnimation(bot.body.image, (float) bot.posX*CELL_SIZE, (float) bot.posY*CELL_SIZE);
        }

        public static void main(String[] args)
	{
            try
            {
                AppGameContainer appgc;
                appgc = new AppGameContainer(new BotsGame("Bots"));
                appgc.setDisplayMode(1024, 768, false);
                appgc.setVSync(true); //включаем вертикальную синхронизацию
                appgc.start();
            }
            catch (SlickException ex)
            {
                Logger.getLogger(BotsGame.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}