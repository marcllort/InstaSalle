package Json;

public class Connect {

    private int to;
    private String name;
    private int cost;

    public int getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Connect(int to, String name, int cost) {
        this.to = to;
        this.name = name;
        this.cost = cost;
    }
}
