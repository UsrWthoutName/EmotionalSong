import java.io.*;
import java.util.Scanner;

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
    public Utenti(){ 
    
    }
    public int Login(String cred, String pass){
        File f = new File(path + "UtentiRegistrati.dati.csv");
        String s;
        String[] s_Split; 
        Boolean eseguito = false;
        try {
        
            Scanner file = new Scanner(f);
            while(file.hasNextLine() == true){
                s = file.nextLine();
                s_Split = s.split(";");
                if(eseguito == false){
                    if(s_Split[4].equals(cred) || s_Split[6].equals(cred)){
                        if(s_Split[7].equals(pass)){
                            System.out.println("Login eseguito");
                            eseguito = true;
                            userid = Integer.parseInt(s_Split[0]);
                            nome = s_Split[1];
                            cognome = s_Split[2];
                            codfis = s_Split[3];
                            username = s_Split[4];
                            indirizzo = s_Split[5];
                            email = s_Split[6];
                            password = s_Split[7];
                            return 0;
                        }
                    }
                }
            }
            if(eseguito == false){
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 3;
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
    public String getInfo(String tipo){
        if (tipo == "nome") {
            return nome;
        }
        else if (tipo == "cognome") {
            return cognome;
        }        
        else if (tipo == "codfis") {
            return codfis;
        }
        else if (tipo == "username") {
            return username;
        }
        else if (tipo == "indirizzo") {
            return indirizzo;
        }
        else if (tipo == "email") {
            return email;
        }
        else if(tipo == "password") {
            return password;
        }
        else{
            return 0;
        }
    }
}