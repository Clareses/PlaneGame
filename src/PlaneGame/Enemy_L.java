package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Enemy_L extends EnemyPlane{

    private static BufferedImage reSource;
    Random random=new Random();
    private int x_speed=2;
    private int y_speed=1;
    private int Status=1;
    protected static int num=0;
    int temp=-1;
    double border;

    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("enemy2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Enemy_L(Point Position, int Time) {
        super(Position, Time);
        this.life=20;
        this.Score=500;
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
        this.border=((double)random.nextInt(10)+10)/100;
        num++;
    }

    @Override
    public BufferedImage getReSource() {
        return reSource;
    }

    @Override
    public void Behavior() {
        clock++;
        setPosition(null);
        if(clock%160==0) Status=random.nextInt(2);
        if(Status==1&&clock%20==0) Launch();
    }

    @Override
    public void setPosition(Point position) {
        if(this.Position.y<(border*Main.HEIGHT)) this.Position.y+=y_speed;
        else{
            if(clock%120==0) temp=random.nextInt(5);
            if(temp>2) this.Position.x+=x_speed;
            if(temp<2) this.Position.x-=x_speed;
        }
    }

    @Override
    public void OutofBound(int type) {
        if(type==0) x_speed=-x_speed;
    }

    public void Launch(){
        this.Main_flyObjects.add(new EnemyBullet(new Point(this.Position.x,this.Position.y),this.Time));
    }

    @Override
    protected void gone() {
        super.gone();
        num--;
    }
}
