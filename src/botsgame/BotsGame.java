package botsgame;

import static botsgame.Constants.*;
import botsgame.bots.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BotsGame extends BasicGame
{
    public int optionArmySize = 2; //кол-во ботов в команде	

    public Bot[] armyBlue = new Bot[optionArmySize];
    public Bot[] armyRed = new Bot[optionArmySize];

    public Landscape land;

    public int respawnBlueX=1,respawnBlueY=1; //позиция для респа синих
    public int respawnRedX=WORLD_SIZE-2,respawnRedY=WORLD_SIZE-2; //позиция для респа красных

        public BotsGame(String gamename)
	{
            super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
            landscapeInit();
            for (int i=0; i<optionArmySize; i++)
            {
                armyBlue[i] = new BlueBot();
                armyBlue[i].spawn(i);
                armyRed[i] = new RedBot();
                armyRed[i].spawn(i);
            }
}
        private void landscapeInit() throws SlickException{
            land = new Landscape(WORLD_SIZE, WORLD_SIZE,"/assets/maps/map1.tmx");
            Bot.terrain=land;
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
            land.render(0,0);
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
                appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
                appgc.setVSync(true); //включаем вертикальную синхронизацию
                appgc.start();
            }
            catch (SlickException ex)
            {
                Logger.getLogger(BotsGame.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}