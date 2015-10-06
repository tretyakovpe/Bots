/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame;

import static botsgame.Constants.*;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
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

    public static class Wall {
       public float x;
       public float y;
       public float w;
       public float h;

       public Wall(float x, float y, float w, float h) {
                this.x = x;
                this.y = y;
                this.w = w;
                this.h = h;
        }
    }

    private final int obstacles;
    public List<Wall> walls;
    
    public Landscape(String ref) throws SlickException {
        super(ref);
        this.walls = new ArrayList();
        this.obstacles = 1;
    }

    public void findWalls(){
        for(int y=0; y<WORLD_SIZE; y++)
        {
            for(int x=0; x<WORLD_SIZE; x++)
            {
                if(this.getTileId(x, y, 1)>0)
                {
                    walls.add(new Wall(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE));
                }
            }
        }
    }
         
    public void render(Graphics g){
        g.setColor(new Color(255,255,255,128));
        for(Wall w: walls)
        {
            g.fillRect(w.x, w.y, w.w, w.h);
        }
    }
    
    @Override
    public int getWidthInTiles() {
        return this.getWidth();
    }

    @Override
    public int getHeightInTiles() {
        return this.getHeight();
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
    }

    @Override
    public boolean blocked(PathFindingContext pfc, int x, int y) {
        return this.getTileId(x, y, obstacles) != 0;
    }

    public void setObstacle(int x, int y){
        this.setTileId(x, y, 1, 2);
    }
    
    @Override
    public float getCost(PathFindingContext pfc, int i, int i1) {
        return 1.0f;
    }
   
}
