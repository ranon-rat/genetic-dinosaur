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
    boolean duck = false;

    ArrayList<Image> dinoSprites = new ArrayList<>();
    ArrayList<Image> dinoDie = new ArrayList<>();

    ArrayList<Image> dinoDuckSprites = new ArrayList<>();
    ArrayList<Image> actualSprite;


    Dinosaur() {
        // this is for load the sprites
        for (int i = 1; i <= 3; i++) {


            dinoSprites.add(others.getImage("sprites/dino" + i + ".png"));
        }
        dinoDie.add(others.getImage("sprites/dino5.png"));          //die
        dinoDuckSprites.add(others.getImage("sprites/dino6.png"));//duck
        dinoDuckSprites.add(others.getImage("sprites/dino7.png"));//duck
        actualSprite = dinoSprites;


    }


    void jump(boolean bigJump) {
        duck=false;
        if (y == 0) {
            score-=10;
            velY = 15;
            gravity=1f;
            if (bigJump) {
                gravity = 0.7f;

            }


        }
    }


    void duck() {
        duck = true;
        
        gravity = 3;// I change the gravity
        width = (int) (realWidth * 1.3409090909);
        height = (int) (realHeight * 0.6382978723);
        actualSprite = dinoDuckSprites;

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

        if (duck) {
            duck = !(score % 4 == 0);
            return;
        }
        actualSprite = dinoSprites;
        width = realWidth;
        height = realHeight;
    }

    void show(Graphics g, Game screen) {

        g.setColor(Color.black);
        //this is just for make a movement animation

        g.drawImage(actualSprite.get(Math.abs(score % actualSprite.size())), 0, screen.height - y - height - 30, width, height, null);


    }
}








