import java.awt.*;
import java.util.ArrayList;

class Subject  {
    Brain brain;
    Dinosaur dino = new Dinosaur();
    String[] names = {"width", "height", "distance", "y obstacle", "speed", "y player"};
    String[] outputNames={"small jump","big jump","duck"};
    String name;
    String action = "nothing";

    boolean death = false;


    Subject(String name) {
        this.name = name;
        brain = new Brain();
        for (int i = 0; i < names.length; i++) {
            brain.network.get(i).name = names[i];

        }
        for(int i=0;i<outputNames.length;i++){
            brain.network.get((brain.network.size()-brain.output)+i).name=outputNames[i];
        }
    }

    void doSomething(Obstacle obstacle) {
        action = "nothing";
        if (death) {
            dino.y=0;
            return;
        }
        death = obstacle.isOnArea(dino);
        // after all that I clear the nodes
        brain.clearNodes();


        // This is for pass the values to the input nodes of the neural network

        ArrayList<Float> input = new ArrayList<>();

        input.add((float) obstacle.width / 91);         // width of obstacle
        input.add((float) obstacle.height / 60);        // height of obstacle
        input.add((float) obstacle.x / obstacle.widthScreen);             // distance of obstacle
        input.add((float) obstacle.y / 50);             // y pos of obstacle
        input.add( (obstacle.vel*obstacle.time)/3000000);  // speed
        input.add((float) dino.y / 159);                 // y pos of player

        brain.passToInput(input);
        //then I get the result


        ArrayList<Float> output = brain.result();


        int index = others.getBiggerIndex(output);
        if (output.get(index) > 0.7) {

            switch (index) {
                case 0 -> {
                    action = "small jump";
                    dino.jump(false);
                }
                case 1 -> {

                    action = "big jump";
                    dino.jump(true);
                }
                case 2 -> {
                    action = "duck";
                    dino.duck();
                }
            }

        }


        dino.moving();

    }

    void show(Graphics2D g, Game screen) {

        if (!death) {
            g.drawString(action, (screen.width / 2) - (action.length() / 2), screen.height / 2);
            dino.show(g, screen);
            brain.show(g, screen);

        }

    }



}