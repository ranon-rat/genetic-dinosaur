import java.util.ArrayList;

public class Brain {
    ArrayList<ArrayList<Node>> network = new ArrayList<>();
    int layers = 5;// 3 hidden layers
    int lengthOfHiddenLayers = 8;
    int output = 3;// length of output nodes
    /*
    jump
    big jump
    duck
    */
    int input = 6;// length of input nodes

    /*
    width of obstacle         ---hidden---hidden----\
    distance of next obstacle ---hidden---hidden---- \--->small jump
    height of obstacle        ---hidden---hidden----  \--> big jump
    y position of obstacle    ---hidden---hidden----  /--> duck
    speed                     ---hidden---hidden---- /
    player y pos              ---hidden---hidden----/
    */

    Brain() {
        //this will create a neural network , then I need to connect the nodes and all that stuff
        for (int x = 0; x < layers; x++)
            network.add(new ArrayList<>());
        for (int y = 0; y < input; y++)
            network.get(0).add(new Node(0));
        for (int x = 1; x < layers - 1; x++)
            for (int y = 0; y < lengthOfHiddenLayers; y++)
                network.get(x).add(new Node(x));
        for (int y = 0; y < output; y++)
            network.get(layers - 1).add(new Node(layers-1));
    }
}
