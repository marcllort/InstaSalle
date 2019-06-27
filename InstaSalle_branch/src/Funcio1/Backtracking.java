package Funcio1;

import Json.Connect;
import Json.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class Backtracking {

    private Node[] nodes;
    private Solution best;
    private String solucio;
    private ArrayList<Node> nodesL;
    private int i;

    public Backtracking(Node[] nodes) {
        this.nodes = nodes;
        i = 0;

        nodesL = new ArrayList<>(Arrays.asList(nodes));
    }

    private boolean promising(Solution s, Connect c, int mode, int[] desti) {

        switch (mode) {

            case 0:
                if (s.lenght + c.getCost() > best.lenght) {
                    return false;
                }
                break;

            case 1:
                double suma = s.lenght;
                for (Node n : nodesL) {
                    suma = suma + n.getReliability();
                }
                if (suma + nodes[c.getTo() - 1].getReliability() < best.reliability) {
                    return false;
                }
                break;
        }

        for (int i = 0; i < s.path.size(); i++) {
            if (s.path.get(i).getId() == c.getTo()) {
                return false;
            } else {
                nodesL.remove(s.path.get(i));
            }
        }
        return true;

    }


    private void isBest(Solution s, int mode) {

        if (mode == 0 && s.lenght < best.lenght) {
            best.setLenght(s.lenght);
            best.setReliability(s.reliability);
            best.setPath(s.path);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < best.path.size(); i++) {
                sb.append(best.path.get(i).getId());
                sb.append(" ");
            }

            solucio = sb.toString();

        } else if (mode == 1 && s.reliability > best.reliability) {
            best.setLenght(s.lenght);
            best.setReliability(s.reliability);
            best.setPath(s.path);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < best.path.size(); i++) {
                sb.append(best.path.get(i).getId());
                sb.append(" ");
            }

            solucio = sb.toString();
        }

    }


    private void backtrack(Solution s, int[] desti, int mode) {
        Solution ss = s;
        i++;
        boolean isSol = false;
        for (int dest : desti) {
            if (s.path.get(ss.path.size() - 1).getId() == dest) {
                isBest(ss, mode);
                isSol = true;
            }
        }
        if (!isSol) {
            ArrayList<Connect> connections = ss.path.get(ss.path.size() - 1).getConnectsTo();

            for (Connect c : connections) {

                boolean prom = promising(ss, c, mode, desti);
                if (prom) {

                    //System.out.println("BEST: " + best.lenght + " Suma: " + sss + " PROMISING: "+prom+ " Mode: " + mode);
                    ss.path.add(nodes[c.getTo() - 1]);
                    ss.lenght = ss.lenght + c.getCost();
                    ss.reliability = ss.reliability + nodes[c.getTo() - 1].getReliability();

                    backtrack(ss, desti, mode);

                    ss.path.remove(nodes[c.getTo() - 1]);
                    ss.lenght = ss.lenght - c.getCost();
                    ss.reliability = ss.reliability - nodes[c.getTo() - 1].getReliability();

                }
            }

        }


    }


    public void backtracking(int[] origen, int[] dest, int mode, boolean greedy, Solution greedySol) {
        nodesL = new ArrayList<>(Arrays.asList(nodes));

        switch (mode) {

            case 0:
                Solution sol = new Solution(0, 0);
                if (!greedy) {
                    best = new Solution(100000000, 0);
                } else {
                    best = greedySol;
                }

                long start = System.currentTimeMillis();

                for (int or : origen) {
                    sol = new Solution();
                    sol.path.add(nodes[or - 1]);
                    //best.path.add(nodes[or - 1]);
                    backtrack(sol, dest, mode);
                }
                long elapsedTimeMillis = System.currentTimeMillis() - start;

                if (!greedy) {
                    System.out.println("B:   Camí Ràpid trobat en " + elapsedTimeMillis + "ms, amb length " + best.lenght + " : " + solucio);
                } else {
                    System.out.println("BG:  Camí Ràpid trobat en " + elapsedTimeMillis + "ms, amb length " + best.lenght + " : " + solucio);
                }
                System.out.println("Nombre total d'iteracions: " + i);
                break;

            case 1:


                if (!greedy) {
                    best = new Solution(0, 0);
                } else {
                    best = greedySol;
                }

                long start2 = System.currentTimeMillis();

                for (int or : origen) {
                    sol = new Solution(0, nodes[or - 1].getReliability());
                    sol.path.add(nodes[or - 1]);
                    best.path.add(nodes[or - 1]);


                    backtrack(sol, dest, mode);
                }

                long elapsedTimeMillis2 = System.currentTimeMillis() - start2;

                double round = Math.round(best.reliability * 100.0) / 100.0;
                if (!greedy) {
                    System.out.println("B:   Camí Fiable trobat en " + elapsedTimeMillis2 + "ms, amb reliability " + round + " : " + solucio);
                } else {
                    System.out.println("BG:  Camí Fiable trobat en " + elapsedTimeMillis2 + "ms, amb reliability " + round + " : " + solucio);
                }
                System.out.println("Nombre total d'iteracions: " + i);


                break;
        }
    }
}

