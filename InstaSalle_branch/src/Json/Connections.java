package Json;


import java.math.BigInteger;

public class Connections {

    private String username;
    private BigInteger since;
    private int visits;
    private int likes;
    private int comments;
    private double order;

    public Connections() {

    }

    public void setOrder(double order) {
        this.order = order;
    }

    public double getOrder() {
        return order;
    }

    public String getUsername() {
        return username;
    }

    public BigInteger getSince() {
        return since;
    }

    public int getVisits() {
        return visits;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

}
