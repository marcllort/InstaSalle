package Funcio1;

import Json.Connect;
import Json.Node;

import java.util.ArrayList;

import static java.lang.Math.round;

public class Greedy {

    private Node[] nodes;
    private Solution s = new Solution(0, 0);
    private boolean done = false;
    private int distance = 0;


    public Greedy(Node[] nodes2) {
        nodes = nodes2;
    }


    public Solution greedy(int[] origen, int[] dest, int mode, boolean out) {

        long start = System.currentTimeMillis();


        done = false;



        Node best1 = new Node(-1, 0, null);

        for (int or : origen) {
            if (best1.getReliability() < nodes[or - 1].getReliability()) {
                best1 = nodes[or - 1];
            }
        }



        s.path.add(best1);
        s.reliability = best1.getReliability();


        for (int i = 0; i < nodes.length && !done; i++) {
            ArrayList<Connect> candidates = s.path.get(s.path.size() - 1).getConnectsTo();

            int candidate = select_candidate(candidates, dest, mode);
            if (promising(s, nodes[candidate - 1])) {
                addCandidate(candidate);
            }

        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.path.size(); i++) {
            sb.append(s.path.get(i).getId());
            sb.append(" ");
        }
        String solucio = sb.toString();
        if(out) {
            if (mode == 0) {
                System.out.println("G:   Camí Ràpid trobat en " + elapsedTimeMillis + "ms, amb length " + s.lenght + " : " + solucio);
            } else {
                double round = Math.round(s.reliability * 100.0) / 100.0;
                System.out.println("G:   Camí Fiable trobat en " + elapsedTimeMillis + "ms, amb reliability " + round + " : " + solucio);
            }

        }
        return s;
    }

    private void addCandidate(int candidate) {
        s.path.add(nodes[candidate - 1]);
        s.reliability = s.reliability + nodes[candidate - 1].getReliability();
        s.lenght = s.lenght + distance;
    }

    private int select_candidate(ArrayList<Connect> candidates, int[] desti, int mode) {

        switch (mode) {
            case 0:
                Connect best = new Connect(-1, "-1", 1000000);
                for (int dest : desti) {
                    for (int i = 0; i < candidates.size(); i++) {
                        if (candidates.get(i).getTo() == dest) {
                            done = true;
                            distance = candidates.get(i).getCost();

                            return nodes[candidates.get(i).getTo() - 1].getId();
                        } else {
                            if (best.getCost() > candidates.get(i).getCost() && promising(s,nodes[candidates.get(i).getTo()-1])) {
                                best.setCost(candidates.get(i).getCost());
                                best.setTo(candidates.get(i).getTo());
                                distance = candidates.get(i).getCost();
                            }
                        }
                    }
                }
                return best.getTo();

            case 1:
                Node best1 = new Node(-1, 0, null);


                for (int i = 0; i < candidates.size(); i++) {
                    for (int dest : desti) {
                        if (candidates.get(i).getTo() == dest) {
                            done = true;
                            distance = candidates.get(i).getCost();

                            return nodes[candidates.get(i).getTo() - 1].getId();
                        } else {
                            if (best1.getReliability() < nodes[candidates.get(i).getTo() - 1].getReliability() && promising(s,nodes[candidates.get(i).getTo()-1])) {
                                best1.setId(nodes[candidates.get(i).getTo() - 1].getId());
                                best1.setReliability(nodes[candidates.get(i).getTo() - 1].getReliability());
                                distance = candidates.get(i).getCost();
                            }
                        }
                    }
                }
                return best1.getId();

            default:
                return -1;
        }


    }


    private boolean promising(Solution s, Node n) {
        for (int i = 0; i < s.path.size(); i++) {
            if (s.path.get(i).getId() == n.getId()) {
                return false;
            }
        }
        return true;
    }

}
