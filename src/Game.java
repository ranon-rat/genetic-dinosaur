import java.awt.*;
import java.util.ArrayList;

public class Game {
    public int width = 600;
    public int height = 600;
    // this is for testing for now, later I will generate more obstacles
    Obstacle obs = new Obstacle("duck", this);

    Subject subject=new Subject();

    // the list of possible obstacles for use it later
    String[] options =  {"big cactus","two big cactus","three big cactus","small cactus","two small cactus","three small cactus","duck"};

    public void clean(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    Game(){
        subject.brain.mutate();
    }

    public void update(Graphics2D g) {
        clean(g);



        g.setColor(Color.black);

        obs.show(g, this);
        subject.doSomething(obs);

        subject.show(g,this);

        if (obs.x < -obs.width) {
            obs = new Obstacle(options[(int) (Math.random() * options.length)], this);

        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {
        }


    }


}

