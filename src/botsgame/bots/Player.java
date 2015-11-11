/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

/**
 *
 * @author pavel.tretyakov
 */
public class Player extends Bot {

    public Player(String name, boolean logged) {
        super("Player", logged);
    }

   public void moveUp()
   {
       this.posY-=body.speed;
   }
   public void moveDown()
   {
       this.posY+=body.speed;
   }
   public void moveLeft()
   {
       this.posX-=body.speed;
   }
   public void moveRight()
   {
       this.posX+=body.speed;
   }
   
    @Override
   public void shoot(float x, float y)
   {
       super.shoot(x, y);
   }

    public void update() {
        super.doReload();
    }
}
