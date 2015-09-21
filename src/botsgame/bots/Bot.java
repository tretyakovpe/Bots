package botsgame.bots;

import static botsgame.Constants.*;
import botsgame.Landscape;
import botsgame.equipment.Comp;
import botsgame.equipment.Power;
import botsgame.equipment.Weapon;
import botsgame.equipment.Body;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
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
    protected AStarPathFinder pathFinder;
    public Path path;

    public String name;
    public Color flagColor;
    
    private SpriteSheet flagSheet;
    public Image flagImage;
    private Image[] flags = new Image[4];

    public Body body = new Body();
    public Weapon weapon = new Weapon();
    public Power power = new Power();
    public Comp comp = new Comp();

    public Bot target;
    public int fireDistance;
    public int targetDistance;
    
    public float botDirection; //направление переда бота
    public float stepDirection; //направление следующего шага бота
    public float targetDirection; //направление на цель
    
    
    private int reloading; //перезарядка оружия
    public float t; //перемещение из клетки в клетку
    
    private final Random random;
    
    private float stepX, stepY = 0;
    
    
    protected int respawnX;
    protected int respawnY;

    
    /**
     *режим работы бота
     * 
     * 0-мертв
     * 1-поиск цели
     * 2-прицеливание
     * 3-движение к target
     * 4-стрельба
     * 5-поиск цели
     * 6-поворачивание
     */
    public int botMode;

    public Bot(String name, Color color, String flagColor) throws SlickException
    {
        super();
        random=new Random();
        this.pathFinder = new AStarPathFinder((TileBasedMap) terrain, 60, false);
        int x = 0;//random.nextInt(3);
        int y = 0;//random.nextInt(3);
        while (terrain.blocked(pathFinder, Math.round(x/CELL_SIZE), Math.round(y/CELL_SIZE))==true)
        {
            x = Math.round(random.nextInt(800)/32)*32;
            y = Math.round(random.nextInt(800)/32)*32; 
        }
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
        this.nextPosX = x;
        this.nextPosY = y;
        this.target = null;
        this.targetDistance = 99999;
        this.botDirection=0;
        this.stepDirection=0;
        this.targetDirection=0;
        setEquipment(random.nextInt(3),random.nextInt(3),random.nextInt(2));
        super.maxHealth = this.body.durability+this.power.durability;
        super.currentHealth=super.maxHealth;
//        System.out.println("Появился "+this.name+" в "+this.posX+"-"+this.posY);
    }

    void see(Army enemies){
        for(Bot enemyBot: enemies.bots)
        {
            float X1 = enemyBot.posX;
            float Y1 = enemyBot.posY;
            float X = this.posX;
            float Y = this.posY;
//            System.out.println(this.name+" видит "+enemyBot.name);
            fireDistance = (int)Math.round(Math.sqrt((X1-X)*(X1-X)+(Y1-Y)*(Y1-Y)));
            targetDirection = (float)(180/Math.PI)*(float)Math.atan2((Y1-Y),(X1-X));
//            System.out.println(this.name+" от "+enemyBot.name+" на расстоянии "+fireDistance);
            this.weapon.image.setRotation(targetDirection);
            if(this.weapon.range>=fireDistance)
            {
                target=enemyBot;
                botMode=2;
            }
            else
            {
                //Вычисляем дистанцию до бота, если она меньше предыдущей, выбираем его в качестве цели.
                path = pathFinder.findPath(this, Math.round(X/CELL_SIZE), Math.round(Y/CELL_SIZE), Math.round(X1/CELL_SIZE), Math.round(Y1/CELL_SIZE));

                if (path!=null)
                {
                    nextPosX = path.getX(1)*CELL_SIZE;
                    nextPosY = path.getY(1)*CELL_SIZE;
                    
                    //
                    stepX=(nextPosX-posX)/body.speed; //знаменатель определяет количество кадров на тайл карты. Обязательно четное
                    stepY=(nextPosY-posY)/body.speed;
                    
                    int distance = path.getLength();
                    if(distance <= this.targetDistance)
                    {
                        target=enemyBot;
//                        System.out.println(this.name+" выбрал целью "+enemyBot.name+" в "+X1+"-"+Y1);
                        targetDistance = distance;
                        if(nextPosX>posX) {stepDirection=90; this.body.image=this.body.imageBody[1];this.flagImage=flags[1];}
                        if(nextPosX<posX) {stepDirection=270; this.body.image=this.body.imageBody[3];this.flagImage=flags[3];}
                        if(nextPosY>posY) {stepDirection=180; this.body.image=this.body.imageBody[2];this.flagImage=flags[2];}
                        if(nextPosY<posY) {stepDirection=0; this.body.image=this.body.imageBody[0];this.flagImage=flags[0];}
                        
                        //Нужно повернуться?
//                        System.out.println(this.name+" смотрит на "+botDirection+", шаг на "+stepDirection);
                        if(botDirection!=stepDirection)
                        {
                            botMode=6;
                        }
                        else
                        {
                            botMode=3;
                        }
                    }
                }
            }
        }
    }

    void aim(){
        //Если зарядился, то можно стрелять
//        System.out.println(this.name+" целится в "+this.target.name);
        if(this.reloading==0)
        {
            this.botMode = 4;
        }
        else
        {
            this.botMode = 1;
        }
    }

    
    void move(){
//        path = pathFinder.findPath(this, Math.round(posX/CELL_SIZE), Math.round(posY/CELL_SIZE), Math.round(target.posX/CELL_SIZE), Math.round(target.posY/CELL_SIZE));
        posX+=stepX;
        posY+=stepY;
        if(posX == nextPosX && posY == nextPosY)
        {
            botMode=1;
        }
    }
    float lerp(float oldPos, float newPos, float t) {
          if (t < 0)
          {
             return oldPos;
          }
          return oldPos +t* (newPos - oldPos);
       }

    void shoot(){
        //Это будут пули. пока не используется
        //Projectile projectile;
        //projectile = new Projectile(this.posX, this.posY, this.target.posX, this.target.posY, this.weapon.speed, this.weapon.damage);
//        System.out.println(this.name+" стреляет в "+this.target.name);
        this.target.doDamage(this.weapon.damage);
        this.reloading=Math.round((float)30/(float)this.weapon.speed);
        if(this.target.currentHealth<=0)
        {
//            System.out.println(this.name+" убивает "+this.target.name);
            this.target.botMode=0;
        }
        this.targetDistance=99999;
        this.botMode=1;

    }
    
    void die(){
//        System.out.println(this.name+" УМЕР");
    }
    
    void rotate(){
        float angleDelta;
        angleDelta=stepDirection-botDirection;
//        System.out.println(this.name+" смотрит на "+botDirection+", поворачивается на "+angleDelta+" до "+stepDirection);
        botDirection+=Math.round(angleDelta);
        botMode=1;
    }
    
    
    
    void doReload()
    {
        if(this.reloading>0)
        {
            this.reloading--;
//            System.out.println(this.name+" осталось заряжаться "+this.reloading);
        }
    }
    
    private void setEquipment(int bodyId, int weaponId, int powerId) throws SlickException
    {
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
    }
}
