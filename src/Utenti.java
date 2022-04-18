import java.io.*;

public class Utenti {
    
    int id;
    String nome;
    String codicefiscale;
    String indirizzo;
    String email;
    String password;

    public Utenti(String nome, String codiceFiscale, String indirizzo, String email, String password){   
        
    }
    public Utenti(String email, String password){ //Chiama login
        Login(); 
    }
    public void Registrazione(){
        id = 1; //Apre lettura file e legge id ultimo utente presente +1
        
    }        
}