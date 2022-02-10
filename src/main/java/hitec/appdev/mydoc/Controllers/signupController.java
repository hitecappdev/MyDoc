package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Doctor;
import hitec.appdev.mydoc.Models.Notification;
import hitec.appdev.mydoc.Models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupController {
    private String name , email , phone , password;
    private Doctor _doctor = new Doctor();
    private Notification _notification ;
    private Patient patient = new Patient();
    private Gson gson = new Gson();
    byte a =0;

    void testInputs(){

        if(nameInput.getText().length()<8){
            label1.setText("Full name is too weak!");
            a=1;
        }else {
            label1.setText("");
        }

        if( !valEmail(emailInput.getText() )){
            label2.setText("Insert a valid e-mail please!");
            a=1;
        } else {
            label2.setText("");
        }

        if(phoneInput.getText().length()<9){
            label3.setText("Insert a valid phone number please!");
            a=1;
        }else {
            label3.setText("");
        }

        if(passwordInput.getText().length() < 6 ){
            label4.setText("Password is too weak!");
            a=1;

        }else if(passwordInput.getText().length() ==0){
            label4.setText("Insert Your Password!");
            a=1;

        }else {
            label4.setText("");
        }

    }

    public static boolean valEmail(String input){

        String emailReg = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailReg,Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(input);

        return matcher.find();
    }

    @FXML
    TextField nameInput,phoneInput,emailInput;
    @FXML
    Label label,label1,label2,label3,label4;
    @FXML
    PasswordField passwordInput;
    @FXML
    AnchorPane DoctorAnchor,patientAnchor;

    public void onCreateAccount(ActionEvent event) throws InterruptedException {

        testInputs();

        if(a==0) {

            try {
                System.out.println(nameInput.getText());

                _doctor.setName(nameInput.getText());
                _doctor.setPassword(passwordInput.getText());
                _doctor.setEmail(emailInput.getText());
                _doctor.setPhone(phoneInput.getText());

                System.out.println(gson.toJson(_doctor));

                String url = "http://localhost:8080/doctor/add";

                String urlNotify = "http://localhost:8080/doctor/notify";


                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(_doctor)))
                        .build();

                _notification = new Notification();

                HttpRequest requestNotify = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(_notification)))
                        .build();

                try {
                    HttpResponse response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    if(response.statusCode() == 200){
                        try {
                            DoctorAnchor.getChildren().setAll(Collections.singleton(FXMLLoader.load(getClass().getResource("/doctorLogin.fxml"))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(response.statusCode()==500){
                        System.out.println("This email is Already used !");
                        label2.setText("This email is Already used !");
                    }else if(response.statusCode()!=200){
                        label.setText("Error from server Try later!");
                    }

                } catch (IOException e) {
                    label.setText("Error from server Try later!");
                    e.printStackTrace();
                }

            }catch (Exception e){
                label.setText("Error from server Try later!");

                e.printStackTrace();
            }


        }

    }

    public void onCreateAccountPatient(ActionEvent event) throws InterruptedException {

        testInputs();

        if(a==0) {
            try {

                patient.setName(nameInput.getText());
                patient.setPassword(passwordInput.getText());
                patient.setEmail(emailInput.getText());
                patient.setPhone(phoneInput.getText());

                System.out.println(gson.toJson(patient));

                String url = "http://localhost:8080/patient/add";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(patient)))
                        .build();

                try {
                    HttpResponse response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    if(response.statusCode()==500){
                        label2.setText("This email is Already used !");
                    }else if(response.statusCode()!=200){
                        label.setText("Error from server Try later!");
                    }else {
                        try {
                            patientAnchor.getChildren().setAll(Collections.singleton(
                                    FXMLLoader.load(
                                            getClass().getResource("/patientLogin.fxml"))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    label.setText("Error from server Try later!");
                    e.printStackTrace();
                }

            }catch (Exception e){
                label.setText("Error from server Try later!");

                e.printStackTrace();
            }


        }

    }


    public void onGoToLogin(ActionEvent event)  {
        try {
            DoctorAnchor.getChildren().setAll(Collections.singleton(FXMLLoader.load(getClass().getResource("/doctorLogin.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onGoToLoginPatient(ActionEvent event)  {

        try {
            patientAnchor.getChildren().setAll(Collections.singleton(
                    FXMLLoader.load(
                            getClass().getResource("/patientLogin.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onSwitchToDoctor(ActionEvent event)  {

        try {
            patientAnchor.getChildren().setAll(Collections.singleton(
                    FXMLLoader.load(
                            getClass().getResource("/doctorLogin.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void onSwitchToPatient(ActionEvent event)  {

        try {
            DoctorAnchor.getChildren().setAll(Collections.singleton(
                    FXMLLoader.load(
                            getClass().getResource("/patientLogin.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
