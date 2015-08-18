/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;

import botsgame.equipment.Equipment;

/**
 *
 * @author pavel.tretyakov
 */
public class Landscape {

    private int x;
    private int y;
    private int[][] surface;
    private Obstacles[][] obstacle;

    public Landscape(int width, int height){
        x=width;
        y=height;
        surface = new int[x][y];
        obstacle= new Obstacles[x][y];
    }
            
    public Obstacles getObstacle(int x, int y) {
        return obstacle[x][y];
    }

    public int getSurface(int x, int y) {
        return surface[x][y];
    }
    
    public int[][] getWholeSurface(){
        return surface;
    }

    public void setSurface(int x, int y, int surface) {
        this.surface[x][y] = surface;
    }

    public void setObstacle(int x, int y, Obstacles obstacle) {
        this.obstacle[x][y] = obstacle;
    }

    public void setBotRemains(int x, int y, Equipment part) {
        BotRemains botRemains = new BotRemains(part);
        this.obstacle[x][y] = botRemains;
    }
   
}
