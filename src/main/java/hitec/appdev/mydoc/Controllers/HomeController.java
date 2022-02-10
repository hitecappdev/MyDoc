package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Card;
import hitec.appdev.mydoc.Models.Doctor;
import hitec.appdev.mydoc.Models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Gson gson = new Gson();
    static String email="" ;
    static  String user = "";
    private String genderType="" ;
    private String weight="" ;
    private String height="" ;
    private boolean diab = false ;
    private String speciality;
    private String adress;
    @FXML
    private GridPane grid;
    private List<Card> cards = new ArrayList<>();
    @FXML
    private Button Addtolistbutton,phomesubmitbutton;
    @FXML
     TextField Pheight,Pweight,sicknesInput,adressInput,specialityInput;
    @FXML
    GridPane HomeGrid;
    @FXML
    Pane searchPane;
    @FXML
    Label PhomeName,Phomephone,Phomeemail,Phomeage;
    @FXML
    Label nameLabel ,emailLabel,phoneLabel;
    @FXML
     RadioButton choiceFemale,choiceMale,diabYes,diabNo;
    @FXML
     ToggleGroup diabetic,gender;
    @FXML
    private ListView<String> phomelist;
    @FXML
    AnchorPane homeAnchor,searchAnchor,homePane;
    @FXML
    private Text texthome;

    public void getGender(ActionEvent event){
        if(choiceFemale.isSelected()){
            genderType=choiceFemale.getText();
        }if(choiceMale.isSelected()){
            genderType=choiceMale.getText();
        }
    }

    public void onChangeDiab(ActionEvent event) {

        if(diabNo.isSelected()){
            diab=false;
            diabYes.setSelected(false);
            diabNo.setSelected(true);
        }if(diabYes.isSelected()){
            diab=true;
            diabNo.setSelected(false);
            diabYes.setSelected(true);
        }
    }

    public void getInfo(String email,String user){
        this.email = email;
        this.user = user;
    }

    private List<Card> getCards() throws IOException, InterruptedException {

        String url = "http://localhost:8080/doctor/allDoctors";

        Gson gson = new Gson();


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        Doctor[] doctors = gson.fromJson(response.body(),Doctor[].class);

        List<Card> cards = new ArrayList<>();

        Card card ;

        for(Doctor doctor : doctors){

            System.out.println(doctor.getName());
            card=new Card();

            card.setCity(doctor.getCity());
            card.setName(doctor.getName());
            card.setSpeciality(doctor.getSpeciality());

            cards.add(card);
        }
        return  cards;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(user.equals("Patient")) {
            String urlPost = "http://localhost:8080/patient/getPat";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlPost))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(this.email))
                    .build();
            try {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                Patient patient = gson.fromJson(response.body(), Patient.class);
                Phomephone.setText(patient.getPhone());
                Phomeemail.setText(patient.getEmail());
                PhomeName.setText(patient.getName());
                Pheight.setText(patient.getHeight());
                Pweight.setText(patient.getWeight());
                if(patient.getGender()!=null) {
                    if (patient.getGender().equals("Male")) {
                        choiceMale.setSelected(true);
                    } else if (patient.getGender().equals("Female")) {
                        choiceFemale.setSelected(true);}}
                if (patient.isDiabetic()) {
                    diabNo.setSelected(false);
                    diabYes.setSelected(true);
                } else {
                    diabNo.setSelected(true);
                    diabYes.setSelected(false);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();}
        }
        else {

            String urlPost = "http://localhost:8080/doctor/getDoc";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlPost))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(this.email))
                    .build();

            try {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                Doctor doctor = gson.fromJson(response.body(), Doctor.class);

                System.out.println(doctor.getName());

                phoneLabel.setText(doctor.getPhone());
                emailLabel.setText(doctor.getEmail());
                nameLabel.setText(doctor.getName());

                if(doctor.getGender()!=null){

                if (doctor.getGender().equals("Male")) {

                    choiceMale.setSelected(true);

                } else if (doctor.getGender().equals("Female")) {

                    choiceFemale.setSelected(true);
                 }
                }

                if(doctor.getCity()!=null){
                    adressInput.setText(doctor.getCity());
                }
                if(doctor.getSpeciality()!=null){
                    specialityInput.setText(doctor.getSpeciality());
                }

            } catch (IOException | InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void onAddSickness(ActionEvent event) {

    }

    public void onSubmitInfo(ActionEvent event) {

        String urlPost = "http://localhost:8080/patient/editPat";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlPost))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(
                        new Patient(PhomeName.getText(),Phomeemail.getText(),
                                Phomephone.getText(),genderType,Pheight.getText(),Pweight.getText(),diab)
                        ))
                ).build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        }

    }

    public void onGoToSeach(MouseEvent mouseEvent) {

        try {
            cards.addAll(getCards());
            System.out.println(cards.size());
            System.out.println(cards.get(1).getCity());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        int column = 1 ;
        int row = 0;

        try {
            for (int i = 0 ; i < cards.size() ; i++){

                System.out.println(cards.get(i).getCity());

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/profileCard.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();

                cardController.setDate(cards.get(i));

                System.out.println("loop cards");

                if(column==4){
                    column=1;
                    row++;
                }

                if(cards.get(i).getCity()!=null)
                grid.add(anchorPane,column++,row);
                GridPane.setMargin(anchorPane,new Insets(10));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        searchPane.toFront();

        searchAnchor.setStyle("-fx-background-color:#ffffff");
        homePane.setStyle("-fx-background-color:#0F7375");



    }

    public void onGoToHome(MouseEvent mouseEvent) {

        HomeGrid.toFront();

        searchAnchor.setStyle("-fx-background-color:#0F7375");
        homePane.setStyle("-fx-background-color:#ffffff");



    }

    public void onSubmitInfoDoctor(ActionEvent event) {

        String urlPost = "http://localhost:8080/doctor/editDoc";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlPost))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(
                                new Doctor(nameLabel.getText(),emailLabel.getText(),phoneLabel.getText(),
                                        adressInput.getText(),specialityInput.getText(),genderType)
                        ))
                )
                .build();

        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            System.out.println(response.body());

            System.out.println("it workss");


        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        }

    }
}
