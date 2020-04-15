import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject {
    static private BufferedImage healthIMG;

    public PowerUp(int x, int y){
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, 40, 40);
    }

    public static void setHealthIMG(BufferedImage healthIMG){
        PowerUp.healthIMG = healthIMG;
    }

    @Override
    public void update() { }

    @Override
    public void collision(){ }

    @Override
    public void drawImage(Graphics2D g) { g.drawImage(healthIMG, x, y, 40,40 , null); }
}
