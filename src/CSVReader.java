import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderJava{

    public static void main(String[] args) {
        List<Playlist> playlist = readPlaylistFromCSV("Canzoni.dati.csv");
        /* Stampiamo tutte le canzoni lette dal CSV File
        for (Playlist p : playlist) {
            System.out.println(p)*/;
        }
    }
    private static List<Playlist> readBooksFromCSV(String fileName) {
        List<Playlist> playlist = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        //crea un instanza di bufferedReader
        //usando il try,

        try (BufferedReader br = File.newBufferedReader(pathToFile)) {
            //legge la prima linea dal file di testo
            String line = br.readLine();
            //mette in loop la lettura
            while (line != null) {
                //usare string.split per caricare un array di stringhe con la
                //valutazione per ogni linea di file, usando un comma per dividere
                String[] attributes = line.split(",");

                Playlist playlist = createBook(attributes);

                //aggiungere una playlist nell'arraylist
                playlist.add(playlist);

                /*legge la linea successiva prima di andare in loop
                e se finisce, la linea sarà nulla
                 */
                line = br.readLine();
            }

        }catch (IOException ioe){
            ioe.printStacktrace();
        }
       return playlist;
   }
}
