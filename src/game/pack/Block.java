package game.pack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {


    private BufferedImage image;

    public Block(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id,ss);
        image = ss.grabImage(1,1,32,32);
    }



    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
