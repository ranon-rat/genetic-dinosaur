import java.util.ArrayList;
import java.util.Random;

public class Node {
    String name ;
    boolean last = false;
    double input = 0;
    double output = 0;
    double bias = 1;

    int nodesConnectedToThis = 0;
    int layer;
    int index;
    int n;

    ArrayList<Node> connections = new ArrayList<>();// this works as a pointer
    ArrayList<Double> weights = new ArrayList<>();
    Random rnd = new Random();

    Node(int layer, int index, int n, String name) {
        this.index = index;
        this.layer = layer;
        this.n = n;
        this.name = name;

    }

    double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }


    void clear() {
        this.output = 0;
        this.input = 0;
    }

    // I will save this as a pointer
    void addNewConnection(Node newCon) {

        if (!connections.contains(newCon)) {
            Random rnd = new Random();
            connections.add(newCon);
            weights.add(rnd.nextDouble());
        }


    }

    void changeBias() {


        if (rnd.nextDouble() < 0.10)
            bias = rnd.nextDouble() + rnd.nextDouble() * -1;
        else
            bias += rnd.nextGaussian();
        if (bias > 1)
            bias = 0;
    }

    void engage() {


        if ((nodesConnectedToThis == 0 || connections.size() == 0) && (layer != 0 && !last)) return;
        if (layer != 0)
            output = sigmoid(input + bias);

        //yeah, it works I think
        for (int i = 0; i < connections.size(); i++) {

            if (connections.get(i).connections.size() != 0 || connections.get(i).last)
                connections.get(i).input += output * weights.get(i);
        }

    }

    void changeWeights() {
        Random rnd = new Random();
        for (int i = 0; i < weights.size(); i++) {
            if (rnd.nextDouble() < 0.10)
                weights.set(i, rnd.nextDouble() + rnd.nextDouble() * -1);
            else
                weights.set(i, weights.get(i) + rnd.nextGaussian());

            if (weights.get(i) > 1)
                weights.set(i, 1d);
            else if (weights.get(i) < -1)
                weights.set(i, -1d);
        }
    }


}
