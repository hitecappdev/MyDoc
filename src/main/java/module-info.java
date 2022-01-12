module hitec.appdev.mydoc {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens hitec.appdev.mydoc to javafx.fxml;
    opens hitec.appdev.mydoc.Controllers to javafx.fxml;
    opens hitec.appdev.mydoc.Models;
    exports hitec.appdev.mydoc;
    exports hitec.appdev.mydoc.Controllers ;

    exports hitec.appdev.mydoc.Models;

}