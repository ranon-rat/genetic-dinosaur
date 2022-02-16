
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    public int width = 800;
    public int height = 600;
    public int framesPerSecond = 14;

    // this is for testing for now, later I will generate more obstacles
    Obstacle obstacle;
    Population population = new Population(10);
    // Brain b=new Brain();
    // the list of possible obstacles for use it later
    String[] options = {"big cactus", "two big cactus", "three big cactus", "small cactus", "two small cactus", "three small cactus", "duck"};

    Game() {

        obstacle = new Obstacle(options[(new Random()).nextInt(options.length)], this);
    }

    public void clean(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
    }

    public void update(Graphics2D g) {
        clean(g);


        obstacle.show(g, this);


        population.doSomething(obstacle, g, this);
        //  b.show(g,this);
        if (obstacle.x < -obstacle.width) {
            float time= obstacle.time;
            time += 0.005;
            time %= 200000;
            obstacle = new Obstacle(options[(new Random()).nextInt(options.length)], this);
            obstacle.time=time;


        }

        try {
            Thread.sleep(1000 / framesPerSecond);
        } catch (InterruptedException ignored) {
        }


    }


}

