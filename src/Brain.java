import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Layers {
    int length, loop, start;

    Layers(int loop, int start, int length) {
        this.length = length;
        this.start = start;
        this.loop = loop;

    }
}

public class Brain implements Cloneable {
    ArrayList<Node> network = new ArrayList<>();
    Font myFont = new Font("Courier New", Font.BOLD, 5);
    int hiddenLayers = 3,// 3 hidden layers
            lengthOfHiddenLayers = 8,
            output = 3,// length of output nodes
    /*
    0 jump
    0 big jump
    0 duck
    */
    input = 6;// length of input nodes
    /*
                            ///0| ---> 0 ---> 0 --->\
    |width of obstacle     ////0| ---> 0 ---> 0 ---->\
|   |distance of next obstacle/0| ---> 0 ---> 0 ----->\--->small jump
    |height of obstacle  //////0| ---> 0 ---> 0 ------>\--> big jump
    |y position of obstacle\\\\0| ---> 0 ---> 0 ------>/--> duck
    |speed                \\\\\0| ---> 0 ---> 0 ----->/
    |player y pos          \\\\0| ---> 0 ---> 0 ---->/
                            \\\0| ---> 0 ---> 0 --->/
    */


    Brain() {
        Random rnd = new Random();
        // first I add the layers

        Layers[] layers = {new Layers(1, 0, input),
                new Layers(hiddenLayers + 1, 1, lengthOfHiddenLayers),
                new Layers(hiddenLayers + 2, hiddenLayers + 1, output)};
        ArrayList<Integer> lengths = new ArrayList<>();
        for (Layers l : layers) {
            for (int layer = l.start; layer < l.loop; layer++) {
                for (int index = 0; index < l.length; index++) {
                    network.add(new Node(layer, index));
                }
                lengths.add(l.length);
            }
        }
        //input
        System.out.println(lengths.size() + " " + network.size());

        //this should generate some connections
        for (int n = 0; n < (network.size() - output); n++) {


            int layer = network.get(n).layer;
            network.get(n).addNewConnection(network.get(layer + rnd.nextInt(lengths.get(layer + 1))));
            network.get(n).changeBias();
            network.get(n).changeWeights();
        }


    }


    // you will get the result after finishing the operation
    public ArrayList<Double> result() {
        ArrayList<Double> output = new ArrayList<>();
        for (Node node : network) //this is not going to be at the output layer and just that
            node.engage();
        for (Node node : network.subList(hiddenLayers + 1, hiddenLayers + 2)) {
            output.add(node.output);
        }

        return output;
    }

    public void passToInput(ArrayList<Double> x) {
        if (x.size() != input) {
            System.err.println("the input its different to the input that it should have");
            return;
        }
        for (int i = 0; i < input; i++)
            network.get(i).output = x.get(i);

    }


    public void clearNodes() {

        for (Node node : network) node.clear();

    }

    // maybe I should create a new connections
    public void mutate() {
        for (Node node : network) {

            node.changeWeights();
            node.changeBias();
        }
    }

    // I hate java
    // execute this function after clone this object
    public void quitPointers() {
        for (Node node : network) //this is not going to be at the output layer and just that
            for (Node conNode : node.connections) {
                node.connections.remove(conNode);
                node.addNewConnection(conNode);
            }
    }

    public void show(Graphics2D g, Game sc) {
        g.setFont(myFont);
        var width = sc.width - 20;
        var height = sc.height - 20;
        var separationWidth = width / (hiddenLayers + 2);
        var separationHeight = height / lengthOfHiddenLayers / 3;

        for (Node node : network) {
            ArrayList<Node> connections = node.connections;
            ArrayList<Double> weights = node.weights;
            for (int i = 0; i < connections.size(); i++) {
                g.setStroke(new BasicStroke(Math.abs(weights.get(i).floatValue() * 1.5f)));
                g.setColor(Color.getHSBColor((float) (node.output * 360), 100, 50));
                g.drawLine(10 + node.layer * separationWidth, 10 + node.index * separationHeight, 10 + connections.get(i).layer * separationWidth, 10 + 2 + connections.get(i).index * separationHeight);
            }
            g.setColor(Color.blue);
            g.setStroke(new BasicStroke(1));
            g.drawArc(10 + node.layer * separationWidth, 10 + node.index * separationHeight, 5, 5, 5, 360);
            g.setColor(Color.black);
            g.drawString(("" + node.output), node.layer * separationWidth, node.index * separationHeight);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        clearNodes();
        return super.clone();
    }

}
