package game.pack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

    private Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    private BufferedImage image;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        image = ss.grabImage(2,1,32,64);

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        choose = r.nextInt(10);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObj = handler.object.get(i);

            if (tempObj.getId() == ID.Block) {
                if (getBoundsbig().intersects(tempObj.getBounds())) {
                    x += (velX * 3) * -1;
                    y += (velY * 3) * -1;
                    velX=-1;
                    velY=-1;
                } else {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
                }
            }

            if(tempObj.getId()==ID.Bullet){
                if (getBoundsbig().intersects(tempObj.getBounds())){
                    hp-=50;
                    handler.removeObject(tempObj);
                }
            }
        }
        if(hp<=0){
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 25, 25);
    }

    public Rectangle getBoundsbig() {
        return new Rectangle(x - 16, y - 16, 50, 50);
    }
}
