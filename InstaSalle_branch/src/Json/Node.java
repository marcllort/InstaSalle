package Json;

import java.util.ArrayList;

public class Node {


    private int id;
    private double reliability;
    private ArrayList<Connect> connectsTo;

    public int getId() {
        return id;
    }

    public double getReliability() {
        return reliability;
    }

    public ArrayList<Connect> getConnectsTo() {
        return connectsTo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public void setConnectsTo(ArrayList<Connect> connectsTo) {
        this.connectsTo = connectsTo;
    }

    public Node(int id, double reliability, ArrayList<Connect> connectsTo) {
        this.id = id;
        this.reliability = reliability;
        this.connectsTo = connectsTo;
    }
}
