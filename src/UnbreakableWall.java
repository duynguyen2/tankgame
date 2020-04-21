import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall{
    private static BufferedImage unbreakableWallIMG;

    public UnbreakableWall(int x, int y) {
        super(x, y);
        this.rectangle = new Rectangle(x, y, unbreakableWallIMG.getWidth(), unbreakableWallIMG.getHeight());
    }

    public static void setUnbreakableWall(BufferedImage image) { UnbreakableWall.unbreakableWallIMG = image; }

    @Override
    public void update() { }

    @Override
    public void collision() { }

    @Override
    public void drawImage(Graphics2D g) {
        g.drawImage(unbreakableWallIMG, x, y, null);
    }
}
