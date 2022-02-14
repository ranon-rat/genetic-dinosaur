import java.awt.*;
import java.util.ArrayList;

public class Dinosaur {

    int realWidth = 56;
    int realHeight = 60;
    int width = 56;
    int height = 60;
    int y = 0;
    float velY;
    float gravity;
    int score = 0;
    boolean hunker = false;

    ArrayList<Image> dinoSprites = new ArrayList<>();
    ArrayList<Image> dinoDie = new ArrayList<>();

    ArrayList<Image> dinoHunkerSprites = new ArrayList<>();
    ArrayList<Image> actualSprite;


    Dinosaur() {
        // this is for load the sprites
        for (int i = 1; i <= 4; i++) {


            dinoSprites.add(others.getImage("sprites/dino" + i + ".png"));
        }
        dinoDie.add(others.getImage("sprites/dino5.png"));          //die
        dinoHunkerSprites.add(others.getImage("sprites/dino6.png"));//duck
        dinoHunkerSprites.add(others.getImage("sprites/dino7.png"));//duck
        actualSprite = dinoSprites;


    }

    void jump(boolean bigJump) {
        if (y == 0) {
            gravity = 0.25f;
            if (bigJump) {

                velY = 10;
                return;
            }

            velY = 8;
        }
    }


    void duck() {

        gravity = 2;// I change the gravity
        width = (int) (realWidth * 1.3409090909);
        height = (int) (realHeight * 0.6382978723);
        actualSprite = dinoHunkerSprites;
    }

    void moving() {
        y += velY;
        score++;
        if (y > 0) {
            velY -= gravity;
        } else {
            velY = 0;
            y = 0;
        }
        if (!hunker) {
            actualSprite = dinoSprites;
        }
    }

    void show(Graphics g, Game screen) {

        g.setColor(Color.black);
        //this is just for make a movement animation
        g.drawImage(actualSprite.get(score % actualSprite.size()), 0, screen.width - y - height - 30, width, height, null);

        moving();
        if (score % 4 == 0 && hunker) {
            hunker = false;
            return;
        }
        width = realWidth;
        height = realHeight;
    }
}








