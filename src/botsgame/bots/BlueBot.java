package botsgame.bots;

import java.awt.Color;

public class BlueBot extends Bot 
{
    public BlueBot() 
    {
        flagColor = Color.blue;
    }

    @Override
    public void init(String name, int team, int X, int Y)
    {
        super.init("B-" + name, team, X, Y, flagColor);
        System.out.println("B-" + name+" появился в "+X+"-"+Y);
        
    }
    
    @Override
    public void die() 
    {
        super.die();
    }
}
