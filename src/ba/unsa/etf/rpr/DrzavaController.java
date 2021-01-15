package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DrzavaController{
    @FXML private Button btnOK;
    @FXML private Button btnCancel;
    @FXML private TextField fieldNaziv;
    @FXML private ChoiceBox choiceGrad;
    private GeografijaDAO dao=GeografijaDAO.getInstance();

    public DrzavaController(Drzava d, ArrayList<Grad> gradovi) {

    }
    public DrzavaController(){}

    @FXML
    public void initialize() {
        fieldNaziv.textProperty().addListener((observableValue, old, novi) -> {
            if(novi.length()!=0)fieldNaziv.setId("fieldNazivOk");
            else{fieldNaziv.setId("fieldNaziv");}
        });
        choiceGrad.setValue(dao.gradovi().get(0));
        choiceGrad.setItems(dajObsListu());
    }

    public ObservableList<Grad> dajObsListu(){
        ObservableList<Grad> povratni =FXCollections.observableArrayList(new ArrayList<Grad>());
        for (Grad g:dao.gradovi()){
            povratni.add(g);
        }
        return povratni;
    }
    public void okDodaj(ActionEvent event){
        if(provjeri()) {
            dao.dodajDrzavu(new Drzava(dao.drzave().size() + 1, dao.nadjiGrad(choiceGrad.getValue().toString()).getId(), fieldNaziv.getText()));
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }
    public void cancelClose(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    public boolean provjeri(){
        if(fieldNaziv.getText().length()!=0)return true;
        return false;
    }
}
