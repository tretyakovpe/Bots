/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author pavel.tretyakov
 */
public class Landscape extends TiledMap implements TileBasedMap{
    private final int obstacle;

    public Landscape(String ref, int obstacle) throws SlickException {
        super(ref);
        this.obstacle = obstacle;
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
        return this.getTileId(x, y, obstacle) != 0;
    }

    public void setObstacle(int x, int y){
        this.setTileId(x, y, 1, 2);
    }
    
    @Override
    public float getCost(PathFindingContext pfc, int i, int i1) {
        return 1.0f;
    }
   
}
