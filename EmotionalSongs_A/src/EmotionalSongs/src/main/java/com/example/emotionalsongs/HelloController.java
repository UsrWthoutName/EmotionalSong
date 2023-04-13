package com.example.emotionalsongs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.*;
import static com.example.emotionalsongs.CreaModificaPlaylist.GetRepository;
/**
 * Hellocontroller class contains all the methods for the management of the graphic interface
 * @author Edoardo Rossi, matricola 749089
 * @author Mattia Sindoni, matricola 750760
 * @author Gabriele Todeschini, matricola 750767
 * @author Matteo Argiolas, matricola 75125
 * **/
public class HelloController implements Initializable{
    @FXML
    public ListView<String> listview2;
    @FXML
    public ListView<String> ListCanzoni;
    @FXML
    public Label MyLabel1;
    @FXML
    public Label ErrorLabel;
    @FXML
    public TextField searchBar1;
    @FXML
    public Button button1;
    @FXML
    public Label CurrUsrId;
    @FXML
    public Label SongInfo;
    @FXML
    public ListView<String> ListUtenti;
    @FXML
    public Label JoyMedia;
    @FXML
    public Label AmazementMedia;
    @FXML
    public Label SolemnityMedia;
    @FXML
    public Label TendernessMedia;
    @FXML
    public Label NostalgiaMedia;
    @FXML
    public Label TensionMedia;
    @FXML
    public Label CalmnessMedia;
    @FXML
    public Label PowerMedia;
    @FXML
    public Label SadnessMedia;
    @FXML
    public Label JoyReview;
    @FXML
    public Label AmazementReview;
    @FXML
    public Label SolemnityReview;
    @FXML
    public Label TendernessReview;
    @FXML
    public Label NostalgiaReview;
    @FXML
    public Label TensionReview;
    @FXML
    public Label CalmnessReview;
    @FXML
    public Label PowerReview;
    @FXML
    public Label SadnessReview;
    //login
    @FXML
    public TextField getUsername;
    @FXML
    public PasswordField getPassword;
    @FXML
    public TextField getName;
    @FXML
    public TextField getSurname;
    @FXML
    public TextField getEmail;
    @FXML
    public TextField getUsernameREG;
    @FXML
    public PasswordField getPasswordREG;
    @FXML
    public TextField getIDtaxcode;
    @FXML
    public TextField getAddress;
    @FXML
    public Label errorpassword;
    @FXML
    public Label erroridtaxcode;
    @FXML
    public Label errorName;
    @FXML
    public Label errorSurname;
    @FXML
    public Label errorEmail;
    @FXML
    public Label errorAddress;
    @FXML
    public Label errorUsername;
    @FXML
    public Label labelSigninMessage;
    @FXML
    public AnchorPane WindowsSwitchPane1;
    public static String path = "..\\data\\";
    public String pathRepository=path+"Canzoni.dati.csv";
    public static String UserRepository=path+"UtentiRegistrati.dati.csv";
    public String UserPath=path;
    public String RegUsers=path+"\\Utenti\\";
    public static String Data=path;
    /**
     * GetRes method loads the pathRepository
     * **/
    public ArrayList<String> GetRes(){return GetRepository(pathRepository);}
    String[][] pr;
    double[] pm;
    /**
     * LoadRepositoryList method loads the current repository of songs
     * **/
    public void LoadRepositoryList(){
        ListCanzoni.getItems().addAll(GetRes());
        ListCanzoni.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String currentItem =  ListCanzoni.getSelectionModel().getSelectedItem();
                String[] css=currentItem.split(";");
                MyLabel1.setText(css[1]);
                SongInfo.setText(css[2]+"/"+css[3]+"/"+css[4]+"/"+css[5]);
                int id=CreaModificaPlaylist.getID(currentItem);
                Emozioni e=new Emozioni(UserPath);
                pm=e.getMedia(id);
                pr=e.getValutazioni(id);
                PrintMedia();
                if(!ListUtenti.getItems().isEmpty()){
                    ListUtenti.getItems().clear();
                }
                dataList();
            }});
        ListUtenti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        String currentItem2 = ListUtenti.getSelectionModel().getSelectedItem();
        if(ListUtenti!=null){
            CurrUsrId.setText(currentItem2);
        }
            AmazementReview.setText("");
            SolemnityReview.setText("");
            TendernessReview.setText("");
            NostalgiaReview.setText("");
            CalmnessReview.setText("");
            PowerReview.setText("");
            JoyReview.setText("");
            TensionReview.setText("");
            SadnessReview.setText("");
        if(pr!= null) {
            assert ListUtenti != null;
            int cis = ListUtenti.getSelectionModel().getSelectedIndex();
            System.out.print(cis);
            PrintReview(cis);}
        }
        }
        );
    }
    /**
     * PrintMedia method prints the media evaluation of user
     ***/
    public void PrintMedia(){
        AmazementMedia.setText(""+pm[0]);
        SolemnityMedia.setText(""+pm[1]);
        TendernessMedia.setText(""+pm[2]);
        NostalgiaMedia.setText(""+pm[3]);
        CalmnessMedia.setText(""+pm[4]);
        PowerMedia.setText(""+pm[5]);
        JoyMedia.setText(""+pm[6]);
        TensionMedia.setText(""+pm[7]);
        SadnessMedia.setText(""+pm[8]);
    }
    /**
     * PrintReview method prints the review of user
     * @param cis id of the selected review
     * **/
    public void PrintReview(int cis){
        if(!ListUtenti.getItems().isEmpty()){
            AmazementReview.setText("" + pr[cis][1]+"  "+pr[cis][10]);
            SolemnityReview.setText("" + pr[cis][2]+"  "+pr[cis][11]);
            TendernessReview.setText("" + pr[cis][3]+"  "+pr[cis][12]);
            NostalgiaReview.setText("" + pr[cis][4]+"  "+pr[cis][13]);
            CalmnessReview.setText("" + pr[cis][5]+"  "+pr[cis][14]);
            PowerReview.setText("" + pr[cis][6]+"  "+pr[cis][15]);
            JoyReview.setText("" + pr[cis][7]+"  "+pr[cis][16]);
            TensionReview.setText("" + pr[cis][8]+"  "+pr[cis][17]);
            SadnessReview.setText("" + pr[cis][9]+"  "+pr[cis][18]);
        }
    }
    /**
     * DataList method generates a datalist from the current user
     * **/
    public void dataList(){
        ArrayList<String>  utl =new ArrayList<>();
        for (String[] strings : pr) {
            String curr = strings[0];
            utl.add(curr);
        }
        ArrayList<String> urg1 =CreaModificaPlaylist.GetRepository(UserRepository);
        ArrayList<String> urg2=new ArrayList<>();
        String[] ca;
        for(int i=0;i<urg1.size();i++){
            ca=urg1.get(i).split(";");
            urg2.add(ca[4]);
        }
        ArrayList<String> urg3=new ArrayList<>();
        for(int i=0;i<utl.size();i++){
            int z = Integer.parseInt(utl.get(i));
            urg3.add(urg2.get(z));
        }
        ListUtenti.getItems().addAll(urg3);
        for (String[] strings : pr) {
            System.out.print("[");
            for (int k = 0; k < pr[0].length; k++) {
                System.out.print(strings[k] + " ");
                if (k + 1 < pr[0].length) System.out.print(", ");
            }
            System.out.print("]\n");
        }
    }
    /**
     * Search method gives the possibility to research a song by a label
     * **/
    public void search(){
        if(!listview2.getItems().isEmpty())
            listview2.getItems().clear();
        String search=searchBar1.getText();
        ArrayList<String> visualizzaplaylist=GetRes();
        ArrayList<String> visualizzaRicerca = new ArrayList<String>();
        for(String str: visualizzaplaylist) {
            if (!search.isEmpty()&&str.contains(search)||!search.isEmpty()&&str.toLowerCase().contains(search)||!search.isEmpty()&&str.toUpperCase().contains(search)) {
                visualizzaRicerca.add(str);
            }
        }
        listview2.getItems().addAll(visualizzaRicerca);
        if(visualizzaRicerca.isEmpty()){
            ArrayList<String> err = new ArrayList<String>();
            err.add("song not found");
            listview2.setAccessibleText("song not found");
            listview2.getItems().addAll(err);
        }
        listview2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                MyLabel1.setText("");
                String currentItem =  listview2.getSelectionModel().getSelectedItem();
                String[] css=currentItem.split(";");
                MyLabel1.setText(css[1]);
                SongInfo.setText(css[2]+"/"+css[3]+"/"+css[4]+"/"+css[5]);

                int id=CreaModificaPlaylist.getID(currentItem);
                Emozioni e=new Emozioni(UserPath);
                pm=e.getMedia(id);
                pr=e.getValutazioni(id);
                if(!ListUtenti.getItems().isEmpty()){
                    ListUtenti.getItems().clear();
                }
                dataList();
                PrintMedia();
            }
        });
    }
    /**
     * PasswordValidator makes a check of the validity of Password
     * @param password is the password of current user
     * **/
    public static boolean PasswordValidator(String password){
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }else{
            Matcher m = p.matcher(password);
            return m.matches();}
    }
    /**
     * idTAXcodeValidator make a check of the validity of idTAXcode
     * @param idTaxCode is the current IdTaxCode of current user
     * @return boolean representing the validity of the tax code
     * **/
    public static boolean idTAXcodeValidator(String idTaxCode){
        int size=idTaxCode.length();
        if(idTaxCode.matches(".*[A-Z].*")&&size==16&&idTaxCode.matches(".*[0-9].*"))
        {
            return true;}
        else
            return false;
    }
    /**
     * emailValidator method make a check of the validity of email
     * @param email is the email of current user
     * @return boolean representing the validity of email address
     * **/
    public static boolean emailValidator(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    /**
     * openSignin method
     * @throws IOException
     * **/
    public void openSignin(/*ActionEvent event*/) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signIN.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root1));
        stage.show();
    }
    /**
     * signIn method allow you to registrate on the application
     *
     * **/
    public void signIN(/*ActionEvent event*/){
        String name,surname,email,username,password,idtaxcode,address;
        Utenti u=new Utenti(UserPath);
        name=getName.getText();
        surname=getSurname.getText();
        email=getEmail.getText();
        username=getUsernameREG.getText();
        address=getAddress.getText();
        boolean ceckduplicatedusers=u.CheckDuplicatedUsers(email,username,UserRepository);
        boolean ceckPassword= PasswordValidator(password=getPasswordREG.getText());
        boolean ceckidTAXcode=idTAXcodeValidator(idtaxcode=getIDtaxcode.getText().toUpperCase());
        boolean ceckEmail=emailValidator(getEmail.getText());
        if(name.isEmpty()) {errorName.setText("not valid name");} else {errorName.setText("");}
        if(surname.isEmpty()) {errorSurname.setText("not valid surname");} else{errorSurname.setText("");}
        if(username.isEmpty()){errorUsername.setText("not valid username");}else{errorUsername.setText("");}
        if(address.isEmpty()){errorAddress.setText("not valid address");}else{errorAddress.setText("");}
        if(!ceckEmail){errorEmail.setText("not valid email");}else{errorEmail.setText("");}
        if(!ceckPassword){errorpassword.setText("not valid password");}else{errorpassword.setText("");}
        if(!ceckidTAXcode){erroridtaxcode.setText("not valid id tax code");}else{erroridtaxcode.setText("");}
        if(ceckPassword && ceckidTAXcode &&!name.isEmpty()&&!surname.isEmpty()&&!email.isEmpty()&&!username.isEmpty()&&!password.isEmpty()&&!address.isEmpty()&&ceckduplicatedusers){
            u.Registrazione(name,surname,idtaxcode,username,address,email,password);
            labelSigninMessage.setText("Sign In successful");
        }
        if(!ceckduplicatedusers&&!getName.getText().isEmpty()&&!getSurname.getText().isEmpty()&&!getEmail.getText().isEmpty()&&!getUsernameREG.getText().isEmpty()&&!getAddress.getText().isEmpty()){
            labelSigninMessage.setText("this user already exists");
        }
    }
    /**
     * logIN method check and make the user login
     * @throws IOException generate new IOException
     * @see IOException
     * **/
    public void logIN(/*ActionEvent event*/) throws IOException {
        String username,password;
        username=getUsername.getText();
        password=getPassword.getText();

        File fc = new File(UserRepository);
        if(fc.exists()){
            if( username!=null&& password!=null){
                Utenti u =new Utenti(UserPath);
                int res=u.Login(username,password);
                if(res>=0){
                    String str=Integer.toString(res);
                    FileWriter fw = new FileWriter(Data+"currentUsrID.txt", false);
                    fw.write(str);
                    fw.close();
                    ErrorLabel.setText("login successful");
                    AnchorPane pane=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
                    WindowsSwitchPane1.getChildren().setAll(pane);

                }
                else{
                    if(username.isEmpty()) {
                        ErrorLabel.setText("not valid username or password");}
                    else{ErrorLabel.setText("");}
                    ErrorLabel.setText("invalid data");
                }
            }
        }
        else {
            ErrorLabel.setText("no user registered on this computer");
        }
    }
    @Override
    /**
     * Initialize method makes the inizialization of graphic interface
     * **/
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    @FXML
    public ListView<String> ListCanzoniHOME;
    @FXML
    public ListView<String> ListCanzoniCercateHOME;
    @FXML
    public Label MyLabel1HOME;
    @FXML
    public Label SongInfoHOME;
    @FXML
    public Label HIDElabel;
    @FXML
    public Label HIDElabel1;
    @FXML
    public TextField searchBar1HOME;
    @FXML
    public Button ButtonCercaHOME;
    @FXML
    public Label HOMEerrorLABEL;
     @FXML
    public TextField GetnamePlaylist;
    @FXML
    public RadioButton Amazement1;
    @FXML
    public RadioButton Amazement2;
    @FXML
    public RadioButton Amazement3;
    @FXML
    public RadioButton Amazement4;
    @FXML
    public RadioButton Amazement5;
    @FXML
    public RadioButton Solemnity1;
    @FXML
    public RadioButton Solemnity2;
    @FXML
    public RadioButton Solemnity3;
    @FXML
    public RadioButton Solemnity4;
    @FXML
    public RadioButton Solemnity5;
    @FXML
    public RadioButton Tenderness1;
    @FXML
    public RadioButton Tenderness2;
    @FXML
    public RadioButton Tenderness3;
    @FXML
    public RadioButton Tenderness4;
    @FXML
    public RadioButton Tenderness5;
    @FXML
    public RadioButton Nostalgia1;
    @FXML
    public RadioButton Nostalgia2;
    @FXML
    public RadioButton Nostalgia3;
    @FXML
    public RadioButton Nostalgia4;
    @FXML
    public RadioButton Nostalgia5;
    @FXML
    public RadioButton Calmness1;
    @FXML
    public RadioButton Calmness2;
    @FXML
    public RadioButton Calmness3;
    @FXML
    public RadioButton Calmness4;
    @FXML
    public RadioButton Calmness5;
    @FXML
    public RadioButton Power1;
    @FXML
    public RadioButton Power2;
    @FXML
    public RadioButton Power3;
    @FXML
    public RadioButton Power4;
    @FXML
    public RadioButton Power5;
    @FXML
    public RadioButton Joy1;
    @FXML
    public RadioButton Joy2;
    @FXML
    public RadioButton Joy3;
    @FXML
    public RadioButton Joy4;
    @FXML
    public RadioButton Joy5;
    @FXML
    public RadioButton Tension1;
    @FXML
    public RadioButton Tension2;
    @FXML
    public RadioButton Tension3;
    @FXML
    public RadioButton Tension4;
    @FXML
    public RadioButton Tension5;
    @FXML
    public RadioButton Sadness1;
    @FXML
    public RadioButton Sadness2;
    @FXML
    public RadioButton Sadness3;
    @FXML
    public RadioButton Sadness4;
    @FXML
    public RadioButton Sadness5;
    @FXML
    public TextField AmazementNote;
    @FXML
    public TextField SolemnityNote;
    @FXML
    public TextField TendernessNote;
    @FXML
    public TextField NostalgiaNote;
    @FXML
    public TextField CalmnessNote;
    @FXML
    public TextField PowerNote;
    @FXML
    public TextField JoyNote;
    @FXML
    public TextField TensionNote;
    @FXML
    public TextField SadnessNote;
    public AnchorPane ReturnToHomePane;
    @FXML
    public Label JoyMediaHOME;
    @FXML
    public Label AmazementMediaHOME;
    @FXML
    public Label SolemnityMediaHOME;
    @FXML
    public Label TendernessMediaHOME;
    @FXML
    public Label NostalgiaMediaHOME;
    @FXML
    public Label TensionMediaHOME;
    @FXML
    public Label CalmnessMediaHOME;
    @FXML
    public Label PowerMediaHOME;
    @FXML
    public Label SadnessMediaHOME;
    @FXML
    public ListView<String> ListUtentiHOME;
    @FXML
    public Label JoyReviewHOME;
    @FXML
    public Label AmazementReviewHOME;
    @FXML
    public Label SolemnityReviewHOME;
    @FXML
    public Label TendernessReviewHOME;
    @FXML
    public Label NostalgiaReviewHOME;
    @FXML
    public Label TensionReviewHOME;
    @FXML
    public Label CalmnessReviewHOME;
    @FXML
    public Label PowerReviewHOME;
    @FXML
    public Label SadnessReviewHOME;
    @FXML
    public Label CurrUsrIdHOME;



    /**
     * StartPlaylist() method starts recording the playlist
     * @throws IOException
     * **/
    public void StartPlaylist() throws IOException {
        String NamePlaylist=GetnamePlaylist.getText();
        Path fileName = Path.of(Data+"currentUsrID.txt");
        String strID = Files.readString(fileName);
        System.out.println(strID);
        if(!NamePlaylist.isEmpty()){
            String str=RegUsers.concat(strID+"\\").concat(NamePlaylist+".csv");
            File file = new File(str);
            if(!file.exists()){
                CreaModificaPlaylist.CreaPlaylistVuota(str);
                System.out.println(str);
            }else HOMEerrorLABEL.setText("error");
        }
        else{
            HOMEerrorLABEL.setText("not valid name");
        }
    }
    /**
     * AddSong method allows the user to add songs and their rating on the graphic interface
     * @throws IOException generate a new IOException
     * @see IOException
     * **/
    public void AddSong() throws IOException {
        HOMEerrorLABEL.setText("");
        String SongForId= HIDElabel.getText();
        String NamePlaylist=GetnamePlaylist.getText();
        Path fileName = Path.of(Data+"currentUsrID.txt");
        String strID = Files.readString(fileName);
        String str=RegUsers.concat(strID+"\\").concat(NamePlaylist+".csv");
        File file = new File(str);
        if(file.exists()){


        int scan=0;

        int[] valutazioni=new int[9];
        String[]opinioni=new String[9];

        int AmazementRatings =0,SolemnityRatings =0,TendernessRatings =0, NostalgiaRatings =0,CalmnessRatings =0,PowerRatings =0,JoyRatings =0,TensionRatings =0,SadnessRatings =0;

        if(Amazement1.isSelected())AmazementRatings=1;
        if(Amazement2.isSelected())AmazementRatings=2;
        if(Amazement3.isSelected())AmazementRatings=3;
        if(Amazement4.isSelected())AmazementRatings=4;
        if(Amazement5.isSelected())AmazementRatings=5;

        if(Solemnity1.isSelected())SolemnityRatings=1;
        if(Solemnity2.isSelected())SolemnityRatings=2;
        if(Solemnity3.isSelected())SolemnityRatings=3;
        if(Solemnity4.isSelected())SolemnityRatings=4;
        if(Solemnity5.isSelected())SolemnityRatings=5;

        if(Tenderness1.isSelected())TendernessRatings=1;
        if(Tenderness2.isSelected())TendernessRatings=2;
        if(Tenderness3.isSelected())TendernessRatings=3;
        if(Tenderness4.isSelected())TendernessRatings=4;
        if(Tenderness5.isSelected())TendernessRatings=5;

        if(Nostalgia1.isSelected())NostalgiaRatings=1;
        if(Nostalgia2.isSelected())NostalgiaRatings=2;
        if(Nostalgia3.isSelected())NostalgiaRatings=3;
        if(Nostalgia4.isSelected())NostalgiaRatings=4;
        if(Nostalgia5.isSelected())NostalgiaRatings=5;

        if(Calmness1.isSelected())CalmnessRatings=1;
        if(Calmness2.isSelected())CalmnessRatings=2;
        if(Calmness3.isSelected())CalmnessRatings=3;
        if(Calmness4.isSelected())CalmnessRatings=4;
        if(Calmness5.isSelected())CalmnessRatings=5;

        if(Power1.isSelected())PowerRatings=1;
        if(Power2.isSelected())PowerRatings=2;
        if(Power3.isSelected())PowerRatings=3;
        if(Power4.isSelected())PowerRatings=4;
        if(Power5.isSelected())PowerRatings=5;

        if(Joy1.isSelected())JoyRatings=1;
        if(Joy2.isSelected())JoyRatings=2;
        if(Joy3.isSelected())JoyRatings=3;
        if(Joy4.isSelected())JoyRatings=4;
        if(Joy5.isSelected())JoyRatings=5;

        if(Tension1.isSelected())TensionRatings=1;
        if(Tension2.isSelected())TensionRatings=2;
        if(Tension3.isSelected())TensionRatings=3;
        if(Tension4.isSelected())TensionRatings=4;
        if(Tension5.isSelected())TensionRatings=5;

        if(Sadness1.isSelected())SadnessRatings=1;
        if(Sadness2.isSelected())SadnessRatings=2;
        if(Sadness3.isSelected())SadnessRatings=3;
        if(Sadness4.isSelected())SadnessRatings=4;
        if(Sadness5.isSelected())SadnessRatings=5;

        valutazioni[0]=AmazementRatings;
        valutazioni[1]=SolemnityRatings;
        valutazioni[2]=TendernessRatings;
        valutazioni[3]=NostalgiaRatings;
        valutazioni[4]=CalmnessRatings;
        valutazioni[5]=PowerRatings;
        valutazioni[6]=JoyRatings;
        valutazioni[7]=TensionRatings;
        valutazioni[8]=SadnessRatings;

        opinioni[0]=AmazementNote.getText();
        opinioni[1]=SolemnityNote.getText();
        opinioni[2]=TendernessNote.getText();
        opinioni[3]=NostalgiaNote.getText();
        opinioni[4]=CalmnessNote.getText();
        opinioni[5]=PowerNote.getText();
        opinioni[6]=JoyNote.getText();
        opinioni[7]=TensionNote.getText();
        opinioni[8]=SadnessNote.getText();

        ArrayList<String> Scan1= GetRepository(str);
        for (String s : Scan1) {
            int i=CreaModificaPlaylist.getID(s);
            int ii=CreaModificaPlaylist.getID(SongForId);
            if(i==ii){
                scan = 6;
                scan=scan+6;
                HOMEerrorLABEL.setText("this song already exist");
            }
        }
        if(scan==0) {
            int CurrID = Integer.parseInt(strID);
            if (!NamePlaylist.isEmpty() && !SongForId.isEmpty() && AmazementRatings != 0 && SolemnityRatings != 0
                    && TendernessRatings != 0 && NostalgiaRatings != 0 && CalmnessRatings != 0 && PowerRatings != 0 && JoyRatings != 0 && TensionRatings != 0 && SadnessRatings != 0) {

                for(int idx = 0; idx<9; idx++){
                    if(opinioni[idx].isEmpty()){
                        opinioni[idx] = " ";
                    }
                }


                int index = CreaModificaPlaylist.getID(SongForId);
                System.out.println(index);
                CreaModificaPlaylist.AggiungiBrani(pathRepository, str, index);
                Emozioni e = new Emozioni(UserPath);
                e.Add(valutazioni, opinioni, index, CurrID);
            } else {
                HOMEerrorLABEL.setText("error");
            }
        }else{
            HOMEerrorLABEL.setText("this song already exist");
        }
        }else HOMEerrorLABEL.setText("error");
    }
    /**
     * StopPlaylist() method generates a new empty playlist by graphic interface
     * @throws IOException generate a new IOException
     * @see IOException
     * **/
    public void StopPlaylist() throws IOException {
        HIDElabel1.setText(GetnamePlaylist.getText());
        Path fileName = Path.of(Data+"currentUsrID.txt");
        String strID = Files.readString(fileName);
        String NamePlaylist=GetnamePlaylist.getText();
        String PlPh= RegUsers+strID+"\\"+NamePlaylist+".csv";
        File file = new File(PlPh);
        if(file.exists()){
            ArrayList<String> Scan1= GetRepository(PlPh);
            if(Scan1.size()!=0){MyLabel1HOME.setText("");SongInfoHOME.setText("");HOMEerrorLABEL.setText("");GetnamePlaylist.setText("");
            reset();
            }
            else {
                HOMEerrorLABEL.setText("empty playlist, insert song");
            }
        }
    }
    /**
     * canc method gives the possibility to cancel the current evalutation
     * **/
    public void canc(){
        reset();
    }
    /**
     * reset method makes the reset of the choice made by the user for the evaluation of emotions
     * **/
    public void reset(){
        AmazementNote.setText("");SolemnityNote.setText("");TendernessNote.setText("");NostalgiaNote.setText("");CalmnessNote.setText("");
        PowerNote.setText("");JoyNote.setText("");TensionNote.setText("");SadnessNote.setText("");
        Amazement1.setSelected(false);Amazement2.setSelected(false);Amazement3.setSelected(false);Amazement4.setSelected(false);Amazement5.setSelected(false);
        Solemnity1.setSelected(false);Solemnity2.setSelected(false);Solemnity3.setSelected(false);Solemnity4.setSelected(false);Solemnity5.setSelected(false);
        Tenderness1.setSelected(false);Tenderness2.setSelected(false);Tenderness3.setSelected(false);Tenderness4.setSelected(false);Tenderness5.setSelected(false);
        Nostalgia1.setSelected(false);Nostalgia2.setSelected(false);Nostalgia3.setSelected(false);Nostalgia4.setSelected(false);Nostalgia5.setSelected(false);
        Calmness1.setSelected(false);Calmness2.setSelected(false);Calmness3.setSelected(false);Calmness4.setSelected(false);Calmness5.setSelected(false);
        Power1.setSelected(false);Power2.setSelected(false);Power3.setSelected(false);Power4.setSelected(false);Power5.setSelected(false);
        Joy1.setSelected(false);Joy2.setSelected(false);Joy3.setSelected(false);Joy4.setSelected(false);Joy5.setSelected(false);
        Tension1.setSelected(false);Tension2.setSelected(false);Tension3.setSelected(false);Tension4.setSelected(false);Tension5.setSelected(false);
        Sadness1.setSelected(false);Sadness2.setSelected(false);Sadness3.setSelected(false);Sadness4.setSelected(false);Sadness5.setSelected(false);
    }
    /**
     * ExitHome() method allows you to make the logOut from the appication
     * @throws IOException generate a new IOException
     * @see IOException
     * **/
    public void ExitHome() throws IOException {
        if(!HIDElabel1.getText().isEmpty()){
            Path fileName = Path.of(Data+"currentUsrID.txt");
            String strID = Files.readString(fileName);
            String NamePlaylist=HIDElabel1.getText();
            String PlPh= RegUsers+strID+"\\"+NamePlaylist+".csv";
            File file = new File(PlPh);
            if(file.exists()){
                ArrayList<String> Scan1= GetRepository(PlPh);
                if(Scan1.size()!=0){
                    AnchorPane pane=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartWindow.fxml")));
                    ReturnToHomePane.getChildren().setAll(pane);
                } else {
                    HOMEerrorLABEL.setText("empty playlist, insert song");
                }
            }
        }
        if(HIDElabel1.getText().isEmpty()){
            AnchorPane pane=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartWindow.fxml")));
            ReturnToHomePane.getChildren().setAll(pane);
        }
    }
    /**
     * PrintMediaHOME allows you to view the average thanks the graphic interface
     * **/
    public void PrintMediaHOME(){
        AmazementMediaHOME.setText(""+pm[0]);
        SolemnityMediaHOME.setText(""+pm[1]);
        TendernessMediaHOME.setText(""+pm[2]);
        NostalgiaMediaHOME.setText(""+pm[3]);
        CalmnessMediaHOME.setText(""+pm[4]);
        PowerMediaHOME.setText(""+pm[5]);
        JoyMediaHOME.setText(""+pm[6]);
        TensionMediaHOME.setText(""+pm[7]);
        SadnessMediaHOME.setText(""+pm[8]);
    }
    /**
     * PrintReviewHOME method allows you to review the choice made thanks the graphic interface
     * @param cis id of the selected review
     * **/
    public void PrintReviewHOME(int cis){
        if(!ListUtentiHOME.getItems().isEmpty()){
            AmazementReviewHOME.setText("" + pr[cis][1]+" "+pr[cis][10]);
            SolemnityReviewHOME.setText("" + pr[cis][2]+" "+pr[cis][11]);
            TendernessReviewHOME.setText("" + pr[cis][3]+" "+pr[cis][12]);
            NostalgiaReviewHOME.setText("" + pr[cis][4]+" "+pr[cis][13]);
            CalmnessReviewHOME.setText("" + pr[cis][5]+" "+pr[cis][14]);
            PowerReviewHOME.setText("" + pr[cis][6]+" "+pr[cis][15]);
            JoyReviewHOME.setText("" + pr[cis][7]+" "+pr[cis][16]);
            TensionReviewHOME.setText("" + pr[cis][8]+" "+pr[cis][17]);
            SadnessReviewHOME.setText("" + pr[cis][9]+" "+pr[cis][18]);
        }
    }
    /**
     * dataListHOME() method generates an arraylist on the homePage
     * **/
    public void dataListHOME(){
        ArrayList<String>  utl =new ArrayList<>();
        for (String[] strings : pr) {
            String curr = strings[0];
            utl.add(curr);
        }
        ArrayList<String> urg1 =CreaModificaPlaylist.GetRepository(UserRepository);
        ArrayList<String> urg2=new ArrayList<>();
        String[] ca;
        for(int i=0;i<urg1.size();i++){
            ca=urg1.get(i).split(";");
            urg2.add(ca[4]);
        }
        ArrayList<String> urg3=new ArrayList<>();
        for(int i=0;i<utl.size();i++){
            int z = Integer.parseInt(utl.get(i));
            urg3.add(urg2.get(z));
        }
        ListUtentiHOME.getItems().addAll(urg3);
        for (String[] strings : pr) {
            System.out.print("[");
            for (int k = 0; k < pr[0].length; k++) {
                System.out.print(strings[k] + " ");
                if (k + 1 < pr[0].length) System.out.print(", ");
            }
            System.out.print("]\n");
        }
    }
    /**
     * LoadRepositoryListHome() method Loads the repository of songs on the homepage of the graphic interface
     * **/
    public void LoadRepositoryListHOME() {
        ListCanzoniHOME.getItems().addAll(GetRes());
        ListCanzoniHOME.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String currentItem =  ListCanzoniHOME.getSelectionModel().getSelectedItem();
                HIDElabel.setText(currentItem);
                String[] css=currentItem.split(";");
                MyLabel1HOME.setText(css[1]);
                SongInfoHOME.setText(css[2]+"/"+css[3]+"/"+css[4]+"/"+css[5]);
                int id=CreaModificaPlaylist.getID(currentItem);
                Emozioni e=new Emozioni(UserPath);
                pm=e.getMedia(id);
                pr=e.getValutazioni(id);
                PrintMediaHOME();
                if(!ListUtentiHOME.getItems().isEmpty()){
                    ListUtentiHOME.getItems().clear();
                }
                dataListHOME();


            }
        }
        );
        ListUtentiHOME.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String currentItem2 = ListUtentiHOME.getSelectionModel().getSelectedItem();
                if(ListUtentiHOME!=null){
                    CurrUsrIdHOME.setText(currentItem2);
                }
                AmazementReviewHOME.setText("");
                SolemnityReviewHOME.setText("");
                TendernessReviewHOME.setText("");
                NostalgiaReviewHOME.setText("");
                CalmnessReviewHOME.setText("");
                PowerReviewHOME.setText("");
                JoyReviewHOME.setText("");
                TensionReviewHOME.setText("");
                SadnessReviewHOME.setText("");
                if(pr!= null){
                    assert ListUtentiHOME != null;
                    int cis =ListUtentiHOME.getSelectionModel().getSelectedIndex();
                    System.out.print(cis);
                    PrintReviewHOME(cis);
                }}}
        );
    }
    /**
     * HomeSearch method allows you to search songs on the home page of the graphic interface
     * **/
    public void HomeSearch(){
        if(!ListCanzoniCercateHOME.getItems().isEmpty()){
            ListCanzoniCercateHOME.getItems().clear();
        }
        String search=searchBar1HOME.getText();
        ArrayList<String> visualizzaplaylist=GetRes();
        ArrayList<String> visualizzaRicerca = new ArrayList<String>();
        for(String str: visualizzaplaylist) {
            if (!search.isEmpty()&&str.contains(search)||!search.isEmpty()&&str.toLowerCase().contains(search)||!search.isEmpty()&&str.toUpperCase().contains(search)) {
                visualizzaRicerca.add(str);
            }
        }
        ListCanzoniCercateHOME.getItems().addAll(visualizzaRicerca);
        if(visualizzaRicerca.isEmpty()){
            ArrayList<String> err = new ArrayList<String>();
            err.add("song not found");
            ListCanzoniCercateHOME.setAccessibleText("song not found");
            ListCanzoniCercateHOME.getItems().addAll(err);
        }
        ListCanzoniCercateHOME.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                MyLabel1HOME.setText("");
                String currentItem =  ListCanzoniCercateHOME.getSelectionModel().getSelectedItem();
                HIDElabel.setText(currentItem);
                String[] css=currentItem.split(";");
                MyLabel1HOME.setText(css[1]);
                SongInfoHOME.setText(css[2]+"/"+css[3]+"/"+css[4]+"/"+css[5]);

                int id=CreaModificaPlaylist.getID(currentItem);
                Emozioni e=new Emozioni(UserPath);
                pm=e.getMedia(id);
                pr=e.getValutazioni(id);
                if(!ListUtentiHOME.getItems().isEmpty()){
                    ListUtentiHOME.getItems().clear();
                }
                dataListHOME();
                PrintMediaHOME();
            }
        }
        );
    }
}



