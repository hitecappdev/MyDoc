package hitec.appdev.mydoc.Controllers;

import hitec.appdev.mydoc.Models.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CardController {

    @FXML
    Label nameId;

    @FXML
    Label cityId;

    @FXML
    Label specialityId;

    private Card card;

    public void setDate(Card c){
        this.card =c;
        nameId.setText(card.getName());
        cityId.setText(card.getCity());
        specialityId.setText(card.getSpeciality());

    }


}
