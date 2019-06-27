package Json;

import java.util.Vector;

public class Server {


    private int id;
    private String country;
    private Vector location;
    private int [] reachable_from;

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Vector getLocation() {
        return location;
    }

    public int [] getReachable_from() {
        return reachable_from;
    }
}
