import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    double input = 0;
    double output = 0;
    double bias = 1;
    int layer;
    ArrayList<Node> connectedNodes = new ArrayList<>();// this works as a pointer
    List<Double> weightOfConnections;

    Node(int l){

        layer=l;
    }
    double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    void changeBias() {
        Random rnd = new Random();
        double rand2 = Math.random();
        if (rand2 < 0.10)
            bias = rnd.nextDouble() + rnd.nextDouble() * -1;
        else
            bias += rnd.nextGaussian();


    }

    // I will save this as a pointer
    void addNewConnection(Node newCon) {

        if (!connectedNodes.contains(newCon)) {
            Random rnd = new Random();
            connectedNodes.add(newCon);
            weightOfConnections.add(rnd.nextDouble() + rnd.nextDouble() * -1);
        }


    }

    void changeWeights() {
        Random rnd = new Random();
        for (int i = 0; i < weightOfConnections.size(); i++)
            if (Math.random() < 0.10)
                weightOfConnections.set(i, rnd.nextDouble() + rnd.nextDouble() * -1);
            else
                weightOfConnections.set(i, weightOfConnections.get(i) + rnd.nextGaussian());
    }

    void engage() {
        if (layer != 0) {
            output = sigmoid(input + bias);
        }
        //I think that it should work because im not declare a new node or something like that
        //I'm just using a variable another variable based in an object, so it should work I think ,but I don't really know
        for (int i = 0; i < connectedNodes.size(); i++) {

            connectedNodes.get(i).input += output * weightOfConnections.get(i);

        }
    }

}
