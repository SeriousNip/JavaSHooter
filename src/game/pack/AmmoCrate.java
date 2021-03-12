package game.pack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AmmoCrate extends GameObject {

    private Handler handler;
    private BufferedImage image;

    public AmmoCrate(int x, int y, ID id,Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler=handler;
        image = ss.grabImage(1,1,32,32);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
