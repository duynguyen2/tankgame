package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends Wall {
    private static BufferedImage backgroundIMG;

    public Background(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setBackgroundImage(BufferedImage image) {
        backgroundIMG = image;
    }

    @Override
    public void update() { }

    @Override
    public void collision() { }

    @Override
    public void drawImage(Graphics2D g) { g.drawImage(backgroundIMG, x, y, null); }
}
