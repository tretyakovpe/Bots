package botsgame.bots;

import static botsgame.Constants.*;
import botsgame.Landscape;
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
    protected int botMode;

    public Bot(String name, Color color, String flagColor) throws SlickException
    {
        super();
        random=new Random();
        this.pathFinder = new AStarPathFinder((TileBasedMap) terrain, 60, false);
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
        this.nextPosX = x;
        this.nextPosY = y;
        this.target = null;
        this.targetDistance = 99999;
        this.botDirection=0;
        this.stepDirection=0;
        this.targetDirection=0;
        setEquipment();
        super.maxHealth = this.body.durability+this.power.durability;
        super.currentHealth=super.maxHealth;
//        System.out.println("Появился "+this.name+" в "+this.posX+"-"+this.posY);
    }

    protected void look()
    {
        int radius = (int)Math.round(comp.viewDistance/32);
        int x = Math.round(posX/CELL_SIZE);
        int y = Math.round(posY/CELL_SIZE);
        int tileId;
        for(int i=1; i<radius; i++)
        {

        }
        
    }
    
    protected void see(Army enemies){
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
                            botMode=6; //поворачиваемся
                        }
                        else
                        {
                            terrain.setTileId((int)posX/CELL_SIZE, (int)posY/CELL_SIZE, 2, 0);
                            botMode=3; //движемся
                        }
                    }
                }
            }
        }
    }

    protected void aim(){
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
    
    protected void move(){
        posX+=stepX;
        posY+=stepY;
        if(posX == nextPosX && posY == nextPosY)
        {
            terrain.setTileId((int)posX/CELL_SIZE, (int)posY/CELL_SIZE, 2, 30);
            botMode=1;
        }
    }

    protected void shoot(){
        //Это будут пули. пока не используется
        //Projectile projectile;
        //projectile = new Projectile(this.posX, this.posY, this.target.posX, this.target.posY, this.weapon.speed, this.weapon.damage);
//        System.out.println(this.name+" стреляет в "+this.target.name);
        this.target.doDamage(this.weapon.damage);
        this.reloading=Math.round((float)100/(float)this.weapon.speed);
        if(this.target.currentHealth<=0)
        {
//            System.out.println(this.name+" убивает "+this.target.name);
            this.target.botMode=0;
        }
        this.targetDistance=99999;
        this.botMode=1;

    }
    
    protected void die(){
//        System.out.println(this.name+" УМЕР");
    }
    
    protected void rotate(){
        float angleDelta;
        angleDelta=stepDirection-botDirection;
//        System.out.println(this.name+" смотрит на "+botDirection+", поворачивается на "+angleDelta+" до "+stepDirection);
        botDirection+=Math.round(angleDelta);
        botMode=1;
    }
    
    protected void doReload()
    {
        if(this.reloading>0)
        {
            this.reloading-=this.power.power;
            if(this.reloading<0)
            {
                this.reloading=0;
            }
//            System.out.println(this.name+" осталось заряжаться "+this.reloading);
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
        body.image.draw(x, y);
        flagImage.draw(x, y);
        weapon.image.draw(x, y);
        switch (botMode)
        {
            case 0:
                g.drawAnimation(explosionAnimation,  x-16,  y-16);
                break;
            case 1:
                break;
            case 2: 
                break;
            case 3:
                break;
            case 4: 
                g.setColor(flagColor);
                g.drawLine(x+16, y+16, target.posX+16,  target.posY+16);
                hitAnimation.draw(target.posX,  target.posY);
                break;
            case 5: 
                break;
            case 6: 
                break;
        }
        g.setColor(Color.black);
        g.fillRect(x, y+33, 32, 5);
        g.setColor(Color.red);
        g.fillRect(x+1, y+34, 30, 3);
        g.setColor(Color.green);
        g.fillRect(x+1, y+34, (int) 30f*this.currentHealth/this.maxHealth, 3);
//        g.drawString(String.valueOf(this.currentHealth), x+6, y+33);
    }
    
}
