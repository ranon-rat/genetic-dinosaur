import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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

        input.add((double) obstacle.width/91);         // width of obstacle
        input.add((double) obstacle.height/60);        // height of obstacle

        input.add((double)obstacle.x/obstacle.widthScreen);             // distance of obstacle
        input.add((double) obstacle.y/50);             // y pos of obstacle
        input.add((double) obstacle.movePerFrame/20);  // speed
        input.add((double) dino.y/10);                 // y pos of player

        brain.passToInput(input);
        //then I get the result

        ArrayList<Double> output = brain.result();
        var temp=(ArrayList<Double>)output.clone();
        Collections.sort(temp);

        int index=output.indexOf(temp.get(temp.size()-1));


        if(output.get(index)>0.8) {

            switch (index) {
                case 0 -> {
                    dino.jump(false);
                }
                case 1 -> {
                    dino.jump(true);
                }
                case 2 -> {

                    dino.duck();
                }
            }

        }


        dino.moving();

    }

    void show(Graphics2D g, Game screen) {
        if (!death) {
            dino.show(g, screen);
            brain.show(g, screen);
        }

    }


}