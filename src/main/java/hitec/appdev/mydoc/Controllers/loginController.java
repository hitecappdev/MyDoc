package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Doctor;
import hitec.appdev.mydoc.Models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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
    private Parent root;
    private Stage stage;
    private Scene scene;
    private Doctor _doctor = new Doctor();
    private Patient _patient = new Patient();
    private Gson gson = new Gson();

    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    Label label;

    public void onLogin(ActionEvent event) {

        String url = "http://localhost:8080/doctor/login";

        if(!emailInput.getText().equals("") && !passwordInput.getText().equals("")) {

            _doctor.setEmail(emailInput.getText());
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

                if (!response.body().equals("Your are LogIn successfully")) {
                    label.setText(response.body());
                } else {
                    // go to Home Page

                    HomeController homeController = new HomeController();

                    homeController.getInfo(emailInput.getText(),"Doctor");

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/doctorHome.fxml"));
                    root = fxmlLoader.load();

                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    //center la scene
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

                }
            } catch (IOException | InterruptedException e) {
                label.setText("Error from server Try later!");
                e.printStackTrace();
            }
        }else {
            label.setText("Please insert your login info");
        }

    }

    public void onLoginPatient(ActionEvent event) {

        String url = "http://localhost:8080/patient/login";

        if(!emailInput.getText().equals("") && !passwordInput.getText().equals("")) {


            _patient.setEmail(emailInput.getText());
            _patient.setPassword(passwordInput.getText());

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(_patient)))
                    .build();

            try {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if(!response.body().equals("Your are LogIn successfully")) {
                    label.setText(response.body());
                }else {
                    // go to Home Page

                    HomeController homeController = new HomeController();

                    homeController.getInfo(emailInput.getText(),"Patient");

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/patientHome.fxml"));
                    root = fxmlLoader.load();

                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    //center la scene
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);


                }

            } catch (IOException | InterruptedException e) {
                label.setText("Error from server Try later!");
                e.printStackTrace();
            }
        }else {
            label.setText("Please insert your login info");
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
