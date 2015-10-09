package botsgame.bots;

import static botsgame.Constants.*;
import botsgame.Landscape;
import botsgame.Landscape.Wall;
import botsgame.equipment.*;

import java.util.Random;
import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author pavel.tretyakov
 */
public class Bot extends Obstacles implements Mover{

    public static Landscape terrain;
    private static Animation explosionAnimation;
    private static Animation hitAnimation;
    private static SpriteSheet explosionSprite;
    private static SpriteSheet hitSprite; 
    protected AStarPathFinder pathFinder;
    public Path path;

    public String name;
    public Color flagColor;
    
    private final SpriteSheet flagSheet;
    public Image flagImage;
    private final Image[] flags = new Image[4];

    public Body body = new Body();
    public Weapon weapon = new Weapon();
    public Power power = new Power();
    public Comp comp = new Comp();

    public float fireDistance;
    public int targetDistance;
    
    public float botDirection; //направление переда бота
    public float stepDirection; //направление следующего шага бота
    public float targetDirection; //направление на цель
    
    private int reloading; //перезарядка оружия
    
    private final Random random;
    
    protected int botMode;

    private boolean logged;
    private PointF point;
    public void setLogging(boolean flag){
        this.logged=flag;
    }
    
    private void printLog(String string){
        if(logged==true)
        {
            System.out.println(string);
        }
    }
    

    public Bot(String name, Color color, String flagColor, boolean logged) throws SlickException
    {
        super();
        random=new Random();
        this.pathFinder = new AStarPathFinder((TileBasedMap) terrain, 80, false);
        int x = 12;//random.nextInt(3);
        int y = 12;//random.nextInt(3);
        while (terrain.blocked(pathFinder, Math.round(x/CELL_SIZE), Math.round(y/CELL_SIZE))==true)
        {
            x = Math.round(random.nextInt(800)/32)*32;
            y = Math.round(random.nextInt(800)/32)*32; 
        }
        hitSprite = new SpriteSheet("/assets/images/hit.png",32,32); 
        hitAnimation = new Animation(hitSprite,0,0,23,0,true,20,true);
        explosionSprite = new SpriteSheet("/assets/images/explosion.png",64,64); 
        explosionAnimation = new Animation(explosionSprite,0,0,3,3,true,150,true);   
        flagSheet = new SpriteSheet("/assets/images/"+flagColor+"flag.png",32,32); //спрайт игрока
        for(int i=0; i<4; i++)
        {
            flags[i]=flagSheet.getSprite(i,0);
        }
        this.flagImage = this.flags[0];

        this.name = name;
        this.flagColor=color;
        this.botMode = 1;
        this.posX = x;
        this.posY = y;
//        this.targetX = 0;
//        this.targetY = 0;
        this.targetDistance = 888888;
        this.botDirection=0;
        this.stepDirection=0;
        this.targetDirection=0;
        this.point = new PointF(0,0);
        setEquipment();
        super.maxHealth = this.body.durability+this.power.durability;
        super.currentHealth=super.maxHealth;
        printLog("Появился "+this.name+" в "+this.posX+"-"+this.posY);
    }
    
    protected void look(Bot enemyBot)
    {
        if(enemyBot.botMode!=0){
            float X1 = enemyBot.posX;
            float Y1 = enemyBot.posY;
            float X = this.posX;
            float Y = this.posY;
            enemyBot.botMode=1;
            targetDirection = (float)(180/Math.PI)*(float)Math.atan2((Y1-Y),(X1-X));
            point = rayCast(X,Y,X1,Y1);
            if(point.x>X1 && point.x<=X1+32 && point.y>Y1 && point.y <= Y1+32)
            {
                if(this.reloading == 0)
                {
                    fireDistance = (float)Math.sqrt((X1-X)*(X1-X)+(Y1-Y)*(Y1-Y));
                    if(fireDistance<weapon.range)
                    {
                            shoot(enemyBot);
                            return;
                    }
                }
            }

            float t = currentHealth/maxHealth*100f;
            if(t<=50)
            {
                X1=400;
                Y1=400;
            }
            
            //дистанция пути
            path = pathFinder.findPath(this, Math.round(X/CELL_SIZE), Math.round(Y/CELL_SIZE), Math.round(X1/CELL_SIZE), Math.round(Y1/CELL_SIZE));
            if(path!=null)
            {
                float nextPosX = path.getX(1)*CELL_SIZE;
                float nextPosY = path.getY(1)*CELL_SIZE;

                float stepX=(nextPosX-posX)/body.speed; //знаменатель определяет количество кадров на тайл карты. Обязательно четное
                float stepY=(nextPosY-posY)/body.speed;
                
                stepDirection = (float)(180/Math.PI)*(float)Math.atan2((nextPosY-Y),(nextPosX-X));
                posX+=stepX;
                posY+=stepY;
            }
        }
    }

    class PointF {
            public float x;
            public float y;

            public PointF(float x, float y) {
                    this.x = x;
                    this.y = y;
            }

    }
        
    public PointF rayCast(float startx, float starty, float endX, float endY) {
        float dx = startx+16;
        float dy = starty+16;
        float diffx = -(startx-endX)/100;
        float diffy = -(starty-endY)/100;
        while (true) 
        {
            dx += diffx;
            dy += diffy;
            boolean hit = false;
            for (Wall w : terrain.walls) {
                if (dx > w.x && dx < w.x + w.w && dy > w.y && dy < w.y + w.h) {
                    hit = true;
                    break;
                }
            }
            if (hit) break;
            if (dx >= endX && dx <= endX + 32 && dy >= endY && dy <= endY + 32) 
            {
                break;
            }
            if (dx > (float)WINDOW_WIDTH) break;
            if (dx < 0) break;
            if (dy > (float)WINDOW_HEIGHT) break;
            if (dy < 0) break;
        }
        return new PointF(dx, dy);
    }

    protected void shoot(Bot target){
        target.doDamage(this.weapon.damage);
        target.botMode = 2;
        this.reloading = 100/this.weapon.speed;
        if(target.currentHealth<=0)
        {
            target.botMode=0;
            botMode=1;
        }
        this.targetDistance=99999;
    }
    
    protected void doReload()
    {
        if(this.reloading>0)
        {
            this.reloading -= power.power;
            if(this.reloading<0)
            {
                this.reloading=0;
            }
        }
    }
    
    private void setEquipment() throws SlickException
    {
        int bodyId = random.nextInt(3);
        int weaponId = random.nextInt(3);
        int powerId = random.nextInt(2);
        int compId = random.nextInt(3);
        
        switch (bodyId)
        {
            case 0:
                body.truck();
                break;
            case 1:
                body.wheel();
                break;
            case 2:
                body.antigrav();
                break;
        }

        switch (weaponId)
        {
            case 0:
                weapon.cannon();
                break;
            case 1:
                weapon.laser();
                break;
            case 2:
                weapon.plasma();
                break;
        }
        switch (powerId)
        {
            case 0:
                power.dieselEngine();
                break;
            case 1:
                power.nuclearReactor();
                break;
        }

        switch (compId)
        {
            case 0:
                comp.deskComp();
                break;
            case 1:
                comp.militaryComp();
                break;
            case 2:
                comp.nasaComp();
                break;
        }
    }

    protected void drawBot(Graphics g) throws SlickException
    {
        float x=posX;
        float y=posY;
        g.setColor(flagColor);
//        g.drawLine(x+16,y+16,point.x, point.y);
//        g.drawString(name, x, y-13);
        
        body.image.setRotation(stepDirection);
        body.image.draw(x, y);
        flagImage.draw(x, y);
        weapon.image.setRotation(targetDirection);
        weapon.image.draw(x, y);
        switch (botMode)
        {
            case 0:
                g.drawAnimation(explosionAnimation,  x-16,  y-16);
                break;
            case 1:
                break;
            case 2:
                g.drawAnimation(hitAnimation, x, y);
                break;
        }
        g.setColor(Color.black);
        g.fillRect(x, y+33, 32, 5);
        g.setColor(Color.red);
        g.fillRect(x+1, y+34, 30, 3);
        g.setColor(Color.green);
        g.fillRect(x+1, y+34, (int) 30f*this.currentHealth/this.maxHealth, 3);
        
        g.drawString(String.valueOf(reloading),x+32, y);
        
//        g.drawLine(x, y, targetX, targetY);
    }
    
}
