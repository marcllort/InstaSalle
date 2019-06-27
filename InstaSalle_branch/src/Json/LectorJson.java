package Json;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class LectorJson {

    private static final String FILE_NAME_USER = System.getProperty("user.dir") + "/data/users.json";       //Nom de l'arxiu a obrir
    //private static final String FILE_NAME_SERVER = System.getProperty("user.dir") + "/data/servers_plus.json";       //Nom de l'arxiu a obrir
    private static final String FILE_NAME_SERVER = System.getProperty("user.dir") + "/data/servers_plusplus.json";       //Nom de l'arxiu a obrir
    //private static final String FILE_NAME_NODE = System.getProperty("user.dir") + "/data/nodes_plus.json";       //Nom de l'arxiu a obrir
    private static final String FILE_NAME_NODE = System.getProperty("user.dir") + "/data/nodes_plusplus.json";       //Nom de l'arxiu a obrir


    /**
     * Fució que retorna el tipus ConfiguracioServer amb tots els parametres llegit del config.json
     *
     * @return parametres del json
     */
    public static User[] llegeixUser() {
        User[] dataUser;                                               // Dades a carregar
        Gson gson = new Gson();                                                // Entitat Gson
        JsonReader reader;                                                     // Reader de JSON

        try {                                                                  //Intentem carregar el fitxer json
            reader = new JsonReader(new FileReader(FILE_NAME_USER));
            dataUser = gson.fromJson(reader, User[].class);

            return dataUser;

        } catch (FileNotFoundException e) {
            System.err.println("No s'ha pogut trobar el fitxer.");
            return null;
        }
    }


    public static Server[] llegeixServer() {
        Server[] dataServer;                                               // Dades a carregar
        Gson gson2 = new Gson();                                                // Entitat Gson
        JsonReader reader2;                                                     // Reader de JSON

        try {
            reader2 = new JsonReader(new FileReader(FILE_NAME_SERVER));
            dataServer = gson2.fromJson(reader2, Server[].class);

            return dataServer;

        } catch (FileNotFoundException e) {
            System.err.println("No s'ha pogut trobar el fitxer.");
            return null;
        }
    }

    public static Node[] llegeixNodes() {
        Node[] dataNode;                                               // Dades a carregar
        Gson gson2 = new Gson();                                                // Entitat Gson
        JsonReader reader2;                                                     // Reader de JSON

        try {
            reader2 = new JsonReader(new FileReader(FILE_NAME_NODE));
            dataNode = gson2.fromJson(reader2, Node[].class);

            return dataNode;

        } catch (FileNotFoundException e) {
            System.err.println("No s'ha pogut trobar el fitxer.");
            return null;
        }
    }
}
