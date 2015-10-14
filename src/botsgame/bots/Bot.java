package botsgame.bots;

import static botsgame.Constants.*;
import botsgame.Landscape;
import botsgame.Landscape.Wall;
import botsgame.equipment.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import java.util.Random;
import java.util.Set;
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
    public static List<Bot> allBots = new LinkedList(); //список всех ботов на поле
    private Set<Bot> enemyBots = new HashSet();  //список ботов-врагов

    private static Animation explosionAnimation;
    private static Animation hitAnimation;
    private static SpriteSheet explosionSprite;
    private static SpriteSheet hitSprite; 
    private SpriteSheet flagSheet;
    private Image flagImage;
    private final Image[] flags = new Image[4];

    protected AStarPathFinder pathFinder;
    public Path path;

    public String name;
    public Color flagColor;
    
    private boolean dead = false;

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
//            System.out.println(string);
        }
    }
    

    public Bot(String name, Color color, String flagColor, boolean logged)
    {
        super();
        try {
            hitSprite = new SpriteSheet("/assets/images/hit.png",32,32);
            hitAnimation = new Animation(hitSprite,0,0,23,0,true,20,true);
        } catch (SlickException ex) {
            System.out.println("Ошибка при загрузке изображения попадания");
        }
        try {
            explosionSprite = new SpriteSheet("/assets/images/explosion.png",64,64);
            explosionAnimation = new Animation(explosionSprite,0,0,3,3,true,150,true);
        } catch (SlickException ex) {
            System.out.println("Ошибка при загрузке изображения взрыва");
        }
        try {
            flagSheet = new SpriteSheet("/assets/images/"+flagColor+"flag.png",32,32);
            for(int i=0; i<4; i++)
            {
                flags[i]=flagSheet.getSprite(i,0);
            }
            this.flagImage = this.flags[0];
        } catch (SlickException ex) {
            System.out.println("Ошибка при загрузке изображения флага");
        }

        random=new Random();
        this.pathFinder = new AStarPathFinder((TileBasedMap) terrain, 80, false);
        int x = 1;//random.nextInt(3);
        int y = 1;//random.nextInt(3);
        while (terrain.blocked(pathFinder, Math.round(x/CELL_SIZE), Math.round(y/CELL_SIZE))==true)
        {
            x = Math.round(random.nextInt(WINDOW_WIDTH)/CELL_SIZE)*CELL_SIZE;
            y = Math.round(random.nextInt(WINDOW_HEIGHT)/CELL_SIZE)*CELL_SIZE; 
        }
        this.name = name;
        this.flagColor=color;
        this.botMode = 1;
        this.posX = x;
        this.posY = y;
        this.targetDistance = 888888;
        this.botDirection=0;
        this.stepDirection=0;
        this.targetDirection=0;
        this.point = new PointF(0,0);
        this.dead = false;
        this.logged = logged;
        setEquipment();
        super.maxHealth = this.body.durability+this.power.durability;
        super.currentHealth=super.maxHealth;
        printLog("Появился "+this.name+" в "+this.posX+"-"+this.posY);
    }
    
    private void setTargets(){
        enemyBots.clear();
        Iterator it;
        it = Bot.allBots.iterator();
        while (it.hasNext())
        {
            Bot bot = (Bot)it.next();
            if(bot.isDead() == false && bot!=this)
            {
                enemyBots.add(bot);
                printLog(this.name+" записал "+bot.name+" во враги");
            }
        }
    }
    
    public void execute(ListIterator it) 
    {
        if (botMode == 0)
        {
            dead = true;
            it.remove();
            it.add(new Bot(name, flagColor, "blue", logged));
        }
        else
        {
            setTargets();
            doReload();
            Iterator iter;
            iter = enemyBots.iterator();
            while (iter.hasNext())
            {
                Bot bot = (Bot)iter.next();
                if(bot.isDead() == false)
                {
                    look(bot);
                }
            }
        }
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
            if(point.x>X1 && point.x<=X1+CELL_SIZE && point.y>Y1 && point.y <= Y1+CELL_SIZE)
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

            //если мало здоровья, то идем лечиться
            float t = currentHealth*100f/maxHealth;
            if(t<=50f)
            {
                X1=512;
                Y1=320;
            }
            
            //дистанция пути
            path = pathFinder.findPath(this, Math.round(X/CELL_SIZE), Math.round(Y/CELL_SIZE), Math.round(X1/CELL_SIZE), Math.round(Y1/CELL_SIZE));
            if(path!=null)
            {
                float nextPosX = path.getX(1)*CELL_SIZE;
                float nextPosY = path.getY(1)*CELL_SIZE;
                
                ListIterator it = Bot.allBots.listIterator();
                while (it.hasNext())
                {
                    Bot b = (Bot)it.next();
                    if(b.isDead() == false)
                    {
                        float speed = body.speed/path.getLength();

                        float stepX=(nextPosX-posX)/speed; //знаменатель определяет количество кадров на тайл карты. Обязательно четное
                        float stepY=(nextPosY-posY)/speed;

                        stepDirection = (float)(180/Math.PI)*(float)Math.atan2((nextPosY-Y),(nextPosX-X));

                        if(posX >= b.posX && posX <= b.posX+CELL_SIZE &&
                           posY >= b.posY && posY <= b.posY+CELL_SIZE)
                        {
                            break;
                        }
                        else
                        {
                            posX+=stepX;
                            posY+=stepY;
                        }
                    }
                }
            }
        }
    }

    public boolean isDead() {
        return dead;
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
        float dx = startx+CELL_SIZE/2;
        float dy = starty+CELL_SIZE/2;
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
            if (dx >= endX && dx <= endX + CELL_SIZE && dy >= endY && dy <= endY + CELL_SIZE) 
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
        printLog(name+" стреляет в "+target.name);
        target.doDamage(this.weapon.damage);
        target.botMode = 2;
        this.reloading = 100/this.weapon.speed;
        if(target.currentHealth<=0)
        {
            target.botMode=0;
            botMode=1;
            printLog(name+" убил "+target.name);
            
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
    
    private void setEquipment()
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

    public void drawBot(Graphics g)
    {
        float x=posX;
        float y=posY;
        g.setColor(flagColor);
        g.drawLine(x+CELL_SIZE/2,y+CELL_SIZE/2,point.x, point.y);
//        g.drawString(name, x, y-13);
        
        body.image.setRotation(stepDirection);
        body.image.draw(x, y);
//        flagImage.draw(x, y);
        weapon.image.setRotation(targetDirection);
        weapon.image.draw(x, y);
        switch (botMode)
        {
            case 0:
                g.drawAnimation(explosionAnimation,  x,  y);
                break;
            case 1:
                break;
            case 2:
                g.drawAnimation(hitAnimation, x, y);
                break;
        }
        g.setColor(Color.black);
        g.fillRect(x, y+CELL_SIZE+1, CELL_SIZE, 5);
        g.setColor(Color.red);
        g.fillRect(x+1, y+CELL_SIZE+2, CELL_SIZE-2, 3);
        g.setColor(Color.green);
        g.fillRect(x+1, y+CELL_SIZE+2, (int) CELL_SIZE*this.currentHealth/this.maxHealth, 3);
        
//        g.drawString(String.valueOf(reloading),x+32, y);
        
//        g.drawLine(x, y, targetX, targetY);
    }
    
}
