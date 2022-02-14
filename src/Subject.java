import java.awt.*;
import java.util.ArrayList;

class Subject  {
    Brain brain;
    Dinosaur dino = new Dinosaur();
    String[] names = {"width", "height", "distance", "y obstacle", "speed", "y player"};
    String name;

    boolean death = false;


    Subject(String name) {
        this.name = name;
        brain = new Brain();
        for (int i = 0; i < names.length; i++) {
            brain.network.get(i).name = names[i];

        }
    }

    void doSomething(Obstacle obstacle) {

        if (death) {
            return;
        }
        death = obstacle.isOnArea(dino);


        // after all that I clear the nodes
        brain.clearNodes();

        // This is for pass the values to the input nodes of the neural network

        ArrayList<Double> input = new ArrayList<>();

        input.add((double) obstacle.width);         // width of obstacle
        input.add((double) obstacle.height);        // height of obstacle
        input.add((double) obstacle.x);             // distance of obstacle
        input.add((double) obstacle.y);             // y pos of obstacle
        input.add((double) obstacle.movePerFrame);  // speed
        input.add((double) dino.y);                 // y pos of player

        brain.passToInput(input);
        //then I get the result

        ArrayList<Double> output = brain.result();

        if (output.get(0) > 0.8) //small jump
            dino.jump(false);
        else if (output.get(1) > 0.8) // big jump
            dino.jump(true);
        else if (output.get(2) > 0.8) //duck
            dino.duck();
        ArrayList<Node> net=brain.network;

        dino.moving();

    }

    void show(Graphics2D g, Game screen) {
        if (!death) {
            dino.show(g, screen);
            brain.show(g, screen);
        }

    }


}