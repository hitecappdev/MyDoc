package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController  {

    private Stage stage;
    private Scene scene;

    private Doctor _doctor = new Doctor();
    private Gson gson = new Gson();

    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInput;

    public void onLogin(ActionEvent event) {

        String url = "http://localhost:8080/doctor/login";

        _doctor.setName(emailInput.getText());
        _doctor.setPassword(passwordInput.getText());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(_doctor)))
                .build();

        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @FXML
    AnchorPane DoctorAnchor,patientAnchor;

    public void onGoToSignup(ActionEvent event)  {

        try {
           DoctorAnchor.getChildren().setAll(Collections.singleton(
                   FXMLLoader.load(
                           getClass().getResource("/doctorSignup.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onGoToSignupPatient(ActionEvent event)  {

        try {
            patientAnchor.getChildren().setAll(Collections.singleton(
                    FXMLLoader.load(
                            getClass().getResource("/patientSignup.fxml"))));
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
