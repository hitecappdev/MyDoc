package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController  {

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
}
