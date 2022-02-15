import java.util.ArrayList;
import java.util.Random;

public class Node  {
    String name;
    public boolean enable = false;
    public boolean last;
    public float input = 0;
    public float output = 0;
    public float bias = 1;
    public int nodesConnectedToThis = 0;
    public int layer;
    public int index;
    public int pos;// number in the neural network

    ArrayList<Node> connections = new ArrayList<>();// this works as a pointer
    ArrayList<Float> weights = new ArrayList<>();
    private final Random rnd = new Random();

    Node(int layer, int index, int pos, boolean last, String name) {
        this.index = index;
        this.layer = layer;
        this.pos = pos;
        this.name = name;
        this.last = last;

    }

    // It's just for try to reduce the output to a range of 1 and 0
   float sigmoid(float x) {
        return(float)( 1 / (1 + Math.exp(-x)));
    }

    // I just clear the output and the input
    void clear() {
        this.output = 0;
        this.input = 0;
    }


    void engage() {
        output = input;

        if (layer != 0 )
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

            connections.add(newCon);
            weights.add((float)(Math.random()+(Math.random() * -1)));
        }


    }

    void changeBias() {


        if (rnd.nextDouble() < 0.2)//10% of probability of change the bias completely
            bias =(float) (Math.random()+(Math.random() * -1));
        else
            bias += rnd.nextGaussian();
        bias%=2;


    }

    void changeWeights() {

        for (int i = 0; i < weights.size(); i++) {
            if (rnd.nextDouble() < 0.3)
                weights.set(i,(float)( Math.random() + Math.random() * -1));
            else
                weights.set(i,(float) (weights.get(i) + rnd.nextGaussian()));
            weights.set(i,weights.get(i)%1);


        }
    }



}