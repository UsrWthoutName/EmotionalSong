package com.example.serverstartemotionalsong;

import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;
//TXT -> JSON
public class strunz2 {

    public static void main(String[] args) {
        try {
            // Leggi il file txt contenente le righe con dati separati da <SEP>
            File inputFile = new File("C:\\Users\\gabry\\Desktop\\ServerStart-EmotionalSong\\src\\main\\java\\com\\example\\serverstartemotionalsong\\canzoni.txt");
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            JSONArray jsonArray = new JSONArray();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("<SEP>");
                if (parts.length == 4) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", parts[0]);
                    jsonObject.put("id", parts[1]);
                    jsonObject.put("artista", parts[2]);
                    jsonObject.put("titolo", parts[3]);
                    jsonArray.add(jsonObject);
                }
            }

            br.close();

            // Scrivi il JSON risultante in un file
            File outputFile = new File("output.json");
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Conversione da txt a JSON completata con successo!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}