package PlaneGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class MainBullet extends Bullet{

    private static BufferedImage reSource;

    static {
        try {
            reSource= ImageIO.read(Main.class.getResource("main_bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainBullet(Point Position, int Time) {
        super(Position, Time);
        this.speed=-4;
        this.Enemy_flyObjects=Main.Enemy_FlyObjects;
        this.Main_flyObjects=Main.Main_FlyObjects;
        this.length=reSource.getHeight();
        this.width=reSource.getWidth();
    }

    @Override
    public BufferedImage getReSource() {
        return reSource;
    }

}
