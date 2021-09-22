package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyBullet extends Bullet{

    private static BufferedImage reSource;

    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("enemy_bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EnemyBullet(Point Position, int Time) {
        super(Position, Time);
        this.speed=4;
        this.Enemy_flyObjects=Main.Main_FlyObjects;
        this.Main_flyObjects=Main.Enemy_FlyObjects;
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
    }

    @Override
    public BufferedImage getReSource() {
        return reSource;
    }
}
