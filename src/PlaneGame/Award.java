package PlaneGame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Award extends FlyObject{
    protected static BufferedImage reSource;
    private int x_speed=3;
    private static final int y_speed=4;
    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("award.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Award(Point Position, int Time) {
        super(Position, Time);
        this.Enemy_flyObjects=Main.Main_FlyObjects;
        this.Main_flyObjects=Main.Award_FlyObjects;
        this.type="Award";
        this.life=1;
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
    }
    @Override
    public BufferedImage getReSource() {
        return reSource;
    }
    @Override
    public void Behavior() {
        setPosition(null);
    }
    @Override
    public void setPosition(Point position) {
        this.Position.x+=x_speed;
        this.Position.y+=y_speed;
    }
    @Override
    public void OutofBound(int type) {
        if(type==0) x_speed=-x_speed;
        else gone();
    }
    @Override
    public void on_hit(FlyObject e) {
        if(!e.type.equals("Bullet")) {
            this.life--;
            if (e.life < 10) e.life++;
            else {
                ((MainPlane) e).firetime = 40;
                ((MainPlane) e).Status = 1;
            }
        }
    }
}
