import Json.Connections;
import Json.Point;
import Json.Posts;
import Json.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.sql.Timestamp;

import static java.lang.Math.abs;


public class Ordenador {

    private static final double IMP_LIKEHOOD = 33 ; //La importància de cada part en %
    private static final double IMP_INTERACCIO = 33 ;
    private static final double IMP_TEMPORALITAT = 40;


    private ArrayList<Posts> posts, postsUsuari;
    private int opcio;
    private int ordre;
    private Point point;
    private User[] users;
    private QuickSort quickSort;
    private MergeSort mergeSort;
    private SelectionSort selectionSort;
    private RadixSort radixSort;
    private Scanner in;


    public Ordenador(ArrayList<Posts> array, User[] users) {   //Arreglar tema constructor i passar arraylist posts

        quickSort = new QuickSort();
        mergeSort = new MergeSort();
        selectionSort = new SelectionSort();
        radixSort = new RadixSort();
        in = new Scanner(System.in);
        this.posts = array;
        this.users = users;
        postsUsuari = new ArrayList<Posts>();

    }

    public void ordena(int opcio, int ordre, Point point) {

        this.opcio = opcio;
        this.ordre = ordre;
        this.point = point;

        prepare();

        if (ordre == 3 ) {
            order(postsUsuari);
            Collections.reverse(postsUsuari);
            print(1);

        }else {
            order(posts);
            if (ordre == 1) {
                Collections.reverse(posts);
            }
            print(0);
        }



    }

    //Preparació dels posts, assignant a cada un un valor per posteriorment ordenar-los
    private void prepare() {
        int i = 0;
        String user;
        switch (ordre) {
            case 1:

                for (Posts p : posts) {

                    posts.get(i).setOrdre(p.getPublished().doubleValue());
                    i++;
                }

                break;
            case 2:

                for (Posts p : posts) {

                    posts.get(i).setOrdre(abs(posts.get(i).getPoint().calcularDistanciaDesde(point)));

                    i++;

                }
                break;
            case 3:

                System.out.println("Entra el nom d'usuari");
                user = in.nextLine();
                ordenaCombinat(user);
        }

    }

    //Ordenació dels posts segons el mètode especificat i segons el ordre, anteriormen definit
    private void order( ArrayList<Posts> posts) {

        long start = System.currentTimeMillis();
        switch (opcio) {
            case 1:
                mergeSort.sort(posts, 0, posts.size()-1);
                break;
            case 2:
                quickSort.sort(posts);

                break;
            case 3:
                selectionSort.sort(posts);
                break;
            case 4:
                radixSort.sort(posts);
        }
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("Temps: " + elapsedTimeMillis + "ms");

    }

    //Printa tantes publicacions com l'usuari demani per pantalla
    private void print(int c) {

        System.out.println("Quantes publicacions vols mostrar?");

        int j = Integer.parseInt(in.nextLine());

        switch (c){
            case 0:
                if (posts.size() < j){
                    j = posts.size();
                }
                for (int i = 0; i < j; i++) {
                    System.out.print(posts.get(i).getPublished() + " " + "ID: " + posts.get(i).getId() + "Location: " + posts.get(i).getPoint().getX() + " , " + posts.get(i).getPoint().getY() + " Likes: " + posts.get(i).getLiked_by().size() + " Comments: " + posts.get(i).getCommented_by().size() + "Ordre: " + posts.get(i).getOrdre()+"\n");
                }
            case 1:
                if (postsUsuari.size() < j){
                    j = postsUsuari.size();
                }
                for (int i = 0; i < j; i++) {

                   System.out.print(postsUsuari.get(i).getPublished() + " " + "ID: " + postsUsuari.get(i).getId() + " Location: " + postsUsuari.get(i).getPoint().getX() + " , " + postsUsuari.get(i).getPoint().getY() + " Likes: " + posts.get(i).getLiked_by().size() + " Comments: " + postsUsuari.get(i).getCommented_by().size() + " CATEGORY :"+postsUsuari.get(i).getCategory() + " Ordre: " + postsUsuari.get(i).getOrdre()  +  "\n");
                }

        }


    }

    //funció per assignar el pes de cada publicació segons interessos del usuari
    private void ordenaCombinat(String usuari) {

        int i;
        double ordre, s = 0, max = 0;


        for (Posts p : posts) {
            p.setOrdre(0);
        }

        i = trobaUsuari(usuari);


        if (i == -1) {
            System.out.println("Usuari no trobat!");
        } else {
            postsUsuari = creaLlistaPosts(i);
            //Perparo la importancia de les conexions guardant la màxima per després dividir-les totes entre la maxima i que quedin en tant per 1
            for (Connections d : users[i].getConnections()) {
                s = d.getComments() * 0.2 + d.getLikes() * 0.1 + d.getVisits() * 0.3;
                d.setOrder(s);

                if (max < s ){
                    max = s;
                }
            }

            //Rebo la informació de les categories que li agraden
            ArrayList<Categories> categories = likeHoodCategory();

            //Utilitzo el bucle per sumar la importancia de la categoria i el usuari de cada un dels posts
            for (Posts p : postsUsuari) {
                //En primer lloc itero per trobar el valor de la connecxó amb el usuari
                for(Connections c : users[i].getConnections()){
                    if(p.getUser().equals(c.getUsername())){
                        p.setOrdre((c.getOrder()/max) * IMP_INTERACCIO/100);
                        break;
                    }
                }
                //En segon lloc faig el mateix amb la categoria
                for (Categories l : categories) {
                    if (p.getCategory().equals(l.getName())) {
                        ordre = p.getOrdre();
                        p.setOrdre(ordre + (l.getLikes() * IMP_LIKEHOOD/100));


                    }


                }
                //sumo la importancia de la data, cal saber el numero maxim de la data per fer-ho en tant per 1
                ordre = p.getOrdre();

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//Resto 1000000000 perque sino tots els valors surten molt petits
                p.setOrdre(ordre + (p.getPublished().intValue() * (IMP_TEMPORALITAT/100)) / timestamp.getTime());
                System.out.println(  + p.getPublished().intValue() * (IMP_TEMPORALITAT/100) / timestamp.getTime());

            }




        }

    }

    private ArrayList<Posts> creaLlistaPosts(int i) {
        String nom;
        int p;

        for (Connections o : users[i].getConnections()) {
            nom = o.getUsername();
            p = trobaUsuari(nom);
            postsUsuari.addAll(users[p].getPosts());
        }
        return postsUsuari;

    }

    private int trobaUsuari(String nom) {
    boolean find = false;
    int i = 0;
    while(i < users.length && !find){
            if (nom.equals(users[i].getUsername())) {
                find = true;

            } else {
                i++;
            }
        }
        if (!find){
        i = -1;
        }
        return i;
    }

    private ArrayList<Categories> likeHoodCategory(){
        //Creo l'array on guardare el numero de likes de cada categoria
       ArrayList<Categories> categories = new ArrayList<Categories>();
       String category;
       boolean exists = false;
       //Omplo l'array amb totes les categories i els likes per cada una
        for(Posts p : postsUsuari){
            exists = false;
            category = p.getCategory();
            for(Categories c : categories){
                if(c.getName().equals(category)){
                    c.addLIke();
                    exists = true;
                    break;
                }
            }
            if (!exists){
                categories.add(new Categories(category));
            }
        }
        //Busco el màxim de likes
        double max = 0;
        for (Categories p : categories){
            if(p.getLikes() > max){
                max = p.getLikes();
            }
        }
        //divideixo els likes per el màxim per tenir-los tots en base 1
        for(Categories p : categories){
            p.setLikes(p.getLikes() / max);
        }
        return categories;
    }


}
