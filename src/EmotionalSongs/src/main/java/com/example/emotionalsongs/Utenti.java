package com.example.emotionalsongs;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Utenti class implements the class for the management of Users
 * @author Edoardo Rossi, matricola 749089
 * @author Mattia Sindoni, matricola 750760
 * @author Gabriele Todeschini, matricola 750767
 * @author Matteo Argiolas, matricola 75125
 * **/

public class Utenti extends HelloController{
    String path;
    private String nome;
    private String cognome;
    private String codfis;
    private String username;
    private String address;
    private String email;
    private int userid;
    private String password;


    /**
     * Utenti method is the constructor method for the registration
     * @param dir get the path directory
     * **/
    public Utenti(String dir){
        path=dir;
    }
    /**
     * Login method allow you to make the LogIN of an existing user
     * @param cred are the credential of the user
     * @param pass is the current password of the user
     * @return if authentication was successful, it returns the user's id, otherwise it returns -1
     * **/
    public int Login(String cred, String pass){
        File f = new File(path + "UtentiRegistrati.dati.csv");
        String s;
        String[] s_Split;
        Boolean eseguito = false;
        try {
            Scanner file = new Scanner(f);
            while(file.hasNextLine()){
                s = file.nextLine();
                s_Split = s.split(";");
                if(!eseguito){
                    if(s_Split[4].equals(cred) || s_Split[6].equals(cred)){
                        if(s_Split[7].equals(pass)){
                            System.out.println("Login eseguito");
                            userid = Integer.parseInt(s_Split[0]);
                            nome = s_Split[1];
                            cognome = s_Split[2];
                            codfis = s_Split[3];
                            username = s_Split[4];
                            address = s_Split[5];
                            email = s_Split[6];
                            password = s_Split[7];
                            return userid;
                        }
                    }
                }
            }
            if(!eseguito){
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 3;
    }
    /**
     * Registrazione method makes the registration of a new user
     * @param ind the current user address
     * @param cdf the current user TaxFiscalCode
     * @param em the current user email
     * @param pw the current user password
     * @param usnm the current user username
     * @param n the current user name
     * @param c the current user surname
     * **/
    public void Registrazione(String n, String c, String cdf, String usnm,String ind, String em, String pw){
        nome = n;
        cognome = c;
        codfis = cdf;
        username = usnm;
        address= ind;
        email = em;
        password = pw;
        File f = new File(path + "UtentiRegistrati.dati.csv");
        try {

            if(!f.exists()){
                f.createNewFile();
                userid = 0;
            }
            else{
                Scanner s = new Scanner(f);
                int JJ=0;
                while(s.hasNextLine()){
                    s.nextLine();
                    JJ++;
                }
                userid = JJ;

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        String infoutente = userid+";"+nome+";"+cognome+";"+codfis+";"+username+";"+address+";"+email+";"+password+"\n";
        try {
            FileWriter fw = new FileWriter(path+"UtentiRegistrati.dati.csv", true);

            fw.write(infoutente);
            fw.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            File d = new File(path+"Utenti\\"+userid);
            d.mkdirs();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * CheckDuplicatedUser method checks if there are similar user
     * @param UserRepository check the userRepository
     * @param userid check the current userID
     * @param email check the current user email
     * @return boolean value represents the result of the email uniqueness check
     * **/
    public boolean CheckDuplicatedUsers(String email,String userid,String UserRepository){
        ArrayList<String>  UsersList = CreaModificaPlaylist.GetRepository(UserRepository);
        boolean result=true;
        String[] vett;
        String t1,t2;
        for(String check:UsersList){
            vett=check.split(";");
            t1=vett[4];
            t2=vett[6];
            if(t1.contains(userid)||t2.contains((email))){
                result=false;
                break;
            }
        }
        return result;
    }
}
