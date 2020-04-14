package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject {
    static private BufferedImage healthIMG;

    public PowerUp(int x, int y){
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, 40, 40);
    }
    static void setHealthIMG(BufferedImage healthIMG){
        PowerUp.healthIMG = healthIMG;
    }

    @Override
    public void update() { }

    @Override
    public void collision(){ }

    @Override
    public void drawImage(Graphics2D img) { img.drawImage(healthIMG, x, y, 40,40 , null); }
}
