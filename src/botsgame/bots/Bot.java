package botsgame.bots;

import botsgame.Landscape;
import botsgame.equipment.Comp;
import botsgame.equipment.Power;
import botsgame.equipment.Weapon;
import botsgame.equipment.Body;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
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

    public Body body = new Body();
    public Weapon weapon = new Weapon();
    public Power power = new Power();
    public Comp comp = new Comp();

    public Bot target;
    public int targetDistance;
    
    private final Random random;
    
    protected int respawnX;
    protected int respawnY;

    
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

    public Bot(String name, Color color) throws SlickException
    {
        super();
        random=new Random();
        this.pathFinder = new AStarPathFinder((TileBasedMap) terrain, 60, false);
        int x = 0;//random.nextInt(3);
        int y = 0;//random.nextInt(3);
        this.name = name;
        this.flagColor=color;
        this.botMode = 1;
        this.target = null;
        this.targetDistance = 99999;
        while (terrain.blocked(pathFinder, x, y)==true)
        {
            x = random.nextInt(20)+respawnX;
            y = random.nextInt(20)+respawnY; 
        }
        this.posX = x;
        this.posY = y;
        this.oldPosX = x;
        this.oldPosY = y;
        setEquipment(random.nextInt(3),random.nextInt(3),random.nextInt(2));
        super.health = this.body.durability+this.power.durability;
        System.out.println("Появился "+this.name+" в "+this.posX+"-"+this.posY);
    }

    void see(Army enemies){
        
        for(Bot enemyBot: enemies.bots)
        {
            int X1 = enemyBot.posX;
            int Y1 = enemyBot.posY;
            int X = this.posX;
            int Y = this.posY;
//            System.out.println(this.name+" видит "+enemyBot.name);
            //Вычисляем дистанцию до бота, если она меньше предыдущей, выбираем его в качестве цели.
            this.path = pathFinder.findPath(this, X, Y, X1, Y1);
            if (path!=null){
                int distance = path.getLength();
                if(distance <= this.targetDistance)
                {
                    this.target=enemyBot;
                    this.targetDistance = distance;
                    //посмотрим, может можно стрельнуть
                    this.botMode=2;
//                    System.out.println(this.name+" выбрал целью "+enemyBot.name+" в "+X1+"-"+Y1);
                }
            }
        }
    }

    void aim(){
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
    
    void move(){
        
        path = pathFinder.findPath(this, posX, posY, target.posX, target.posY);

//        System.out.println(this.name+" ходит в " + path.getX(1) + ", " + path.getY(1));
        oldPosX=posX;
        oldPosY=posY;
        posX=path.getX(1);
        posY=path.getY(1);
        targetDistance=99999;
        
//        terrain.setObstacle(posX, posY);
        //посмотрим, может есть кто-нть поближе
        botMode=1;
    }

    void shoot(){
        //Это будут пули. пока не используется
        //Projectile projectile;
        //projectile = new Projectile(this.posX, this.posY, this.target.posX, this.target.posY, this.weapon.speed, this.weapon.damage);
//        System.out.println(this.name+" стреляет в "+this.target.name);
        this.target.doDamage(this.weapon.damage);
        if(this.target.health<=0)
        {
            this.target.botMode=0;
        }
        this.targetDistance=99999;
        this.botMode=1;

    }
    
    void die(){
//        System.out.println(this.name+" --------------------------- УМЕР");
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
