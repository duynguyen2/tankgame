package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private String playerWhoFired;
    private boolean active = false;
    private boolean exploded = true;
    boolean collided = false;
    static private BufferedImage bulletIMG;
    static private BufferedImage explosionIMG;
    private int collisionIterations = 0;

    public Bullet(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(3 * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(3 * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.rectangle = new Rectangle(x, y, bulletIMG.getWidth(), bulletIMG.getHeight());
    }

    public static void setExplosionImage(BufferedImage explosionImage) { // used to set the explosion image
        explosionIMG = explosionImage;
    }

    public void setExploded(boolean exploded){
        this.exploded = exploded;
    }

    public String getPlayerWhoFired() {
        return this.playerWhoFired;
    }

    public void setPlayerWhoFired(String playerWhoFired) {
        this.playerWhoFired = playerWhoFired;
    }

    public boolean activeCheck() { return this.active; }

    public static void setBufferedImage(BufferedImage img) {
        bulletIMG = img;
    }

    private void checkBorder() {
        int leftBorder = 30;
        if (x < leftBorder)
            this.active = true;

        int rightBorder = GameWorld.WORLD_WIDTH - 65;
        if (x >= rightBorder)
            this.active = true;

        int bottomBorder = 40;
        if (y < bottomBorder)
            this.active = true;

        int topBorder = GameWorld.WORLD_HEIGHT - 60;
        if (y >= topBorder)
            this.active = true;

    }

    @Override
    public void update() {
        if (!collided) {
            this.x = x + vx;
            this.y = y + vy;
            this.checkBorder();
        }
        else
            collisionIterations++;

        this.rectangle.setLocation(x,y);
    }

    @Override
    public void collision() { collided = true; }

    @Override
    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), bulletIMG.getWidth() / 2.0, bulletIMG.getHeight() / 2.0);
        if (collided && exploded) {
            g.drawImage(explosionIMG, rotation, null);
            if (collisionIterations >= 5)
                this.active = true;

        }
        else if(collided){
            g.drawImage(explosionIMG, rotation, null);
            if (collisionIterations >= 5)
                this.active = true;

        }
        else
            g.drawImage(bulletIMG, rotation, null);

    }

}
