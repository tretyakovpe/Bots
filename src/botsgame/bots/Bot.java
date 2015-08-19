package botsgame.bots;

import botsgame.equipment.Comp;
import botsgame.equipment.Power;
import botsgame.equipment.Weapon;
import botsgame.equipment.Body;
import botsgame.equipment.Equipment;
import static botsgame.Constants.*;
import botsgame.Landscape;
import java.awt.Color;
import java.util.Random;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


/**
 *
 * @author pavel.tretyakov
 */
public abstract class Bot extends Obstacles{

    public static Landscape terrain;

    public String name;
    public int team;
    public Color flagColor;

    public Body body = new Body();
    public Weapon weapon = new Weapon();
    public Power power = new Power();
    public Comp comp = new Comp();

    private Tower tower;
    private Obstacles obstacle;
    
    public Obstacles target;
    public int targetDistance;
    
    private Random random;
    
    protected int respawnX = 1;
    protected int respawnY = 1;

    
    /**
     *режим работы бота
     * 
     * 0-мертв
     * 1-поиск цели
     * 2-прицеливание (оценка дистанции)
     * 3-движение к target
     * 4-стрельба
     * 5-убегание
     */
    public int botMode;

    public Bot()
    {
        random=new Random();
    }
    
    public abstract void init(String name, int team, int X, int Y);

    public void init(String name, int team, int X, int Y, Color color)
    {
        this.name = name;
        this.team = team;
        this.posX = X;
        this.posY = Y;
        this.flagColor = color;
        this.botMode = 1;
        this.target = null;
        this.targetDistance = 99999;
    }


    public void doAction(Bot[] army, int botIndex) throws SlickException
    {
        selfTest();
        switch (botMode)
        {
            case 0: 
                spawn(botIndex);
                break;
            case 1: 
                see(army);
                break;
            case 2: 
                aim();
                break;
            case 3: 
                move();
                break;
            case 4: 
                shoot();
                break;
            case 5: 
                break;
        }
    }
    
    public void spawn(int index) throws SlickException
    {
        int x = random.nextInt(WORLD_SIZE);
        int y = random.nextInt(WORLD_SIZE);
        
        init(String.valueOf(index), 0, x, y);
        setEquipment(0);
//        this.health=body.durability+power.durability;
    }

    public void selfTest(){
        if(health<=0)
        {
            die();
            botMode=0;
        }
        else if (health <=3)
        {
            botMode=5;
        }
    }

    public void see(Bot[] enemies){
        
        for (Bot enemyBot:enemies)
        {
            int X1 = enemyBot.posX;
            int Y1 = enemyBot.posY;
            int X = this.posX;
            int Y = this.posY;
//            System.out.println(this.name+" видит "+enemyBot.name);
            //Вычисляем дистанцию до бота, если она меньше предыдущей, выбираем его в качестве цели.
            double distance=Math.sqrt(Math.pow((X1-X),2)+Math.pow((Y1-Y),2));
            if((int)distance <= this.targetDistance)
            {
                this.target=enemyBot;
                this.targetDistance = (int) distance;
                //посмотрим, может можно стрельнуть
                this.botMode=2;
//                System.out.println(this.name+" выбрал целью "+enemyBot.name+" в "+X1+"-"+Y1);
            }
        }
    }

    public void aim(){
        if (this.weapon.range>this.targetDistance)
        {
//        System.out.println(this.name+" прицелился в "+this.target.name);
            //Можно стрелять
            this.botMode = 4;
        }
        else
        {
//        System.out.println(this.name+" видит, что до цели "+this.targetDistance);
            //далеко, надо шагнуть ближе
            this.botMode = 3;
        }
    }
    
    public void move(){
        
        AStarPathFinder pathFinder = new AStarPathFinder((TileBasedMap) terrain, 10, false);
        Path path = pathFinder.findPath(null, posX, posY, target.posX, target.posY);

        int length = path.getLength();
        System.out.println("Found path of length: " + length + ".");

        for(int i = 0; i < length; i++) {
            System.out.println("Move to: " + path.getX(i) + "," + path.getY(i) + ".");
        }
        
        
        
        int surface = terrain.getSurface(this.posX, this.posY);
        float step = (float)this.targetDistance/(float)this.body.speed;
        float vectorX=(this.target.posX-this.posX);
        float vectorY=(this.target.posY-this.posY);
        //
        float newX = this.posX+(vectorX/step)/surface;
        float newY = this.posY+(vectorY/step)/surface;
        
        newX = newX<0?0:newX;
        newY = newY<0?0:newY;
        newX = newX>WORLD_SIZE?WORLD_SIZE:newX;
        newY = newY>WORLD_SIZE?WORLD_SIZE:newY;
        
        this.posX=Math.round(newX);
        this.posY=Math.round(newY);
        
        this.targetDistance=99999;
        //посмотрим, может есть кто-нть поближе
        this.botMode=1;
    }

    public void shoot(){
        
        //Это будут пули. пока не используется
        //Projectile projectile;
        //projectile = new Projectile(this.posX, this.posY, this.target.posX, this.target.posY, this.weapon.speed, this.weapon.damage);
        
        //System.out.println(this.name+" стреляет.");
        this.target.doDamage(this.weapon.damage);
        this.targetDistance=99999;
        this.botMode=1;

    }
    
    public void die(){
//        System.out.println(this.name+" УМЕР");
        BotRemains remains;
        switch(random.nextInt(2)){
            case 0:
                remains = new BotRemains(this.body);
                remains.health = this.body.durability;
                remains.part.name = this.body.name;
                remains.part.image = this.body.image;
                break;
            case 1:
                remains = new BotRemains(this.power);
                remains.health = this.power.durability;
                remains.part.name = this.power.name;
                remains.part.image = this.power.image;
                break;
            default:
                remains=null;
        }
    }
    
    public void setEquipment(int bodyId) throws SlickException
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
/*
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
            case 2:
                power.tousandChinese();
                break;
        }*/
    }

    /**
     *Описывает цвета каждого из режимов работы бота
     * @return
     */
    public Color getStatusColor()
    {
        Color C = Color.WHITE;
        
        switch(this.botMode){
            case 0: C=Color.black;break;
            case 1: C=Color.white;break;
            case 2: C=Color.yellow;break;
            case 3: C=Color.green;break;
            case 4: C=Color.red;break;
            case 5: C=Color.magenta;break;
        }
        return C;
    }
 
}
