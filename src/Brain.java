import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Brain {

    ArrayList<Node> network = new ArrayList<>();// simple neural network
    ArrayList<Integer> lengths = new ArrayList<>();// this is for know the length of each layer
    ArrayList<Color> colors = new ArrayList<>();

    Font myFont = new Font("Courier New", Font.BOLD, 8);

    Random rnd = new Random();

    int hiddenLayers = 2,// 3 hidden layers
            lengthOfHiddenLayers = 3,
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


        // first I add the layers

        Layers[] layers = {new Layers(1, 0, input),
                new Layers(hiddenLayers + 1, 1, lengthOfHiddenLayers),
                new Layers(hiddenLayers + 2, hiddenLayers + 1, output)};

        int min = 0;
        for (Layers l : layers) {
            for (int layer = l.start; layer < l.loop; layer++) {
                for (int index = 0; index < l.length; index++) {
                    network.add(new Node(layer, index, min + index, layer == hiddenLayers + 1, "" + index));


                }
                min += l.length;
                lengths.add(l.length);

            }

        }


        //this should generate some connections


        for (Node input : network.subList(0, input)) {
            input.enable = true;
            connectNodeToEnd(input.pos);
        }


    }

    //im using this for trying to connect the nodes to the end and for not having some trashy connections
    private void connectNodeToEnd(int start) {

        int min = 0;
        int pos = start;
        for (int i : lengths.subList(0, network.get(pos).layer)) min += i;

        // 0->1->2->3
        //(0,1)->(1,7)->(2,3)->(3,5)->(4,1)
        while (!network.get(pos).last) {

            int layer = network.get(pos).layer;
            // I get a random node from the next layer
            Node randomNode = network.get(min + lengths.get(layer) + rnd.nextInt(lengths.get(layer + 1)));
            //then I add a connection for use it
            network.get(pos).addNewConnection(randomNode);
            randomNode.nodesConnectedToThis++;
            if (randomNode.enable) break;
            randomNode.enable = true;
            min += lengths.get(layer);
            pos = randomNode.pos;


        }


    }


    // you will get the result after finishing the operation
    public ArrayList<Float> result() {
        ArrayList<Float> out = new ArrayList<>();
        for (Node node : network) //this is not going to be at the output layer and just that
            if ((node.connections.size() != 0 && node.nodesConnectedToThis != 0) || node.layer == 0 || node.enable)
                node.engage();


        for (Node node : network.subList(network.size() - output, network.size()))
            out.add(node.output);


        return out;
    }

    public void passToInput(ArrayList<Float> x) {
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
        Node node = network.get(rnd.nextInt(network.size() - output));
        // this add
        if (Math.random() < 0.2 && node.connections.size() < lengths.get(node.layer)) {
            connectNodeToEnd(node.pos);
        }
        //this remove
        if (Math.random() < 0.01 && node.connections.size() > 2) {
            int pos=rnd.nextInt(node.connections.size());
            Node randomNode = node.connections.get(pos);
            randomNode.nodesConnectedToThis--;
            randomNode.enable=randomNode.nodesConnectedToThis>0;

            node.weights.remove(pos);
            node.connections.remove(pos);

        }

    }

    // I hate java

    public void copyOtherBrain(Brain otherBrain) {
     
        for (int n = 0; n < network.size(); n++) {
            network.get(n).weights = new ArrayList<>();
            network.get(n).connections = new ArrayList<>();
            network.get(n).enable=otherBrain.network.get(n).enable;
            network.get(n).bias=otherBrain.network.get(n).bias;
            Node node = otherBrain.network.get(n);

            for (int c = 0; c < node.connections.size(); c++) {
                network.get(n).connections.add(network.get(node.connections.get(c).pos));
                network.get(n).weights.add(node.weights.get(c));

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

            for (int i = 0; i < connections.size() && connections.size() != 0 && (node.nodesConnectedToThis != 0 || node.layer == 0); i++) {


                g.setColor(Color.blue);
                g.drawLine(40 + node.layer * separationLayer, 40+4 + node.index * separationNode, 40 + connections.get(i).layer * separationLayer, 40+4  + connections.get(i).index * separationNode);

            }
            g.setColor(Color.getHSBColor( (node.output * 210) + 150, 100, 50));

            g.setColor(Color.getHSBColor(242, 100,  (50 * node.output)));

            //just show the node
            g.fillArc(40 + node.layer * separationLayer, 40 + node.index * separationNode, 5, 5, 5, 360);
            g.setColor(Color.black);
            // the stroke
            g.drawArc(40 + node.layer * separationLayer, 40 + node.index * separationNode, 5, 5, 5, 360);

            //and then the name
            g.drawString(node.name, node.layer * separationLayer + 30, node.index * separationNode + 30);

            g.drawString(node.output + "", node.layer * separationLayer + 50, node.index * separationNode + 40);
        }
    }


}