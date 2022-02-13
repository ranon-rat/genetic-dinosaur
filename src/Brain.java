import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Brain implements Cloneable {
    ArrayList<ArrayList<Node>> network = new ArrayList<>();
    int layers = 5;// 3 hidden layers
    int lengthOfHiddenLayers = 8;
    int output = 3;// length of output nodes


    Font myFont = new Font ("Courier New", 1, 5);
    /*
    0 jump
    0 big jump
    0 duck
    */
    int input = 6;// length of input nodes

    /*
    |width of obstacle         0| ---> 0 ---> 0 ---->\
|   |distance of next obstacle 0| ---> 0 ---> 0 ----->\--->small jump
    |height of obstacle        0| ---> 0 ---> 0 ------>\--> big jump
    |y position of obstacle    0| ---> 0 ---> 0 ------>/--> duck
    |speed                     0| ---> 0 ---> 0 ----->/
    |player y pos              0| ---> 0 ---> 0 ---->/
    */

    Brain() {
        Random rnd = new Random();

        for (var x = 0; x < layers; x++)
            network.add(new ArrayList<>());


        //this will create a neural network , then I need to connect the nodes and all that stuff
        for (var y = 0; y < input; y++)
            network.get(0).add(new Node(0, y));

        for (var x = 1; x < layers - 1; x++)
            for (var y = 0; y < lengthOfHiddenLayers; y++)
                network.get(x).add(new Node(x, y));

        for (var y = 0; y < output; y++)
            network.get(layers - 1).add(new Node(layers - 1, y));

        //this should generate some connections
        for (var x = 0; x < network.size() - 1; x++) //this is not going to be at the output layer and just that
            for (var y = 0; y < network.get(x).size(); y++) {
                //I hate myself for writing this
                network.get(x).get(y).addNewConnection(network.get(x + 1).get(rnd.nextInt(network.get(x + 1).size() )));
                network.get(x).get(y).changeWeights();
                network.get(x).get(y).changeBias();
            }

    }
    public ArrayList<Double> result(){
        ArrayList<Double> output=new ArrayList<>();
        for (ArrayList<Node> nodes:network) //this is not going to be at the output layer and just that
            for (Node node:nodes) {
                node.engage();

            }
        for(Node node:network.get(layers-1))
            output.add(node.output);

        return output;


    }
    public void passToInput(ArrayList<Double> x){
        if(x.size()!=input){
            System.err.println("the input its different to the input that it should have");
            return;
        }
        for(int i=0;i<input;i++)
            network.get(0).get(i).output=x.get(i);

    }
    public void clearNodes() {
        for (ArrayList<Node> nodes : network)
            for (Node node : nodes) node.clear();

    }

    // I hate java
    // execute this function after clone this object
    public void quitPointers() {
        for (ArrayList<Node> nodes : network) //this is not going to be at the output layer and just that
            for (Node node : nodes)

                for (Node conNode : node.connections) {
                    node.connections.remove(conNode);
                    node.addNewConnection(conNode);


                }


    }

    public void show(Graphics2D g, Game sc) {
        g.setFont(myFont);
        var width = sc.width - 20;
        var height = sc.height - 20;
        var separationWidth = width / network.size();
        var separationHeight= height/lengthOfHiddenLayers/3;
        for (ArrayList<Node> nodes : network) {

            for (Node node : nodes) {
                ArrayList<Node> connections=node.connections;
                ArrayList<Double> weights=node.weights;
                for(int i=0;i<connections.size();i++) {
                    g.setStroke(new BasicStroke(Math.abs (weights.get(i).floatValue()*1.5f)));
                    g.setColor(Color.getHSBColor((float) (node.output * 360), 100, 50));
                    g.drawLine(10+node.layer * separationWidth, 10 + node.index * separationHeight, 10+connections.get(i).layer * separationWidth, 10 + connections.get(i).index * separationHeight);
                }
                g.setColor(Color.blue);
                g.setStroke(new BasicStroke(1));
                g.drawArc(10+node.layer * separationWidth, 10 + node.index * separationHeight, 5, 5, 5, 360);
                g.setColor(Color.black);
                g.drawString((""+node.output),node.layer*separationWidth,node.index*separationHeight);


            }
        }

    }

    public Object clone() throws CloneNotSupportedException {
        clearNodes();
        return super.clone();
    }

}
