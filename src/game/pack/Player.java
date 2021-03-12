package game.pack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    Handler handler;
    Game game;
    private String facing;
    private BufferedImage image;

    public Player(int x, int y, ID id, Handler handler,Game game, SpriteSheet ss) {
        super(x, y, id,ss);
        this.game = game;
        this.handler = handler;
        image = ss.grabImage(2,3,32,32);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        collision();

        if (handler.isUp()) velY = -5;
        else if (!handler.isDown()) {velY = 0; facing = "up";}

        if (handler.isDown()) velY = 5;
        else if (!handler.isUp()) {velY = 0; facing ="down";}

        if (handler.isRight()) velX = 5;
        else if (!handler.isLeft()) {velX = 0; facing="right";}

        if (handler.isLeft()) velX = -5;
        else if (!handler.isRight()) {velX = 0; facing="left";}
    }

    private void collision(){
        for( int i = 0; i<handler.object.size(); i++){
            GameObject tempObj = handler.object.get(i);

            if(tempObj.getId()== ID.Block){
                if(getBounds().intersects(tempObj.getBounds())){
                    x+= velX*-1;
                    y+= velY*-1;
                }
            }

            if(tempObj.getId()== ID.Crate){
                if(getBounds().intersects(tempObj.getBounds())){
                    game.ammo+=10;
                    handler.removeObject(tempObj);
                }
            }
            if(tempObj.getId()== ID.Enemy){
                if(getBounds().intersects(tempObj.getBounds())){
                    game.hp --;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y,25,25);
    }
}
