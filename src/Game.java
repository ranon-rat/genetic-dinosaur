
import java.awt.*;
import java.util.Random;

public class Game {
    public int width = 800;
    public int height = 600;
    public int framesPerSecond=12;

    // this is for testing for now, later I will generate more obstacles
    Obstacle obs ;
    Population population = new Population(10);
   // Brain b=new Brain();
    // the list of possible obstacles for use it later
    String[] options ={"big cactus", "two big cactus", "three big cactus", "small cactus", "two small cactus", "three small cactus", "duck"};
    Game(){
        obs = new Obstacle(options[(int) (Math.random() * options.length)], this);
    }
    public void clean(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
    }

    public void update(Graphics2D g) {
        clean(g);
        g.setColor(Color.black);
        obs.show(g, this);
        population.doSomething(obs, g, this);
      //  b.show(g,this);
        if (obs.x < -obs.width) {
            obs = new Obstacle(options[(new Random()).nextInt(options.length)],this);
        }
        try {
            Thread.sleep(1000/framesPerSecond);
        } catch (InterruptedException ignored) {
        }


    }


}

