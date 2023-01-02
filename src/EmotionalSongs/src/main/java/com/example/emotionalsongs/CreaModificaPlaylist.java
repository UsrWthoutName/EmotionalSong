package com.example.emotionalsongs;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The CreaModificaPlaylist class implements all of the functions for the management of playlists
 * @author Edoardo Rossi, matricola 749089
 * @author Mattia Sindoni, matricola 750760
 * @author Gabriele Todeschini, matricola 750767
 * @author Matteo Argiolas, matricola 75125
 * **/
public class CreaModificaPlaylist extends HelloController{
    /**
     * getID method allows to get the id of the selected song
     * @param str element in Canzoni.dati.csv with selected song information
     * @return id
     * **/
    public static int getID(String str){
        Pattern p = Pattern.compile("([\\d]+)+[^\\d]*[\\d]");
        Matcher m = p.matcher(str);
        int val = 0;
        if (m.find()) {
            val = Integer.parseInt(m.group(1));
        }
        return val;
    }

    /**
     * GetRepository method reads the csv file and insert on an arraylist all the data which are in the file Canzoni.dati.csv
     * @param PathRepository is the current path of the Repository
     * **/
    public static  ArrayList<String> GetRepository(String PathRepository){
        ArrayList<String> catalogo = new ArrayList<String>();
        try {
            String pathRepository=PathRepository;
            //lettura del file csv
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                //aggiunta all'arraylist catalogo di tutti gli elementi contenuti nel file canzoni.dati.csv
                catalogo.add(pathRepository);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      return catalogo;

    }
    /**
     * the CreateEmptyPlaylist() method allows the creation of a completely empty csv file
     * @param filePath is the path of the file
     * **/
    public static void CreaPlaylistVuota(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * AggiungiBrani() method creates a new playlist that contains all data of the file Canzoni.dati.csv
     * @param filePath is the path of the file
     * @param PathRepository is the current path of the Repository
     * @param index is the index
     * **/
    public static void  AggiungiBrani(String PathRepository, String filePath, int index){
        //creazione di un arraylist di stringhe "catalogo" che andrà a contenere tutto il file canzoni.dati.csv
        ArrayList<String> catalogo =GetRepository(PathRepository);
        //creazione dell'arraylist "playlist" che andrà a contenere tutti gli elementi presenti nella playlist gia esistente
        ArrayList<String> playlist = GetRepository(filePath);
        //eliminazione della vecchia playlist per evitare problemi di file duplicati
        //try {Path ceckpath = Paths.get(filePath);boolean isFileDeleted = Files.deleteIfExists(ceckpath);} catch (Exception e) {e.getStackTrace();}
        //lettura dell'index che farà riferimento all'indice dell'arraylist "catalogo"
        if(index>=0&&index<=169){
            //riempimento dell'arraylist "playlist" con un nuovo brano
            playlist.add(catalogo.get(index));
            //creazione di un nuovo file.csv che andrà a contenere i brani contenuti all'interno dell arraylist "playlist"
            try {
                FileWriter fw = new FileWriter(filePath);
                for (String s : playlist) {
                    fw.write(s + "\n");
                }
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{System.out.println("id non valido");}
    }
}