package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

//敌方的小型飞机
public class Enemy_S extends EnemyPlane{

    protected static BufferedImage reSource;//图片资源
    Random random=new Random();//随机对象
    private int speed=5;//飞机的速度

    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("enemy.png"));//绑定图片资源
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //构造函数
    public Enemy_S(Point Position, int Time) {
        super(Position, Time);
        this.life=1;//生命值为1
        this.Score=15;//击落的分数为15
        //设置宽高
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
    }

    @Override
    public BufferedImage getReSource() {
        return reSource;
    }

    @Override
    public void Behavior() {
        //每单位时间里做的事是更新位置
        setPosition(null);
//        clock++;
//        if(Time>4000) {
//            if (clock % 30 == 0) {
//                if (random.nextInt(100) > 97) {
//                    speed *=2;
//                }
//            }
//        }
    }

    //更新位置函数
    @Override
    public void setPosition(Point position) {
        this.Position.y+=speed;
    }

    //出界则删除
    @Override
    public void OutofBound(int type) {
        gone();
    }
}
