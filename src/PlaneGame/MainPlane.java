package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//游戏中的我方飞行物的类，直接继承于flyobject
public class MainPlane extends FlyObject{

    protected static BufferedImage reSource;//资源文件
    protected int firetime=0;//双倍开火的时间
    protected int Status=0;//开火的状态，0为单发，1为双发

    static{
        try {
            //绑定图片资源
            reSource=ImageIO.read(Main.class.getResource("myplane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //构造函数，利用图片资源对宽高等进行初始化
    public MainPlane(Point Position, int Time) {
        super(Position, Time);
        this.Main_flyObjects=Main.Main_FlyObjects;//Mainplane与整个游戏中的我方飞行物同阵营
        this.Enemy_flyObjects=Main.Enemy_FlyObjects;//与整个游戏中的敌机为不同阵营
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
        this.type="Plane";
        this.life=10;
    }

    //重写返回resource的函数
    @Override
    public BufferedImage getReSource() {
        return reSource;
    }

    //我方飞机每单位时间内要做的事是开火
    @Override
    public void Behavior() {
        clock++;
        if(clock%30==0){//每30个单位时间，也就是30*10ms开一次火
            Launch(Status);//开火函数
            //对双倍开火时间的判断
            if(Status==1) firetime--;
            if(firetime==0) Status=0;
        }
    }

    //设定位置的函数
    @Override
    public void setPosition(Point position) {
        this.Position=position;
    }

    //我方飞机不会出界，所以留空
    @Override
    public void OutofBound(int type) {}

    //与敌方撞击，则双方飞行物的life--；
    @Override
    public void on_hit(FlyObject e) {
        this.life--;
        e.life--;
    }

    //开火函数，新建子弹对象，并添加进同阵营飞行物list里
    protected void Launch(int Status){
        if(Status==0) {
            MainBullet bullet = new MainBullet(new Point(this.Position.x - 2, this.Position.y - this.length / 2), Time);
            this.Main_flyObjects.add(bullet);
        }else{
            MainBullet bullet1=new MainBullet(new Point(this.Position.x-20,this.Position.y),Time);
            MainBullet bullet2=new MainBullet(new Point(this.Position.x+20,this.Position.y),Time);
//            MainBullet bullet3=new MainBullet(new Point(this.Position.x-30,this.Position.y),Time);
//            MainBullet bullet4=new MainBullet(new Point(this.Position.x+30,this.Position.y),Time);
//            MainBullet bullet5=new MainBullet(new Point(this.Position.x-40,this.Position.y),Time);
//            MainBullet bullet6=new MainBullet(new Point(this.Position.x+40,this.Position.y),Time);
            this.Main_flyObjects.add(bullet1);
            this.Main_flyObjects.add(bullet2);
//            this.Main_flyObjects.add(bullet3);
//            this.Main_flyObjects.add(bullet4);
//            this.Main_flyObjects.add(bullet5);
//            this.Main_flyObjects.add(bullet6);
        }
    }
}
