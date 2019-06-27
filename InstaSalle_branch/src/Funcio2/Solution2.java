package Funcio2;

import Json.Server;
import Json.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution2 {

    ArrayList<User>[] users;

    //ArrayList<ArrayList<User>> users;
    double[] activitat;
    double[] distancia;
    int userI;
    double resta;
    double dist;

    public double getResta() {
        return resta;
    }

    public double getDist() {
        return dist;
    }

    public Solution2(ArrayList<User>[] userss, double[] activitat, double[] distancia, int nservers, int ii) {

        users = (ArrayList<User>[]) new ArrayList[nservers];
        for (int i = 0; i < nservers; i++) {
            this.users[i] = (ArrayList<User>)userss[i].clone();
        }

        //this.users = users.clone();
        this.activitat = activitat.clone();
        this.distancia = distancia.clone();
        userI=ii;
    }

    public Solution2(int nservers) {
        users = (ArrayList<User>[]) new ArrayList[nservers];
        activitat = new double[nservers];
        distancia = new double[nservers];

        resta = 100000;
        this.dist=100000000;
        for (int i = 0; i < nservers; i++) {
            users[i] = new ArrayList<>();
        }
        userI=1;
    }

    public Solution2(int nservers, int ii,User us, Server server) {

        users = (ArrayList<User>[]) new ArrayList[nservers];
        activitat = new double[nservers];
        distancia = new double[nservers];

        for (int i = 0; i < nservers; i++) {
            users[i] = new ArrayList<>();
        }

        users[ii].add(us);
        activitat[ii]=us.getActivity();
        distancia[ii]=calculaDistancia(server, us);
        userI=1;
    }


    public ArrayList<User>[] getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User>[] users) {
        this.users = users;
    }

    public double[] getActivitat() {
        return activitat;
    }

    public void setActivitat(double[] activitat) {
        this.activitat = activitat;
    }

    public double[] getDistancia() {
        return distancia;
    }

    public void setDistancia(double[] distancia) {
        this.distancia = distancia;
    }

    public int getUserI() {
        return userI;
    }

    private double calculaDistancia(Server ser, User candidate) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians((double) ser.getLocation().get(0) - candidate.getPosts().get(0).getLocation().get(0));
        double lonDistance = Math.toRadians((double) ser.getLocation().get(1) - candidate.getPosts().get(0).getLocation().get(1));

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(candidate.getPosts().get(0).getLocation().get(0))) * Math.cos(Math.toRadians((double) ser.getLocation().get(0)))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters


        return Math.floor(distance * 100) / 100;
    }

}
