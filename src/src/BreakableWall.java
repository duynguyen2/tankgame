package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    private int durability = 100;
    private boolean broken = false;
    private static BufferedImage breakableWallIMG;

    public BreakableWall(int x, int y) {
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, breakableWallIMG.getWidth(), breakableWallIMG.getHeight());
    }

    public int getDurability() {
        return this.durability;
    }

    private void removeHealth(int damage) {
        if (this.durability - damage <= 0) {
            this.durability = 0;
            this.broken = true;
        }
        else
            this.durability -= damage;
    }

    public boolean isBroken() {
        return this.broken;
    }

    public static void setBreakableWall(BufferedImage image) {
        BreakableWall.breakableWallIMG = image;
    }

    @Override
    public void update() { }

    @Override
    public void collision() { this.removeHealth(34); }

    @Override
    public void drawImage(Graphics2D img) {
        if (!broken)
            img.drawImage(breakableWallIMG, x, y, null);
    }
}