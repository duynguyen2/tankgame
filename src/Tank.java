import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author anthony-pc
 */
public class Tank extends GameObject{

    private int vx;
    private int vy;
    private int angle;

    private final int R = 2;
    private final int ROTATION_SPEED = 2;

    private BufferedImage tankImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    private int health = 100;
    private long lastFireTime;
    private GameWorld gameWorld;
    private boolean wallCheck = false;
    private String player;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImage) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.tankImage = tankImage;
        this.angle = angle;
        this.height = 50;
        this.width = 50;
        this.rectangle = new Rectangle(x, y, tankImage.getWidth(), tankImage.getHeight());
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void toggleShootPressed() { this.shootPressed = true; }

    void unToggleShootPressed() { this.shootPressed = false; }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle))) * -1;
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle))) * -1;
        if (!wallCheck) {
            x += vx;
            y += vy;
        }
        this.checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if (!wallCheck) {
            x += vx;
            y += vy;
        }
        this.checkBorder();
    }

    private void checkBorder() {
        if (x < 30)
            x = 30;

        if (x >= GameWorld.WORLD_WIDTH - 80)
            x = GameWorld.WORLD_WIDTH - 80;

        if (y < 40)
            y = 40;

        if (y >= GameWorld.WORLD_HEIGHT - 60)
            y = GameWorld.WORLD_HEIGHT - 60;

    }

    public void setGameWorld(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void setPlayer(String player) { this.player = player; }

    public String getPlayer() { return this.player; }

    public void setHealth(int health) { this.health = health; }

    public int getHealth(){
        return this.health;
    }

    public Rectangle checkOffsetBounds() {
        return new Rectangle(x + vx, y + vy, 50, 50);
    }

    public void setCollision(boolean wallCheck){ this.wallCheck = wallCheck; }

    @Override
    public void update() {
        this.rectangle.setLocation(x, y);

        if (this.UpPressed)
            this.moveForwards();

        if (this.DownPressed)
            this.moveBackwards();

        if (this.LeftPressed)
            this.rotateLeft();

        if (this.RightPressed)
            this.rotateRight();

        if (this.shootPressed && System.currentTimeMillis() - lastFireTime > 750) {
            Bullet bullet = new Bullet(x, y, angle, player);
            gameWorld.addGameObject(bullet);
            lastFireTime = System.currentTimeMillis();
        }

        this.wallCheck = false;
    }

    @Override
    public void collision() {
        this.health -= 20;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);
        if (this.health != 0)
            g.drawImage(this.tankImage, rotation, null);

    }

}
