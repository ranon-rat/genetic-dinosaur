import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Brain {

    ArrayList<Node> network = new ArrayList<>();// simple neural network
    ArrayList<Integer> lengths = new ArrayList<>();// this is for know the length of each layer
    ArrayList<Color> colors = new ArrayList<>();

    Font myFont = new Font("Courier New", Font.BOLD, 8);

    Random rnd = new Random();

    int hiddenLayers = 3,// 3 hidden layers
            lengthOfHiddenLayers = 8,
            output = 3,

    // length of output nodes

    /*
    0 jump
    0 big jump
    0 duck
    */
    input = 6;// length of input nodes
    /*
                            ///0| ---> 0 ---> 0 --->\
    |width of obstacle     ////0| ---> 0 ---> 0 ---->\
|   |height of obstacle   /////0| ---> 0 ---> 0 ----->\--->small jump
    |distance of next obstacle/0| ---> 0 ---> 0 ------>\--> big jump
    |y position of obstacle\\\\0| ---> 0 ---> 0 ------>/--> duck
    |speed                \\\\\0| ---> 0 ---> 0 ----->/
    |player y pos          \\\\0| ---> 0 ---> 0 ---->/
                            \\\0| ---> 0 ---> 0 --->/
    */


    Brain() {
        colors.add(Color.black);
        colors.add(Color.black);
        colors.add(Color.black);


        Random rnd = new Random();
        // first I add the layers

        Layers[] layers = {new Layers(1, 0, input),
                new Layers(hiddenLayers + 1, 1, lengthOfHiddenLayers),
                new Layers(hiddenLayers + 2, hiddenLayers + 1, output)};

        int min = 0;
        for (Layers l : layers) {
            for (int layer = l.start; layer < l.loop; layer++) {
                for (int index = 0; index < l.length; index++) {
                    network.add(new Node(layer, index, min + index, "" + index));
                }
                min += l.length;
                lengths.add(l.length);

            }

        }


        //this should generate some connections
        min = 0;

        for (int layer = 0; layer < lengths.size() - 1; layer++) {

            for (int index = 0; index < lengths.get(layer); index++) {
                int pos = min + index;
                if (network.get(pos).nodesConnectedToThis == 0 && network.get(0).layer != 0) {
                    continue;
                }

                Node randomNode = network.get(min + lengths.get(layer) + rnd.nextInt(lengths.get(layer + 1)));
                randomNode.nodesConnectedToThis++;
                Node n = network.get(pos);
                n.addNewConnection(
                        randomNode
                );
                n.changeBias();
                n.changeWeights();
            }
            min += lengths.get(layer);


        }
        for (Node output : network.subList(network.size() - output, network.size())) {
            output.last = true;
        }


    }


    // you will get the result after finishing the operation
    public ArrayList<Double> result() {
        ArrayList<Double> out = new ArrayList<>();
        for (Node node : network) //this is not going to be at the output layer and just that
            if ((node.connections.size() != 0 && node.nodesConnectedToThis != 0) || node.layer == 0 || node.last)
                node.engage();

        for (Node node : network.subList(network.size() - output, network.size()))
            out.add(node.output);


        return out;
    }

    public void passToInput(ArrayList<Double> x) {
        if (x.size() != input) {
            System.err.println("the input its different to the input that it should have");
            return;
        }
        for (int i = 0; i < input; i++)
            network.get(i).input = x.get(i);

    }


    public void clearNodes() {

        for (Node node : network) node.clear();

    }

    // maybe I should create a new connections
    public void mutate() {

        for (Node node : network) {
            if (node.nodesConnectedToThis > 0 || node.layer == 0) {
                node.changeWeights();
                node.changeBias();
            }

        }
        Node n = network.get(rnd.nextInt(network.size() - output));
        // this add
        if (rnd.nextDouble() < 0.1 && n.connections.size() < lengths.get(n.layer) && (n.nodesConnectedToThis > 0 || n.layer == 0)) {
            int min = 0;
            for (int i : lengths.subList(0, n.layer)) min += i;

            Node randomNode = network.get(min + lengths.get(n.layer) + rnd.nextInt(lengths.get(n.layer + 1)));

            randomNode.nodesConnectedToThis++;
            n.addNewConnection(
                    randomNode
            );


        }
        //this remove
        if (rnd.nextDouble() < 0.05 && n.connections.size() > 2 && (n.nodesConnectedToThis > 0 || n.layer== 0)) {
            Node randomNode = n.connections.get(rnd.nextInt(n.connections.size()));
            randomNode.nodesConnectedToThis--;
            n.connections.remove(rnd.nextInt(n.connections.size()));

        }

    }

    // I hate java

    public void copyOtherBrain(Brain otherBrain) {

        for (int i = 0; i < network.size(); i++) {
            network.get(i).connections = new ArrayList<>();
            network.get(i).weights=new ArrayList<>();
            Node node=otherBrain.network.get(i);



            for (int j = 0; j < otherBrain.network.get(i).connections.size(); j++) {
                network.get(i).weights.add(node.weights.get(j) );
                network.get(i).connections.add(network.get(node.connections.get(j).n));


            }

        }


    }

    public void show(Graphics2D g, Game screen) {
        g.setFont(myFont);
        // the separation that each node is going to have
        int separationLayer = screen.width / (hiddenLayers + 2);
        int separationNode = screen.height / lengthOfHiddenLayers / 3;
        for (Node node : network) {

            ArrayList<Node> connections = node.connections;
            ArrayList<Double> weights = node.weights;

            for (int i = 0; i < connections.size() && connections.size() != 0 && (node.nodesConnectedToThis != 0 || node.layer == 0); i++) {

                g.setStroke(new BasicStroke(Math.abs(weights.get(i).floatValue() * 2f)));
                g.setColor(Color.getHSBColor((float) (node.output * 210) + 150, 100, 50));
                g.drawLine(40 + node.layer * separationLayer, 40 + node.index * separationNode, 40 + connections.get(i).layer * separationLayer, 40 + 2 + connections.get(i).index * separationNode);

            }
            g.setColor(Color.getHSBColor((float) (node.output * 210) + 150, 100, 50));
            if (node.last) {
                g.setColor(Color.getHSBColor(242, 100, (float) (50 * node.output)));
            }
            //just show the node
            g.fillArc(40 + node.layer * separationLayer, 40 + node.index * separationNode, 5, 5, 5, 360);
            g.setColor(Color.black);
            // the stroke
            g.drawArc(40 + node.layer * separationLayer, 40 + node.index * separationNode, 5, 5, 5, 360);

            //and then the name
            g.drawString(node.name, node.layer * separationLayer + 30, node.index * separationNode + 30);


        }
    }


}
