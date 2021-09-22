package PlaneGame;
import java.awt.*;

public abstract class Bullet extends FlyObject{
    protected int speed;
    public Bullet(Point Position, int Time) {
        super(Position, Time);
        this.life=1;
        this.type="Bullet";
    }

    @Override
    public void Behavior() {
        setPosition(null);
    }

    @Override
    public void setPosition(Point position) { this.Position.y+=speed; }

    @Override
    public void OutofBound(int type) { gone();}

    @Override
    public void on_hit(FlyObject e) {
        if(e.type.equals("Plane")){
            e.life--;
            this.life--;
        }
    }
}
