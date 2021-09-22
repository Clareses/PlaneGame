package PlaneGame;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.abs;

//飞行物类，所有的飞行物都直接或间接继承于它
public abstract class FlyObject {
    protected Point Position;//位置
    protected int length,width;//飞行物的长和宽
    protected int life;//飞行物生命值
    protected String type;//飞行物类型
    protected int Time;//游戏运行总时间
    protected int clock;//计数器
    protected ArrayList<FlyObject> Main_flyObjects;//同一阵营飞行物的list
    protected ArrayList<FlyObject> Enemy_flyObjects;//敌对阵营的飞行物的list

    //构造函数
    public FlyObject(Point Position, int Time){
        this.Position=Position;
        this.clock=0;
        this.Time=Time;
    }

    //状态更新函数，对生命值低于0的飞行物进行移除，对没死的飞行物进行判断是否相撞、是否出界并调用该飞行物的behavior函数
    public void State_Update(){
        if (this.life<=0) gone();
        else{
            FlyObject e = is_hit();
            if (e != null) on_hit(e);
            Behavior();
            if (this.Position.x < 0 || this.Position.x >Main.WIDTH )
                OutofBound(0);
            if (this.Position.y < 0 || this.Position.y >Main.HEIGHT )
                OutofBound(1);
            if (this.life <= 0) gone();
        }
    }

    //返回图片资源
    public abstract BufferedImage getReSource();

    //behavior函数，用于写入每个周期内飞机要做的事
    public abstract void Behavior();

    //更新位置的函数
    public abstract void setPosition(@Nullable Point position);

    //出界函数，type为0的话为x方向出界，type为1的话则y方向出界
    public abstract void OutofBound(int type);

    //撞击函数，对相撞的flyobject e进行相关的操作，比如扣血之类的
    public abstract void on_hit(FlyObject e);

    //判断是否相撞的函数，如果相撞则返回撞击的飞行物，否则返回null
    public FlyObject is_hit(){
        for(int i=0;i<Enemy_flyObjects.size();i++){
            FlyObject e=Enemy_flyObjects.get(i);
            if(abs(this.Position.x-e.Position.x)<(this.width+e.width)/2&&abs(this.Position.y-e.Position.y)<(this.width+e.width)/2){
                return e;
            }
        }
        return null;
    };

    //gone函数，调用此函数会将飞行物对象从同阵营list中移除
    protected void gone(){
        int temp=Main_flyObjects.indexOf(this);
        if(temp!=-1) Main_flyObjects.remove(temp);
    }

}
