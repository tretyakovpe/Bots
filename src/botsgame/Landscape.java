/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame;

import botsgame.bots.*;
import botsgame.equipment.*;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author pavel.tretyakov
 */
public class Landscape extends TiledMap implements TileBasedMap{

    private int h;
    private int v;
    private int[][] surface;
    private Obstacles[][] obstacle;

    public Landscape(int h, int v, String ref) throws SlickException 
    {
        super(ref);
        h=width;
        v=height;
        surface = new int[width][height];
        obstacle= new Obstacles[width][height];
        Random random = new Random();
        for(int y=0; y<height; y++)
        {
            for(int x=0; x<width; x++)
            {
                int r=random.nextInt(2)+6;
                surface[x][y] = r;
            }
        }

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

    public void render(Graphics g)
    {
        
    }
    
    
    @Override
    public int getWidthInTiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getHeightInTiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean blocked(PathFindingContext pfc, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getCost(PathFindingContext pfc, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
