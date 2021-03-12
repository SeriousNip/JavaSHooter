package game.pack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    private boolean isRunning = false;
    private Thread thread;
    public int ammo = 10;
    private Handler handler;
    private BufferedImage level = null;
    private Camera camera;
    private BufferedImage SpriteSheetFloor = null;
    private BufferedImage SpriteSheetWall = null;
    private BufferedImage SpriteSheetPlayer = null;
    private BufferedImage SpriteSheetEnemy = null;
    private BufferedImage SpriteSheetChest = null;
    private SpriteSheet ssfloor;
    private SpriteSheet sswall;
    private SpriteSheet ssPlayer;
    private SpriteSheet ssEnemy;
    private SpriteSheet ssChest;
    private BufferedImage floor;
    private BufferedImage wall;
    public int hp =100;


    public Game() {
        new Window(1000, 563, "Top Down Shooter", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler,this));


        BufferedImageLoader loader = new BufferedImageLoader();

        level = loader.loadImage("/Level.png");
        SpriteSheetFloor = loader.loadImage("/floor.png");
        SpriteSheetWall = loader.loadImage("/Wall.png");
        SpriteSheetEnemy = loader.loadImage("/enemy.png");
        SpriteSheetPlayer = loader.loadImage("/player.png");
        SpriteSheetChest = loader.loadImage("/Chests.png");
        ssfloor= new SpriteSheet(SpriteSheetFloor);
        sswall = new SpriteSheet(SpriteSheetWall);
        ssEnemy = new SpriteSheet(SpriteSheetEnemy);
        ssPlayer = new SpriteSheet(SpriteSheetPlayer);
        ssChest = new SpriteSheet(SpriteSheetChest);
        this.addMouseListener(new MouseInput(handler, camera,this,ssfloor));

        floor = ssfloor.grabImage(1,1,32,32);
        wall = sswall.grabImage(1,1,32,32);
        loadLevel(level);
    }


    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double ammountOfTicks = 60.0;
        double ns = 1000000000 / ammountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                // updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                // updates = 0;
            }
        }
        stop();
    }


    public void tick() {

            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getId() == ID.Player) {
                    camera.tick(handler.object.get(i));
                }
            }

            handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        Graphics2D g2d = (Graphics2D) g;
            // starting here we draw stuf
            g2d.translate(-camera.getX(), -camera.getY());
            for (int xx = 0; xx < 30 * 72; xx += 32) {
                for (int yy = 0; yy < 30 * 72; yy += 32) {
                    g.drawImage(floor, xx, yy, null);
                }
            }


            handler.render(g); // renders objects on top of the background
            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.green);
            g.fillRect(5, 5, hp * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);
            g.setColor(Color.white);
            g.drawString("Ammo: " + ammo, 5, 50);


            // here ends the drawing part
            g.dispose();
            bs.show();


    }

    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getWidth();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255)
                    handler.addObject(new Block(xx * 32, yy * 32, ID.Block,sswall));
                if (blue == 255 && green == 0)
                    handler.addObject(new Player(xx * 32, yy * 32, ID.Player, handler,this,ssPlayer));
                if (green == 255)
                    handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler,ssEnemy));
                if(blue==255 && green == 255)
                    handler.addObject(new AmmoCrate(xx*32,yy*32,ID.Crate,handler,ssChest));
            }
        }
    }


    public static void main(String[] args) {
        new Game();
    }


}
