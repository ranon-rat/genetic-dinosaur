import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Obstacle {
    int x;
    int y = 0;
    int height;
    int width;
    int widthScreen;

    int vel = 10 ;// x per second
    float time=1;
    ArrayList<Image> sprite = new ArrayList<>();
    String type;


    //this is only for generate the obstacles
    public Obstacle(String typeOfObstacle, Game screen) {

        this.x = screen.width;
        widthScreen = screen.width;
        type = typeOfObstacle;

        switch (type) {
            case "duck" -> {

                Random rnd = new Random();
                int[] possibleHeights = {123, 40, 10};
                this.y = possibleHeights[rnd.nextInt(possibleHeights.length)];
                this.width = 45;
                this.height = 30;
                sprite.add(others.getImage("sprites/duck1.png"));
                sprite.add(others.getImage("sprites/duck2.png"));
            }

            case "small cactus" -> {
                this.height = 43;
                this.width = 20;
            }
            case "two small cactus" -> {
                this.width = 43;
                this.height = 41;
            }
            case "three small cactus" -> {
                this.height = 43;
                this.width = 62;
            }
            case "big cactus" -> {
                this.height = 60;
                this.width = 31;
            }
            case "two big cactus" -> {
                this.height = 60;
                this.width = 62;
            }
            case "three big cactus" -> {
                this.height = 60;
                this.width = 91;
            }
            default -> System.err.format("\"%s\" not found", type);
        }
        if (!Objects.equals(type, "duck")) sprite.add(others.getImage("sprites/" + type + ".png"));

    }

    // I just show the obstacle
    public void show(Graphics g, Game screen) {

        g.drawImage(sprite.get(Math.abs(x % sprite.size())), x, screen.height - y - height - 30, width, height, null);
        time+=0.01;
        time%=200000;
        x -= vel*time;


    }

    // I will use it for detect if the dinosaur touch the obstacle
    // if that is the case , this function should return true
    public boolean isOnArea(Dinosaur dino) {

        return dino.width >= this.x &&
                0 <= this.x + this.width &&
                dino.y + dino.height >= this.y &&
                dino.y <= this.y + this.height;
    }
}