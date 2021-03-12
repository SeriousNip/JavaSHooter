package game.pack;

import java.awt.*;

public class Bullet extends GameObject {

    private Handler handler;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        velX = (mx-x)/35;
        velY = (my-y)/35;
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;
        for(int i=0; i <handler.object.size(); i++){
                GameObject tempObject = handler.object.get(i);

                if(tempObject.getId() == ID.Block){
                    if(getBounds().intersects(tempObject.getBounds())){
                        handler.removeObject(this);
                    }
                }
            }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x,y,5,5);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,5,5);
    }
}
