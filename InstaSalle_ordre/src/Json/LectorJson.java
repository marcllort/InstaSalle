package Json;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class LectorJson {

    private static final String FILE_NAME = System.getProperty("user.dir") + "/data/posts_1M5_dataset.json";       //Nom de l'arxiu a obrir

    /**
     * Fució que retorna el tipus ConfiguracioServer amb tots els parametres llegit del config.json
     *
     * @return parametres del json
     */
    public static User[] llegeixUser() {
        User[] data;                                               // Dades a carregar
        Gson gson = new Gson();                                                // Entitat Gson
        JsonReader reader;                                                     // Reader de JSON

        try {                                                                  //Intentem carregar el fitxer json
            reader = new JsonReader(new FileReader(FILE_NAME));
            data = gson.fromJson(reader, User[].class);

            return data;

        } catch (FileNotFoundException e) {
            System.err.println("No s'ha pogut trobar el fitxer.");
            return null;
        }
    }
}
