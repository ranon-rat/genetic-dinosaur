import java.util.ArrayList;
import java.util.Random;

public class Node {
    String name;
    public boolean enable = false;
    public boolean last;
    public double input = 0;
    public double output = 0;
    public double bias = 1;
    public int nodesConnectedToThis = 0;
    public int layer;
    public int index;
    public int pos;// number in the neural network

    ArrayList<Node> connections = new ArrayList<>();// this works as a pointer
    ArrayList<Double> weights = new ArrayList<>();
    private final Random rnd = new Random();

    Node(int layer, int index, int pos, boolean last, String name) {
        this.index = index;
        this.layer = layer;
        this.pos = pos;
        this.name = name;
        this.last = last;

    }

    // It's just for try to reduce the output to a range of 1 and 0
    double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -4.9*x));
    }

    // I just clear the output and the input
    void clear() {
        this.output = 0;
        this.input = 0;
    }


    void engage() {
        output=input;
        if(layer!=0)
            output = sigmoid(input + bias);

        //yeah, it works I think
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).connections.size() != 0 || connections.get(i).last)
                connections.get(i).input += output * weights.get(i);
        }

    }

    // I will save this as a reference to a Node
    void addNewConnection(Node newCon) {

        if (!connections.contains(newCon)) {
            Random rnd = new Random();
            connections.add(newCon);
            weights.add(rnd.nextDouble());
        }


    }

    void changeBias() {


        if (rnd.nextDouble() < 0.01)//10% of probability of change the bias completely
            bias = rnd.nextDouble() + rnd.nextDouble() * -1;
        else
            bias += rnd.nextGaussian();
        bias%=2;

    }

    void changeWeights() {
        Random rnd = new Random();
        for (int i = 0; i < weights.size(); i++) {
            if (rnd.nextDouble() < 0.01)
                weights.set(i, rnd.nextDouble());
            else
                weights.set(i, weights.get(i) + rnd.nextGaussian());

         weights.set(i, weights.get(i)%2);
        }
    }


}