import Json.Point;

import java.util.Scanner;


public class Menu {

    int opcio;
    int ordre;
    Scanner in;


    public Menu() {
        opcio = 0;
        in = new Scanner(System.in);
    }

    public void printaMenu() {
        System.out.println("Quin mètode d'ordenació vols seguir?");
        System.out.println("    1- Merge Sort");
        System.out.println("    2- Quick Sort");
        System.out.println("    3- Selection Sort");
        System.out.println("    4- Radix Sort");
        System.out.println("    5- Sortir");
    }

    public int llegeixOpcio() {
        try {
            this.opcio = in.nextInt();
            while (opcio < 0 || opcio > 5) {
                System.out.println("Opció Incorrecte !!");
                printaMenu();

                llegeixOpcio();
            }
        } catch (Exception e) {
            System.out.println("Opcio incorrecte");
        }

        return opcio;
    }

    public void printaOrdre() {

        System.out.println("Segons que vols ordenar?");
        System.out.println("    1- Temporalitat");
        System.out.println("    2- Location");
        System.out.println("    3- Combinació");

    }

    public int llegeixOrdre() {
        try {
            ordre = in.nextInt();
            while (ordre < 0 || ordre > 3) {
                System.out.println("Opció Incorrecte !!");
                printaOrdre();
                llegeixOrdre();
            }
            return ordre;

        } catch (Exception e) {
            System.out.println("Opcio Incorrecte");
            return 0;
        }

    }

    public Point llegeixPoint() {

        int x, y;
        System.out.println("A quines coordenades et trobes?");
        System.out.println("Longitud?");
        x = in.nextInt();
        System.out.println("Latitud?");
        y = in.nextInt();
        Point p = new Point(x, y);
        return p;

    }


}
