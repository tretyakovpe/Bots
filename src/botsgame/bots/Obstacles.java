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
    
    public float posX;
    public float posY;
    public int maxHealth;
    public int currentHealth;
    
    public void doDamage(int damage)
    {
        this.currentHealth-=damage;
    }
    
    
}

