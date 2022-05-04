package com.example.visualizzals;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class CreaModificaPlaylist extends HelloController{
    /*
        System.out.println("inserisci la path");
        String path=sc.nextLine();
        System.out.println("inserisci il nome della playlist");
        String nomePy=sc.nextLine();
        String DefinitivePath=path.concat("\\"+nomePy);
        System.out.println(DefinitivePath.toString());
     */
     //Metodo CreaPlaylist() permette di creare un playlist sottoforma di file.csv
     //partendo da un repository di canzoni gia esistente "Canzoni.dati.csv"


    public static  ArrayList<String> GetRepository(String PathRepository){
        Scanner sc=new Scanner(System.in);
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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return catalogo;
    }

    public static void CreaPlaylist(String filePath,String PathRepository) {
        //creazione scanner per l'input dei dati
        Scanner sc=new Scanner(System.in);
        //creazione di un arraylist di stringhe "catalogo" che andrà a contenere tutto il file canzoni.dati.csv
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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //creazione dell'arraylist "playlist" che partendo dall'arraylist"catalogo" verrà riempito con i nuovi brani
        ArrayList<String> playlist = new ArrayList<String>();
        int index;
        //riempimendo dell arraylist "playlist" con i brani contenuti all'interno dell'arraylist "catalogo"
        //il riempimento avviene tramite un indice"index" che fa riferimento alla posizione dei brani contenuti nell
        //arraylist "catalogo"
        do {
            System.out.println("inserisci l'id del brano da aggiungere");
            index = sc.nextInt();
            if(index>=0&&index<=169){
                playlist.add(catalogo.get(index));
                System.out.println("brano scelto: " + catalogo.get(index));
            }else{System.out.println("id non valido reinserisci l'id");}
        }while(index>-1);
        //creazione di un nuovo file.csv che andrà a contenere i brani contenuti all'interno dell arraylist "playlist"
        try {
            FileWriter fw = new FileWriter(filePath);
            for(int i=0;i< playlist.size();i++){
                fw.write(playlist.get(i)+"\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //il metodo CreaPlaylistVuota() consente la creazione di un file.csv completamente vuoto
    public static void CreaPlaylistVuota(String filePath) {
        Scanner sc=new Scanner(System.in);
        ArrayList<String> playlist = new ArrayList<String>();
        try {
            FileWriter fw = new FileWriter(filePath);
            for(int i=0;i< playlist.size();i++){
                fw.write(playlist.get(i)+"\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //il metodo RinominaPlaylist() consente di rinominare una playlist "sottoforma di file.csv" già esistente
    public static void RinominaPlaylist(String filePath,String newname) throws IOException {
        Path source = Paths.get(filePath);
        Files.move(source, source.resolveSibling(newname));
    }
    //il metodo EliminaPlaylist() consente di eliminare una playlist "sottoforma di file.csv" già esistente
    public static void EliminaPlaylist(String filePath){
        try {
            Path ceckpath = Paths.get(filePath);
            boolean isFileDeleted = Files.deleteIfExists(ceckpath);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    //il metodo AggungiBrani() consente di aggiungere brani ad una playlist "sottoforma di file.csv" già esistente
    public static void AggungiBrani(String PathRepository, String filePath, int index) throws IOException {
        //creazione scanner per l'input dei dati
        Scanner sc=new Scanner(System.in);
        //creazione di un arraylist di stringhe "catalogo" che andrà a contenere tutto il file canzoni.dati.csv
        ArrayList<String> catalogo = new ArrayList<String>();
        try {
            //lettura del file csv
            String pathRepository=PathRepository;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                //aggiunta all'arraylist catalogo di tutti gli elementi contenuti nel file canzoni.dati.csv
                catalogo.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //creazione dell'arraylist "playlist" che andrà a contenere tutti gli elementi presenti nella playlist gia esistente
        ArrayList<String> playlist = new ArrayList<String>();
        try {
            //lettura del file csv
            String pathRepository=filePath;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                //aggiunta all'arraylist "playlist" di tutti gli elementi contenuti nella playlist gia esestente
                //alla quale bisogna aggiungere nuovi brani
                playlist.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //eliminazione della vecchia playlist per evitare problemi di file duplicati
        try {Path ceckpath = Paths.get(filePath);boolean isFileDeleted = Files.deleteIfExists(ceckpath);} catch (Exception e) {e.getStackTrace();}
        //lettura dell'index che farà riferimento all'indice dell'arraylist "catalogo"
        index = sc.nextInt();
        if(index>=0&&index<=169){
            //riempimento dell'arraylist "playlist" con un nuovo brano
            playlist.add(catalogo.get(index));
        }else{System.out.println("id non valido");}
        //creazione di un nuovo file.csv che andrà a contenere i brani contenuti all'interno dell arraylist "playlist"
        try {
            FileWriter fw = new FileWriter(filePath);
            for(int i=0;i< playlist.size();i++){
                fw.write(playlist.get(i)+"\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //il metodo RimuoviBrani() consente di rimuovere i brani contenuti in una playlist "sottoforma di file.csv" già esistente
    public static void RimuoviBrani(String filePath, int index){
        //creazione scanner per l'input dei dati
        Scanner sc=new Scanner(System.in);
        //creazione dell'arraylist "playlist" che andrà a contenere tutti gli elementi presenti nella playlist gia esistente
        ArrayList<String> playlist = new ArrayList<String>();
        //lettura dell'index che farà riferimento all'indice dell'arraylist "playlist"
        index = sc.nextInt();
        try {
            //lettura del file csv
            String pathRepository=filePath;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                //aggiunta all'arraylist "playlist" di tutti gli elementi contenuti nella playlist gia esestente
                //alla quale bisogna rimuovere nuovi brani
                playlist.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //eliminazione della vecchia playlist per evitare problemi di file duplicati
        try {Path ceckpath = Paths.get(filePath);boolean isFileDeleted = Files.deleteIfExists(ceckpath);} catch (Exception e) {e.getStackTrace();}
        //rimozione dall'arraylist "playlist" dell'elemento specificato tramite indice "index"
            playlist.remove(index);
        //creazione di un nuovo file.csv che andrà a contenere i brani contenuti all'interno dell arraylist "playlist"
        try {
            FileWriter fw = new FileWriter(filePath);
            for(int i=0;i< playlist.size();i++){
                fw.write(playlist.get(i)+"\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}




