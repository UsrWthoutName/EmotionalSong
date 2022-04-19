import java.io.*;

public class Utenti {
    String path = "../data/";
    private String nome;
    private String cognome;
    private String codfis;
    private String username;
    private String indirizzo;
    private String email;
    private int userid;
    private String password;


    //Costruttore per registrazione
    public Utenti(String n, String c, String cdf, String usnm,String ind, String em, String pw){
        nome = n;
        cognome = c;
        codfis = cdf;
        username = usnm;
        indirizzo= ind;
        email = em;
        password = pw;
        Registrazione();
    }
    private void Registrazione(){
        File f = new File(path + "UtentiRegistrati.dati.csv");
        try {
            
            if(f.exists() == false){
                f.createNewFile();
                userid = 0;
            }
            else{
                Scanner s = new Scanner(f); 
                int c=0;
                while(s.hasNextLine() == true){
                    s.nextLine();
                    c++;
                }
                userid = c;
                
            }
        
        } catch (Exception e) {
            System.out.println(e);
        }
        
        String infoutente = userid+";"+nome+";"+cognome+";"+codfis+";"+username+";"+indirizzo+";"+email+";"+password+"\n";
        try {
            FileWriter fw = new FileWriter(path+"UtentiRegistrati.dati.csv", true);
            fw.write(infoutente);
            fw.close();
   
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            File d = new File(path+"Utenti/"+userid);
            d.mkdir();
        } catch (Exception e) {
            System.out.println(e);
        }
                    
        
    }
}