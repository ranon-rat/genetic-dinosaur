import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Brain implements Cloneable {
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
        Random rnd = new Random();

        for (int x = 0; x < layers; x++)
            network.add(new ArrayList<>());


        //this will create a neural network , then I need to connect the nodes and all that stuff
        for (int y = 0; y < input; y++)
            network.get(0).add(new Node(0, y));

        for (int x = 1; x < layers - 1; x++)
            for (int y = 0; y < lengthOfHiddenLayers; y++)
                network.get(x).add(new Node(x, y));

        for (int y = 0; y < output; y++)
            network.get(layers - 1).add(new Node(layers - 1, y));

        //this should generate some connections
        for (int x = 0; x < network.size() - 1; x++) //this is not going to be at the output layer and just that
            for (int y = 0; y < network.get(x).size(); y++) {
                //I hate myself for writing this
                network.get(x).get(y).addNewConnection(network.get(x + 1).get(rnd.nextInt(network.get(x + 1).size() - 1)));
                network.get(x).get(y).changeWeights();
                network.get(x).get(y).changeWeights();
            }

    }

    // I hate java pointers
    // execute this function after clone this object
    public void replaceAndConnectTheNodesLikeTheOriginal() {
        for (int x = 0; x < network.size() ; x++) //this is not going to be at the output layer and just that
            for (int y = 0; y < network.get(x).size(); y++)
                // I hate my self for make this function large
                for (int connections = 0; connections < network.get(x).get(y).connectedNodes.size(); connections++) {
                    Node n = network.get(x).get(y).connectedNodes.get(connections);
                    network.get(x).get(y).connectedNodes.remove(n);
                    network.get(x).get(y).addNewConnection(network.get(n.layer).get(n.Y));


                }


    }

    public void show(Graphics g, Game sc) {
        g.setColor(Color.black);
        int width = sc.width - 20;
        int height = sc.height - 20;
        int separationWidth = width / network.size();
        for (int x = 0; x < network.size(); x++) {
            int x1 = x + 1 % network.size();
            int separationHeight = height / 3 / network.get(x).size();

            for (int y = 0; y < network.get(x).size(); y++) {
                Node n = network.get(x).get(y);
                g.drawArc(x * separationWidth, 10 + y * separationHeight, 5, 5, 5, 360);
                for (int c = 0; c < n.connectedNodes.size(); c++) {
                    g.drawLine(x * separationWidth, 10 + y * separationHeight, n.connectedNodes.get(c).layer * separationWidth, 10+n.connectedNodes.get(c).Y * separationHeight);

                }

            }
        }

    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
