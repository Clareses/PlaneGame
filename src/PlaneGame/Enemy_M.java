package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Enemy_M extends EnemyPlane{

    private static BufferedImage reSource;
    private int speed=2;

    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("enemy1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Enemy_M(Point Position, int Time) {
        super(Position, Time);
        this.life=3;
        this.Score=60;
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
        clock++;
        if(random.nextInt(100)+40>40&&clock%75==0) Launch();
    }

    @Override
    public void setPosition(Point position) {
        this.Position.y+=this.speed;
    }

    @Override
    public void OutofBound(int type) {
        gone();
    }

    public void Launch(){
        this.Main_flyObjects.add(new EnemyBullet(new Point(this.Position.x - 2, this.Position.y + this.length / 2), Time));
    }
}
