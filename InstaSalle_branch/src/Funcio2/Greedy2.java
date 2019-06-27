package Funcio2;


import Json.Server;
import Json.User;


public class Greedy2 {


    private User[] users;
    private Server[] servers;
    private Solution2 s;
    private boolean done = false;
    private int next = -1;


    public Greedy2(User[] users, Server[] servers) {
        this.users = users;
        this.servers = servers;
        s = new Solution2(servers.length);
        //Solution2 best = new Solution2(0, 0);
    }

    public Solution2 greedy(int marge, boolean out) {

        long start = System.currentTimeMillis();
        done = false;
        double distancies[] = new double[servers.length];

        for (int i = 0; i < users.length && !done; i++) {

            User candidate = users[i];
            if (promising(s, candidate, distancies, marge)) {
                addCandidate(s, candidate, distancies);
            }

        }

        double min2 = 100000, max2 = 0;

        for (int i = 0; i < servers.length; i++) {
            if (min2 > s.activitat[i]) {
                min2 = s.activitat[i];
            }
            if (max2 < s.activitat[i]) {
                max2 = s.activitat[i];
            }
        }

        s.resta = max2 - min2;



        long elapsedTimeMillis = System.currentTimeMillis() - start;
        if (out) {
            System.out.println("G:  Ha tardat " + elapsedTimeMillis + "ms");
        }
        return s;
    }

    private void addCandidate(Solution2 s, User candidate, double distancies[]) {
        s.users[next].add(candidate);
        s.activitat[next] = s.activitat[next] + candidate.getActivity();
        s.distancia[next] = s.distancia[next] + distancies[next];

        //System.out.println("Afegeixo "+candidate.getUsername()+" a server "+(next+1));
    }

    private boolean promising(Solution2 s, User candidate, double distancies[], int marge) {

        double min = 1000000;
        for (Server ser : servers) {
            distancies[ser.getId() - 1] = Math.floor(calculaDistancia(ser, candidate) * 100) / 100;
        }


        if (equitivitat(s, marge)) {
            for (int i = 0; i < distancies.length; i++) {
                if (min > distancies[i]) {
                    min = distancies[i];
                    next = i;
                }
            }
        } else {
            serverMinim(s);
        }
        return true;
    }

    private void serverMinim(Solution2 s) {
        double min = s.activitat[0];
        next = 0;
        for (int i = 1; i < s.activitat.length; i++) {
            if (min > s.activitat[i]) {
                min = s.activitat[i];
                next = i;
            }
        }
        //System.out.println("Next server: " + next +" lenght: " + s.activitat.length);
    }

    private boolean equitivitat(Solution2 s, int marge) {

        for (int i = 1; i < s.activitat.length; i++) {
            if (s.activitat[i - 1] > s.activitat[i] + marge || s.activitat[i - 1] < s.activitat[i] - marge) {
                //System.out.println("No equitivitat");

                return false;
            }
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


}
