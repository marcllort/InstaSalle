package Json;

import java.util.ArrayList;


public class User {


    private String username;
    private int followers;
    private int follows;
    private ArrayList<Connections> connections;
    private ArrayList<Posts> posts;


    public int getFollowers() {
        return followers;
    }

    public int getFollows() {
        return follows;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Connections> getConnections() {
        return connections;
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }


}
