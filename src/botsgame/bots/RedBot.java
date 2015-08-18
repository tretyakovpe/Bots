package botsgame.bots;

import java.awt.Color;
import static botsgame.Constants.*;


public class RedBot extends Bot 
{
    //позиция для респа красных
    protected int respawnX = WORLD_SIZE - 1;
    protected int respawnY = WORLD_SIZE - 1;
    
    public RedBot() 
    {
        flagColor = Color.red;
    }
    
    @Override
    public void init(String name, int team, int X, int Y)
    {
        super.init("R-" + name, team, X, Y, flagColor);
//        System.out.println("R-" + name+" появился в "+X+"-"+Y);
    }
    
    @Override
    public void die() 
    {
        super.die();
    }
}
