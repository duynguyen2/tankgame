package src;

import java.awt.*;
import java.util.ArrayList;

public class Collision {

    public Collision() { }

    public ArrayList<GameObject> collide(ArrayList<GameObject> gameObjects) {
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i; j < gameObjects.size(); j++) {
                GameObject obj1 = gameObjects.get(i), obj2 = gameObjects.get(j);

                if (i != j) {
                    if (obj1 instanceof Bullet && obj2 instanceof Tank && !(((Bullet) obj1).getPlayerWhoFired().equals(((Tank) obj2).getPlayer())) && !((Bullet) obj1).collided) {
                        if (obj1.rectangle.intersects(obj1.rectangle)) {
                            obj1.collision();
                            ((Bullet) obj1).setExploded(false);
                            obj2.collision();
                        }
                    }

                    else if (obj1 instanceof Tank && obj2 instanceof Bullet && !((Bullet) obj2).getPlayerWhoFired().equals(((Tank) obj1).getPlayer()) && !((Bullet) obj2).collided) {
                        if (obj1.rectangle.intersects(obj2.rectangle)) {
                            ((Bullet) obj2).setExploded(false);
                            obj2.collision();
                            obj1.collision();
                        }
                    }

                    else if (((obj2 instanceof Bullet && obj1 instanceof BreakableWall && !((Bullet) obj2).collided))) {
                        if (obj1.rectangle.intersects(obj2.rectangle)) {
                            obj2.collision();
                            obj1.collision();
                        }
                    }

                    else if (((obj2 instanceof Bullet && obj1 instanceof UnbreakableWall && !((Bullet) obj2).collided))) {
                        if (obj1.rectangle.intersects(obj2.rectangle)) {
                            obj2.collision();
                            obj1.collision();
                        }
                    }

                    else if (obj1 instanceof Tank && obj2 instanceof Tank) {
                        Rectangle rectangle1 = ((Tank) obj1).checkOffsetBounds();
                        Rectangle rectangle2 = (((Tank) obj2).checkOffsetBounds());
                        if (rectangle1.intersects(obj2.rectangle))
                            ((Tank) obj1).setCollision(true);
                        if (rectangle2.intersects(obj1.rectangle))
                            ((Tank) obj2).setCollision(true);
                    }

                    else if (obj1 instanceof Tank && obj2 instanceof UnbreakableWall) {
                        Rectangle rectangle1 = ((Tank) obj1).checkOffsetBounds();
                        if (rectangle1.intersects(obj2.rectangle))
                            ((Tank) obj1).setCollision(true);

                    }

                    else if (obj1 instanceof UnbreakableWall && obj2 instanceof Tank) {
                        Rectangle rectangle1 = ((Tank) obj2).checkOffsetBounds();
                        if (rectangle1.intersects(obj1.rectangle))
                            ((Tank) obj2).setCollision(true);

                    }

                    else if (obj1 instanceof Tank && obj2 instanceof BreakableWall) {
                        Rectangle rectangle1 = ((Tank) obj1).checkOffsetBounds();
                        if (rectangle1.intersects(obj2.rectangle))
                            ((Tank) obj1).setCollision(true);

                    }

                    else if (obj1 instanceof BreakableWall && obj2 instanceof Tank) {
                        Rectangle rectangle1 = ((Tank) obj2).checkOffsetBounds();
                        if (rectangle1.intersects(obj1.rectangle))
                            ((Tank) obj2).setCollision(true);

                    }

                    else if (obj1 instanceof Tank && obj2 instanceof PowerUp) {
                        if (obj1.rectangle.intersects(obj2.rectangle)) {
                            ((Tank) obj1).setHealth(100);
                            gameObjects.remove(j);
                        }
                    }

                }
            }
        }

        return gameObjects;
    }
}
