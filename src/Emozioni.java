import java.io.*;
import java.util.*;

    
public class Emozioni{
    int Amazement, Solemnity, Tenderness, Nostalgia, Calmness, Power, Joy, Tension, Sadness;
    String AmazementSTR, SolemnitySTR, TendernessSTR, NostalgiaSTR, CalmnessSTR, PowerSTR, JoySTR, TensionSTR, SadnessSTR;
    String path;

    public Emozioni(String p){
        path = p;
    }
    public void Add(int[] valutazioni, String[]opinioni, int idCanzione){           
        Amazement = valutazioni[0];
        Solemnity = valutazioni[1];
        Tenderness = valutazioni[2];
        Nostalgia = valutazioni[3];
        Calmness = valutazioni[4];
        Power = valutazioni[5];
        Joy = valutazioni[6];
        Tension = valutazioni[7];
        Sadness = valutazioni[8];

        AmazementSTR = opinioni[0];
        SolemnitySTR = opinioni[1];
        TendernessSTR = opinioni[2];
        NostalgiaSTR = opinioni[3];
        CalmnessSTR = opinioni[4];
        PowerSTR = opinioni[5];
        JoySTR = opinioni[6];
        TensionSTR = opinioni[7];
        SadnessSTR = opinioni[8];
        
        try {
            File f = new File(path +"Emozioni.dati.csv");
            BufferedReader reader = new BufferedReader(new FileReader(path+"Canzoni.dati.csv"));
            Boolean newFile = false;
            int numerocanzoni=0, i=0, c=0;
            String contStg, newStringMedia = "";;
            
            // Conto numero canzoni in canzoni.dati.csv e creo array che conterrà le righe del file
            while (reader.readLine() != null){
                numerocanzoni++;
            }
            String[]Elementi =new String[numerocanzoni];
            
            //Verifico esistenza file Emozioni.dati.csv, in caso non esista lo crea e imposta id e media a 0,0,0,0,0,0,0,0,0;

            if(f.exists() == false){
                           
                f.createNewFile();
                newFile=true;
                FileWriter fw = new FileWriter(path+"Emozioni.dati.csv");
                while(i<numerocanzoni){
                    fw.write(i+";0,0,0,0,0,0,0,0,0;\n");
                    i++;
                    
                }
                fw.close();        
            }
            
            //Leggo elementi di emozioni.dati.csv e li salvo nell'array Elementi
            BufferedReader reader2 = new BufferedReader(new FileReader(path+"Emozioni.dati.csv"));
            while( (contStg = reader2.readLine())!= null){
                Elementi[c] = contStg;
                c++;        
            }

            //Estraggo la stringa da modificare(basato su id canzone passato) da Elementi
            String str = Elementi[idCanzione];

            //Aggiungo le valutazioni in fondo alla stringa della canzone
            String ArrayDaInserire = Amazement+","+Solemnity+","+Tenderness+","+Nostalgia+","+Calmness+","+Power+","+Joy+","+Tension+","+Sadness+","+AmazementSTR+","+SolemnitySTR+","+TendernessSTR+","+NostalgiaSTR+","+CalmnessSTR+","+PowerSTR+","+JoySTR+","+TensionSTR+","+SadnessSTR+";";
            str = str+ArrayDaInserire;

            //Suddivido la stringa in elementi all'interno dell'array ContenutoElementi
            String[] ContenutoElementi = str.split(";");
            
            //Controllo se la valutazione inserita è la prima
            boolean primoEl = false; 
            if(ContenutoElementi[1].equals("0,0,0,0,0,0,0,0,0") || newFile == true){
                primoEl = true;
            }
            
            if(primoEl==false){
                newStringMedia = calcMedia(valutazioni, ContenutoElementi);
            }  
            else{
                newStringMedia = Amazement +","+ Solemnity +","+ Tenderness +","+ Nostalgia +","+ Calmness+","+Power+","+Joy+","+Tension+","+Sadness;
            }
            ContenutoElementi[1] = newStringMedia;
            str = "";
            for(i=0; i<ContenutoElementi.length; i++){
                if(i!=0){
                    str = str+";";
                }
                str = str + ContenutoElementi[i];
            }

            Elementi[idCanzione] = str+";";
            FileWriter fw2 = new FileWriter(path+"Emozioni.dati.csv");

            for(i=0; i<numerocanzoni; i++){
                fw2.write(Elementi[i]+"\n");
            }
            fw2.close();
        }
        catch(Exception e){System.out.println(e);}
    }           

    private String calcMedia(int[] valutazioni, String[] contenutoElementi){
        int l = contenutoElementi.length;
        int somma = 0, cont; 
        String[] s;
        double d;
        String ret = "";
        for(int c=0; c<9; c++){
            cont = 0;
            somma = 0;
            for(int i=2;i<l;i++){
                s=contenutoElementi[i].split(",");
                somma = somma + Integer.parseInt(s[c]);
                cont++;
            }
            d = somma / cont;
            if(c!=0){
                ret = ret+",";
            }
            ret = ret+Double.toString(d);
        }        
        
        return ret;
    }
}
