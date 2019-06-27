package Funcio2;


import Json.Server;
import Json.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BranchBound2 {


    private User[] users;
    private Server[] servers;


    public BranchBound2(User[] users, Server[] servers) {
        this.users = users;
        this.servers = servers;
    }

    public Solution2 branchandboundeame(Solution2 bestt, int marge, boolean greedy) {
        long start = System.currentTimeMillis();
        double min2 = 100000, max2 = 10;

        if (greedy) {
            for (int i = 0; i < servers.length; i++) {
                if (min2 > bestt.activitat[i]) {
                    min2 = bestt.activitat[i];
                }
                if (max2 < bestt.activitat[i]) {
                    max2 = bestt.activitat[i];
                }
            }
            double distancia2 = 0;
            for (double dist : bestt.distancia) {
                distancia2 = distancia2 + dist;
            }
            bestt.dist = distancia2;
            bestt.resta = max2 - min2;
        }

        Solution2 best = bestt;
        Solution2 s = new Solution2(servers.length);

        ArrayList<Solution2> arraySolucions = new ArrayList<>();
        ArrayList<Solution2> options;


        for (int j = 0; j < servers.length; j++) {
            arraySolucions.add(new Solution2(servers.length, j, users[0], servers[j]));
        }


        while (arraySolucions.size() != 0) {

            //Vui minimitzar la diferencia de usuaris entre els diferents servidors

            int suma = 0;
            for (int i = 0; i < servers.length; i++) {
                suma = suma + s.users[i].size();
            }

            if (suma == users.length) {
                Collections.sort(arraySolucions, new Comparator<Solution2>() {
                    public int compare(Solution2 s1, Solution2 s2) {
                        return Double.compare(s1.resta, s2.resta);
                    }
                });
            }

            s.setDistancia(arraySolucions.get(0).getDistancia().clone());
            s.setActivitat(arraySolucions.get(0).getActivitat().clone());
            s.setUsers(arraySolucions.get(0).getUsers().clone());
            s.userI = arraySolucions.get(0).getUserI();
            arraySolucions.remove(0);

            //options = giveOptions(s);
            options = expand(s);

            for (Solution2 sol : options) {
                boolean isSol = found(sol, best, marge);
                if (isSol) {
                    isBest(best, sol);
                } else {
                    if (promising(sol, best, marge)) {
                        //System.out.println(sol.userI);
                        arraySolucions.add(new Solution2(sol.getUsers().clone(), sol.getActivitat().clone(), sol.getDistancia().clone(), servers.length, sol.userI));
                    }

                }
            }

        }


        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("BB:  Ha tardat " + elapsedTimeMillis + "ms");


        return best;
    }


    public ArrayList<Solution2> expand(Solution2 s) {
        ArrayList<Solution2> arrayOpcions = new ArrayList<>();

        for (int j = 0; j < servers.length; j++) {
            Solution2 s1 = new Solution2(s.getUsers().clone(), s.getActivitat().clone(), s.getDistancia().clone(), servers.length, s.userI + 1);
            //System.out.println("DESPRES"+s.userI);

            s1.users[j].add(users[s.userI]);
            s1.distancia[j] = s1.distancia[j] + calculaDistancia(servers[j], users[s.userI]);
            s1.activitat[j] = s1.activitat[j] + users[s.userI].getActivity();


            arrayOpcions.add(new Solution2(s1.getUsers().clone(), s1.getActivitat().clone(), s1.getDistancia().clone(), servers.length, s1.userI));
        }
        return arrayOpcions;
    }

    private ArrayList<User> giveOptions(Solution2 s) {
        ArrayList<User> userOp = new ArrayList<>(Arrays.asList(users));
        for (int j = 0; j < servers.length; j++) {
            for (User u : s.users[j]) {
                userOp.remove(u);
            }
        }
        return userOp;
    }


    private boolean promising(Solution2 ss, Solution2 best, int marge) {
        double min = 100000, max = 0;
        for (int i = 0; i < servers.length; i++) {
            if (min > ss.activitat[i]) {
                min = ss.activitat[i];
            }
            if (max < ss.activitat[i]) {
                max = ss.activitat[i];
            }

        }

        double distancia1 = 0;

        for (double dist : ss.distancia) {
            distancia1 = distancia1 + dist;
        }

        if (max - min > best.resta + marge) {
            return false;
        }
        if (max - min > best.resta && best.dist < distancia1) {
            return false;
        }


        return true;
    }


    private boolean found(Solution2 s, Solution2 best, int marge) {

        /*int suma = 0;
        for (int i = 0; i < servers.length; i++) {
            suma = suma + s.users[i].size();
        }

        if (suma != users.length) {
            return false;
        }
        return true;*/
        if (s.userI == users.length) {
            return true;
        }
        return false;
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
        double distancia1 = 0;
        for (double dist : s.distancia) {
            distancia1 = distancia1 + dist;
        }
        s.resta = max - min;


        if (best.resta >= s.resta && best.dist > distancia1 || best.resta > s.resta || best.resta == 0) {

            best.setActivitat(s.activitat.clone());
            best.setDistancia(s.distancia.clone());
            best.resta = s.resta;
            best.dist = distancia1;
            best.users = s.users.clone();
        }
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
