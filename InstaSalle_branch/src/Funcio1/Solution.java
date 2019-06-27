package Funcio1;

import Json.Node;

import java.util.ArrayList;

public class Solution {

    ArrayList<Node> path = new ArrayList<>();
    int lenght;
    double reliability;


    public Solution() {
    }

    public Solution(int lenght, double reliability) {
        this.lenght = lenght;
        this.reliability = reliability;
    }

    public Solution(int lenght, double reliability, Node node) {
        this.lenght = lenght;
        this.reliability = reliability;
        path.add(node);
    }

    public Solution(ArrayList<Node> path, int lenght, double reliability) {
        this.path = path;
        this.lenght = lenght;
        this.reliability = reliability;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }
}
