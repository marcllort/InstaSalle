package Funcio2;

import Funcio1.Solution;
import Json.Server;
import Json.User;

import java.util.ArrayList;


public class Backtracking2 {


    private User[] users;
    private Server[] servers;
    private Solution2 s ;


    public Backtracking2(User[] users, Server[] servers) {
        this.users = users;
        this.servers = servers;
        s = new Solution2(servers.length);
    }


    public Solution2 backtrack(Solution2 s, Solution2 best, int marge) {
        Solution2 ss = s;


        if (casTrivial(ss)) {
            isBest(best, ss);
            return best;
        } else {

            for (User u : users) {

                if (promising(ss, u, best, marge)) {
                    for (int i = 0; i < servers.length; i++) {
                        ss.users[i].add(u);
                        ss.activitat[i] = ss.activitat[i] + u.getActivity();
                        double dist = Math.floor(calculaDistancia(servers[i], u) * 100) / 100;
                        ss.distancia[i] = ss.distancia[i] + dist;


                        backtrack(ss, best, marge);

                        ss.users[i].remove(u);
                        ss.activitat[i] = ss.activitat[i] - u.getActivity();

                        ss.distancia[i] = ss.distancia[i] - dist;

                    }
                }
            }

        }

        return best;
    }

    private boolean promising(Solution2 ss, User user, Solution2 best, int marge) {
        double min = 100000, max = 0;
        for (int i = 0; i < servers.length; i++) {
            if (min > ss.activitat[i]) {
                min = ss.activitat[i];
            }
            if (max < ss.activitat[i]) {
                max = ss.activitat[i];
            }

        }
        double min2 = 100000, max2 = 0;

        for (int i = 0; i < servers.length; i++) {
            if (min2 > best.activitat[i]) {
                min2 = best.activitat[i];
            }
            if (max2 < best.activitat[i]) {
                max2 = best.activitat[i];
            }
        }

        //System.out.println(max+" -- "+ min+">>false>>"+max2+" -- "+min2);
        if (max - min > best.resta + marge) {
            //System.out.println("dale");
            return false;
        }

        for (ArrayList<User> sol : ss.users) {
            for (User u : sol) {
                if (u.getUsername().equals(user.getUsername())) {
                    return false;

                }
            }


        }


        return true;
    }

    private void isBest(Solution2 best, Solution2 s) {
        double min = 10000, max = 0;
        for (int i = 0; i < servers.length; i++) {
            if (min > s.activitat[i]) {
                min = s.activitat[i];
            }
            if (max < s.activitat[i]) {
                max = s.activitat[i];
            }
        }
        s.resta = max - min;

        double distancia1 = 0;
        for (double dist : s.distancia) {
            distancia1 = distancia1 + dist;
        }
        double distancia2 = 0;
        for (double dist : best.distancia) {
            distancia2 = distancia2 + dist;
        }
        //System.out.println(best.resta + "------------------------------------- " + s.resta);
        if (best.resta >= s.resta && distancia2 >= distancia1 || best.resta > s.resta || best.resta == 0) {
            //if (best.resta >= s.resta) {
            best.setActivitat(s.activitat.clone());
            best.setDistancia(s.distancia.clone());
            best.resta = s.resta;
            best.users = s.users.clone();
            //System.out.println("Nou best");
        }
    }

    private boolean casTrivial(Solution2 s) {
        int suma = 0;
        for (int i = 0; i < servers.length; i++) {
            suma = suma + s.users[i].size();
        }
        //System.out.println(suma);
        if (suma != users.length) {
            //System.out.println("false");
            return false;
        }
        return true;
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


    public Solution2 backtracker(Solution2 s, Solution2 best, int marge) {

        long start = System.currentTimeMillis();

        Solution2 besst = backtrack(s, best, marge);
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        System.out.println("B:  Ha tardat " + elapsedTimeMillis + "ms");
        return besst;
    }


}
