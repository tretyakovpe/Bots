/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

/**
 *
 * @author pavel.tretyakov
 * 
 * Главный класс, от которого плодятся все классы возможных предметов на поле
 * 
 */
public abstract class Obstacles {
    
    public int posX;
    public int posY;
    public int health;
    
    public void doDamage(int damage)
    {
        this.health-=damage;
    }
    
    
}

