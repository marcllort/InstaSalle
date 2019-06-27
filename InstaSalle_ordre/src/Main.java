import Json.LectorJson;
import Json.Point;
import Json.Posts;
import Json.User;
import javafx.geometry.Pos;

import java.util.ArrayList;



public class Main {

    public static void main(String[] args) {

        int opcio = 0;
        int ordre;
        Point point;
        Ordenador orde;

        User[] users = LectorJson.llegeixUser();
        ArrayList<Posts> posts = new ArrayList<>();
        Menu menu = new Menu();

//Preparo el array de posts i li omplo a tots els posts el username del user que l'ha publicat per facilitar l'ordenació de prioritats combinades
        for (User u : users) {
            for (Posts p :u.getPosts()){
                p.setUser(u.getUsername());
            }
            posts.addAll(u.getPosts());
        }
        System.out.println("Posts");
        for (Posts post : posts) {
            post.setPoint(post.getLocation().get(0), post.getLocation().get(1));
        }
        System.out.println("punts");

        orde = new Ordenador(posts, users);
        if(args.length == 0) {
            while (opcio != 5) {

                menu.printaMenu();
                opcio = menu.llegeixOpcio();

                if (opcio != 5) {
                    menu.printaOrdre();
                    ordre = menu.llegeixOrdre();

                    if (ordre == 2) {
                        point = menu.llegeixPoint();
                    } else {
                        point = new Point(0, 0);
                    }

                    orde.ordena(opcio, ordre, point);
                }

            }
        }else {
            try {
                int op = Integer.parseInt(args[0]);

                    ordre = Integer.parseInt(args[1]);

                    if (ordre == 2) {
                        double x = Double.parseDouble(args[2]);
                        double y = Double.parseDouble(args[3]);
                        point = new Point(x, y);
                    } else {
                        point = new Point(0, 0);
                    }

                    orde.ordena(op, ordre, point);
            }catch (Exception e ){
                System.out.println("Arguments incorrectes!");
                System.out.println( "Argument 1 ");
                System.out.println("    1- Merge Sort");
                System.out.println("    2- Quick Sort");
                System.out.println("    3- Selection Sort");
                System.out.println("    4- Radix Sort");
                System.out.println("\nArgument 2");
                System.out.println("    1- Temporalitat");
                System.out.println("    2- Location");
                System.out.println(" Arguments 3 i 4 : Coordenades x i y");

                System.out.println("    3- Combinació");
                System.out.println();

            }


        }
        /*
        for(Posts i:posts){
            System.out.print(i.getPublished()+ " "+ "ID: "+ i.getId()+ " Likes: " + i.getLiked_by().size()+ " Comments: "+ i.getCommented_by().size()+"\n");
        }*/
    }

}