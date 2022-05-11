import java.io.*;

class Canzoni{
    String path;
    public Canzoni(String p){
        path = p;
    }
    public String[] getInfo(int idCanzone){
        String[] res = "";
        BufferedReader br_info = new BufferedReader(new FileReader(path+"Canzoni.dati.csv"));
        BufferedReader br_val = new BufferedReader(new FileReader(path+"Emozioni.dati.csv"));
        
        
        
        return res;
    }
}

public class a {
    public static void main(String[] args) {
        
    }
}