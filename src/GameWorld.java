import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld extends JPanel {

    static final int WORLD_HEIGHT = 1600;
    static final int WORLD_WIDTH = 1216;

    private static final int SCREEN_HEIGHT = 600;
    private static final int SCREEN_WIDTH = 800;
    private static Tank tank1, tank2;
    private final int Tank1XSpawn = 608, Tank1YSpawn = 70, Tank1AngleSpawn = 90;
    private final int Tank2XSpawn = 608, Tank2YSpawn = 1480, Tank2AngleSpawn = 270;
    private BufferedImage gameWorld;
    private Graphics2D buffer;
    private JFrame jframe;

    private Collision collisions;

    public void addGameObject(GameObject obj) { //package private
        this.gameObjects.add(obj);
    }

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int player1Lives = 3;
    private int player2Lives = 3;
    static boolean gameOver = false;
    private static String winner;
    private static BufferedImage player1winner, player2winner;

    public static void main(String[] args) {
        GameWorld tankGame = new GameWorld();
        tankGame.init();
        tankGame.collisions = new Collision();
        try {
            while (!gameOver) {
                tankGame.repaint();
                for (int i = 0; i < tankGame.gameObjects.size(); i++) {

                    if (tankGame.gameObjects.get(i) instanceof Tank) {
                        if (((Tank) tankGame.gameObjects.get(i)).getHealth() == 0) {
                            if (((tankGame.gameObjects.get(i)).equals(tank1))) {
                                if (tankGame.player1Lives < 2) {
                                    tankGame.player1Lives = 0;
                                    gameOver = true;
                                    winner = "player2";
                                    break;
                                } else {
                                    tankGame.player1Lives--;
                                    ((Tank) tankGame.gameObjects.get(i)).setHealth(100);
                                    tankGame.gameObjects.get(i).setX(tankGame.Tank1XSpawn);
                                    tankGame.gameObjects.get(i).setY(tankGame.Tank1YSpawn);
                                    tankGame.gameObjects.get(i).setAngle(tankGame.Tank1AngleSpawn);
                                }
                            }

                            if (((tankGame.gameObjects.get(i)).equals(tank2))) {
                                if (tankGame.player2Lives < 2) {
                                    tankGame.player2Lives = 0;
                                    gameOver = true;
                                    winner = "player1";
                                    break;
                                } else {
                                    tankGame.player2Lives--;
                                    ((Tank) tankGame.gameObjects.get(i)).setHealth(100);
                                    tankGame.gameObjects.get(i).setX(tankGame.Tank2XSpawn);
                                    tankGame.gameObjects.get(i).setY(tankGame.Tank2YSpawn);
                                    tankGame.gameObjects.get(i).setAngle(tankGame.Tank2AngleSpawn);
                                }
                            }

                        }
                    }

                    if (tankGame.gameObjects.get(i) instanceof Bullet) {
                        if (((Bullet) tankGame.gameObjects.get(i)).activeCheck())
                            tankGame.gameObjects.remove(i--);

                        else
                            tankGame.gameObjects.get(i).update();

                    }

                    if (((tankGame.gameObjects.get(i) instanceof BreakableWall) && ((BreakableWall) tankGame.gameObjects.get(i)).getDurability() == 0))
                        tankGame.gameObjects.remove(i);

                }
                tankGame.gameObjects = tankGame.collisions.collide(tankGame.gameObjects);
                tankGame.tank1.update();
                tankGame.tank2.update();
                Thread.sleep(5);
            }
        }
        catch (InterruptedException ex) { }

    }

    private void init() {
        this.jframe = new JFrame("Tank Game");
        this.gameWorld = new BufferedImage(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            BufferedImage tank, bullet, background, unbreakableWall, breakableWall, explosion;
            tank = ImageIO.read(getClass().getResource("/tank1.png"));

            background = ImageIO.read(getClass().getResource("/Background.bmp"));
            Background.setBackgroundImage(background);

            unbreakableWall = ImageIO.read(getClass().getResource("/Wall2.gif"));
            UnbreakableWall.setUnbreakableWall(unbreakableWall);

            breakableWall = ImageIO.read(getClass().getResource("/Wall1.gif"));
            BreakableWall.setBreakableWall(breakableWall);

            bullet = ImageIO.read(getClass().getResource("/Shell.gif"));
            Bullet.setBufferedImage(bullet);

            explosion = ImageIO.read(getClass().getResource("/Explosion_small.gif"));
            Bullet.setExplosionImage(explosion);

            PowerUp.setHealthIMG(ImageIO.read(getClass().getResource("/Shield1.gif")));

            player1winner = ImageIO.read(getClass().getResource("/player1wins.png"));
            player2winner = ImageIO.read(getClass().getResource("/player2wins.png"));

            this.tank1 = new Tank(Tank1XSpawn, Tank1YSpawn, 0, 0, Tank1AngleSpawn, tank);
            this.tank1.setPlayer("Player1");
            this.tank2 = new Tank(Tank2XSpawn, Tank2YSpawn, 0, 0, Tank2AngleSpawn, tank);
            this.tank2.setPlayer("Player2");

        }
        catch (IOException ex) { }

        for (int i = 0; i < WORLD_WIDTH; i = i + 320) {
            for (int j = 0; j < WORLD_HEIGHT; j = j + 240)
                gameObjects.add(new Background(i, j));

        }

        int[][] map = new int[50][38];
        Random rand = new Random();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 38; j++) {
                if(i == 0 || i == 49 || j == 0 || j == 37)
                    gameObjects.add(new UnbreakableWall(j * 32, i * 32));

                if(map[i][j] == rand.nextInt(50) && i > 3 && i < 46 && j > 3 && j < 34)
                    gameObjects.add(new UnbreakableWall(j * 32, i * 32));

                else if (map[i][j] == rand.nextInt(20) && i > 3 && i < 45 && j > 3 && j < 34)
                    gameObjects.add(new BreakableWall(j * 32, i * 32));

            }
        }

        this.gameObjects.add(this.tank1);
        this.tank1.setGameWorld(this);

        this.gameObjects.add(this.tank2);
        this.tank2.setGameWorld(this);

        PowerUp power1 = new PowerUp(204, 800), power2 = new PowerUp(608, 800), power3 = new PowerUp(948, 800);
        this.gameObjects.add(power1);
        this.gameObjects.add(power2);
        this.gameObjects.add(power3);

        this.jframe.setLayout(new BorderLayout());
        this.jframe.add(this);

        this.jframe.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT);
        this.jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);

        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setVisible(true);

        TankControl controlSet1 = new TankControl(tank1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q); //adding control
        TankControl controlSet2 = new TankControl(tank2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jframe.addKeyListener(controlSet1);
        this.jframe.addKeyListener(controlSet2);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        this.buffer = this.gameWorld.createGraphics();
        super.paintComponent(g);
        for (int i = 0; i < gameObjects.size(); i++)
            this.gameObjects.get(i).drawImage(buffer);

        int player1X = tank1.getX(), player2X = tank2.getX();
        int player1Y = tank1.getY(), player2Y = tank2.getY();

        if (player1X < SCREEN_WIDTH / 4)
            player1X = SCREEN_WIDTH / 4;
        else if (player1X > WORLD_WIDTH - SCREEN_WIDTH / 2)
            player1X = WORLD_WIDTH - SCREEN_WIDTH / 2;

        if (player1Y < SCREEN_HEIGHT / 2)
            player1Y = SCREEN_HEIGHT / 2;
        else if (player1Y > WORLD_HEIGHT - SCREEN_HEIGHT / 2)
            player1Y = WORLD_HEIGHT - SCREEN_HEIGHT / 2;

        if (player2X < SCREEN_WIDTH / 4)
            player2X = SCREEN_WIDTH / 4;
        else if (player2X > WORLD_WIDTH - SCREEN_WIDTH / 2)
            player2X = WORLD_WIDTH - SCREEN_WIDTH / 2;

        if (player2Y < SCREEN_HEIGHT / 2)
            player2Y = SCREEN_HEIGHT / 2;
        else if (player2Y > WORLD_HEIGHT - SCREEN_HEIGHT / 2)
            player2Y = WORLD_HEIGHT - SCREEN_HEIGHT / 2;

        BufferedImage leftScreen = this.gameWorld.getSubimage(player1X - SCREEN_WIDTH / 4, player1Y - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
        BufferedImage rightScreen = this.gameWorld.getSubimage(player2X - SCREEN_WIDTH / 4, player2Y - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        g1.drawImage(leftScreen, 0, 0, null);
        g1.drawImage(rightScreen, SCREEN_WIDTH / 2 + 1, 0, null);

        g1.drawImage(this.gameWorld, SCREEN_WIDTH / 2 - GameWorld.WORLD_WIDTH / 16, SCREEN_HEIGHT - GameWorld.WORLD_HEIGHT / 4, GameWorld.WORLD_WIDTH / 8, WORLD_HEIGHT / 8, null);
        g1.setFont(new Font("Dialog", Font.PLAIN, 20));
        g1.setColor(Color.WHITE);

        g1.drawString("Player 1       Lives: " + this.player1Lives, 10, 28);
        g1.drawString("Player 2       Lives: " + this.player2Lives, SCREEN_WIDTH / 2 + 10, 28);

        g1.setColor(Color.green);
        g1.fillRect(10, 30, 2 * tank1.getHealth(), 10);
        g1.fillRect(SCREEN_WIDTH / 2 + 10, 30, 2 * tank2.getHealth(), 10);

        if (winner == "player1") {
            g1.drawImage(player1winner, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }
        else if (winner == "player2") {
            g1.drawImage(player2winner, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }
    }
}
