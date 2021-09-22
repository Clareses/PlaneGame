package PlaneGame;

import java.awt.*;
import java.util.Random;

//敌机飞行物
public abstract class EnemyPlane extends FlyObject{

    protected int Score;//飞机带有的分值
    static Random random=new Random();//随机对象

    //构造函数
    public EnemyPlane(Point Position, int Time) {
        super(Position, Time);
        this.Enemy_flyObjects=Main.Main_FlyObjects;//敌方飞机的敌人为我方的飞行物
        this.Main_flyObjects=Main.Enemy_FlyObjects;//敌方飞机的友军为敌方的飞行物
        this.type="Plane";//类型为plane
    }

    //重载状态更新函数，加入一个接口作为参数，用于接口回调
    public void State_Update(GetScore getScore){
        //如果生命值小于0，则删除该飞行物，并调用接口里的Score_set函数
        if(this.life<=0){
            gone();
            getScore.Score_set();
        }
        //调用behavior函数
        Behavior();
        //对出界进行处理
        if (this.Position.x < 0 || this.Position.x >Main.WIDTH )
            OutofBound(0);
        if (this.Position.y < 0 || this.Position.y >Main.HEIGHT )
            OutofBound(1);
    }

    @Override
    public abstract void Behavior();

    @Override
    public abstract void setPosition(Point position);

    @Override
    public abstract void OutofBound(int type);

    //相撞则双方life--
    @Override
    public void on_hit(FlyObject e) {
        this.life--;
        e.life--;
    }

}
