package hitec.appdev.mydoc.Controllers;

import com.google.gson.Gson;
import hitec.appdev.mydoc.Models.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupController {

    private String name , email , phone , password;
    private Doctor _doctor = new Doctor();
    private Gson gson = new Gson();


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

    public void onCreateAccount(ActionEvent event) throws InterruptedException {

        byte a =0;

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


        if(a==0) {

            try {

                _doctor.setName(nameInput.getText());
                _doctor.setPassword(passwordInput.getText());
                _doctor.setEmail(emailInput.getText());
                _doctor.setPhone(phoneInput.getText());

                _doctor = new Doctor(name,email,phone,password);

                System.out.println(gson.toJson(_doctor));

                String url = "http://localhost:8080/doctor/add";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(_doctor)))
                        .build();

                try {
                    HttpResponse response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    if(response.statusCode()==500){
                        System.out.println("This name is Already used !");
                        label1.setText("This name is Already used !");
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

    public void onGoToLogin(ActionEvent event) {
    }
}
