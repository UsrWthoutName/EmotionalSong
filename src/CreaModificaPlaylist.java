package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class CreaModificaPlaylist extends Main{
    /*
       System.out.println("inserisci la path");
        String path=sc.nextLine();
        System.out.println("inserisci il nome della playlist");
        String nomePy=sc.nextLine();
        String DefinitivePath=path.concat("\\"+nomePy);
        System.out.println(DefinitivePath.toString());
     */
    public static void CreaPlaylist(String filePath,String PathRepository) {
        Scanner sc=new Scanner(System.in);
        ArrayList<String> catalogo = new ArrayList<String>();
        try {
            String pathRepository=PathRepository;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                catalogo.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<String> playlist = new ArrayList<String>();
        int index;
        File file = new File(filePath);
        do {
            System.out.println("inserisci l'id del brano da aggiungere");
            index = sc.nextInt();
            if(index>=0&&index<=169){
                playlist.add(catalogo.get(index));
                System.out.println("brano scelto: " + catalogo.get(index));
            }else{System.out.println("id non valido reinserisci l'id");}
        }while(index>-1);
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
    public static void RinominaPlaylist(String filePath,String newname) throws IOException {
        Path source = Paths.get(filePath);
        Files.move(source, source.resolveSibling(newname));
    }
    public static void EliminaPlaylist(String filePath){
        try {
            Path ceckpath = Paths.get(filePath);
            boolean isFileDeleted = Files.deleteIfExists(ceckpath);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public static void AggungiBrani(String PathRepository, String filePath, int index) throws IOException, CsvException {
        Scanner sc=new Scanner(System.in);
        ArrayList<String> catalogo = new ArrayList<String>();
        try {
            String pathRepository=PathRepository;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                catalogo.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<String> playlist = new ArrayList<String>();
        try {
            String pathRepository=filePath;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                playlist.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {Path ceckpath = Paths.get(filePath);boolean isFileDeleted = Files.deleteIfExists(ceckpath);} catch (Exception e) {e.getStackTrace();}
        index = sc.nextInt();
        if(index>=0&&index<=169){
            playlist.add(catalogo.get(index));
        }else{System.out.println("id non valido");}
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
    public static void RimuoviBrani(String filePath, int index){
        Scanner sc=new Scanner(System.in);
        ArrayList<String> playlist = new ArrayList<String>();
        index = sc.nextInt();
        try {
            String pathRepository=filePath;
            BufferedReader br = new BufferedReader( new FileReader(pathRepository));
            while( (pathRepository = br.readLine()) != null)
            {
                playlist.add(pathRepository);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {Path ceckpath = Paths.get(filePath);boolean isFileDeleted = Files.deleteIfExists(ceckpath);} catch (Exception e) {e.getStackTrace();}
            playlist.remove(index);
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
