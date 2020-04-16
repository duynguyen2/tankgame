import java.awt.*;

public abstract class GameObject {

    int x, y, vx, vy, angle, height, width;
    Rectangle rectangle;

    public abstract void update();
    public abstract void collision();
    public abstract void drawImage(Graphics2D g);

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public int getVx() {
        return this.vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return this.vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

}