package Funcio1;

import Json.Connect;
import Json.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class BranchBound {

    private Node[] nodes;
    private ArrayList<Node> nodesL;
    private Solution best;
    private int i;


    public BranchBound(Node[] nodes) {
        this.nodes = nodes;
        nodesL = new ArrayList<>(Arrays.asList(nodes));
        i = 0;
    }

    private boolean promising(Solution s, Connect c, int mode) {
        //nodesL = new ArrayList<>(Arrays.asList(nodes));
        int sss = s.lenght + c.getCost();
        if (mode == 0 && sss > best.lenght) {
            return false;
        }
        for (int i = 0; i < s.path.size(); i++) {
            if (s.path.get(i).getId() == c.getTo()) {
                return false;
            } else {
                nodesL.remove(s.path.get(i));
            }
        }

        double suma = s.lenght;
        for (Node n : nodesL) {
            suma = suma + n.getReliability();
        }

        if (mode == 1 && suma + nodes[c.getTo() - 1].getReliability() < best.reliability) {
            return false;
        }


        return true;
    }

    public void branchBound(int[] origen, int[] desti, int mode, boolean greedy, Solution greedSol) {
        nodesL = new ArrayList<>(Arrays.asList(nodes));
        long start = System.currentTimeMillis();

        boolean done = false;

        Solution s = new Solution();


        if (!greedy) {
            best = new Solution(10000000, 0);
        } else {
            best = greedSol;
        }


        ArrayList<Solution> arraySolucions = new ArrayList<>();

        ArrayList<Connect> options;


        for (int or : origen) {
            arraySolucions.add(new Solution(0, nodes[or - 1].getReliability(), nodes[or - 1]));
        }


        arraySolucions.add(s);  // ON ORDENES????

        while (arraySolucions.size() != 0) {
            i++;

            s.path = (ArrayList<Node>) arraySolucions.get(0).path.clone();
            s.reliability = arraySolucions.get(0).reliability;
            s.lenght = arraySolucions.get(0).lenght;

            arraySolucions.remove(0);

            options = s.path.get(s.path.size() - 1).getConnectsTo();

            for (Connect option : options) {
                boolean isSol = false;
                for (int dest : desti) {
                    if (option.getTo() == dest && promising(s, option, mode)) {
                        if (isBest(best, option, s.lenght, s.reliability, mode)) {
                            s.path.add(nodes[option.getTo() - 1]);
                            best.path = (ArrayList<Node>) s.path.clone();
                            best.setReliability(s.reliability + nodes[option.getTo() - 1].getReliability());
                            best.setLenght(s.lenght + option.getCost());

                        }
                        isSol = true;
                    }
                }
                if (promising(s, option, mode) && !isSol) {
                    Solution s1 = new Solution();
                    s1.setLenght(s.lenght);
                    s1.setReliability(s.reliability);

                    s1.path = (ArrayList<Node>) s.path.clone();


                    s1.path.add(nodes[option.getTo() - 1]);
                    s1.lenght = s1.lenght + option.getCost();
                    s1.reliability = s1.reliability + nodes[option.getTo() - 1].getReliability();
                    arraySolucions.add(new Solution((ArrayList<Node>) s1.path.clone(), s1.lenght, s1.reliability));
                    if(mode == 0){

                        Collections.sort(arraySolucions, new Comparator<Solution>(){
                            public int compare(Solution s1, Solution s2) {
                                return Double.compare(s1.lenght,s2.lenght);
                            }
                        });
                    }else{

                        Collections.sort(arraySolucions, new Comparator<Solution>(){
                            public int compare(Solution s1, Solution s2) {
                                return Double.compare(s2.reliability,s1.reliability);
                            }
                        });


                    }
                }


            }
        }


        long elapsedTimeMillis = System.currentTimeMillis() - start;


        output(best, mode, elapsedTimeMillis, greedy);


    }


    private boolean isBest(Solution best, Connect option, int lenght, double rel, int mode) {


        switch (mode) {
            case 0:
                if (best.lenght > option.getCost() + lenght) {
                    return true;
                } else {
                    return false;
                }


            case 1:

                if (best.reliability < nodes[option.getTo() - 1].getReliability() + rel) {
                    return true;
                } else {
                    return false;
                }

            default:
                return false;
        }

    }


    private void output(Solution best, int mode, long elapsedTimeMillis, boolean greedy) {


        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < best.path.size(); i++) {
            sb.append(best.path.get(i).getId());
            sb.append(" ");
        }

        String solucio = sb.toString();


        switch (mode) {

            case 0:

                if (!greedy) {
                    System.out.println("BB:  Camí Ràpid trobat en " + elapsedTimeMillis + "ms, amb length " + best.lenght + " : " + solucio);
                } else {
                    System.out.println("BBG: Camí Ràpid trobat en " + elapsedTimeMillis + "ms, amb length " + best.lenght + " : " + solucio);
                }


                break;


            case 1:
                double round = Math.round(best.reliability * 100.0) / 100.0;

                if (!greedy) {
                    System.out.println("BB:  Camí Fiable trobat en " + elapsedTimeMillis + "ms, amb reliability " + round + " : " + solucio);
                } else {
                    System.out.println("BBG: Camí Fiable trobat en " + elapsedTimeMillis + "ms, amb reliability " + round + " : " + solucio);
                }


                break;

        }
        System.out.println("Nombre total d'iteracions: " + i);

    }

}
