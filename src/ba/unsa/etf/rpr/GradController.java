package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GradController{
    @FXML private Button btnOK;
    @FXML private Button btnCancel;
    @FXML private TextField fieldNaziv;
    @FXML private TextField fieldBrojStanovnika;
    @FXML private ChoiceBox choiceDrzava;
    private GeografijaDAO dao=GeografijaDAO.getInstance();

    public GradController(Object o, ArrayList<Drzava> drzave) {
    }
    public GradController(){}
    @FXML
    public void initialize() {
        fieldNaziv.textProperty().addListener((observableValue, old, novi) -> {
        });
        choiceDrzava.setValue(dao.drzave().get(0));
        choiceDrzava.setItems(dajObsListu());
    }

    public ObservableList<Drzava> dajObsListu(){
        ObservableList<Drzava> povratni =FXCollections.observableArrayList(new ArrayList<Drzava>());
        for (Drzava d:dao.drzave()){
            povratni.add(d);
        }
        return povratni;
    }
    public void okDodaj(ActionEvent event){
        System.out.println(dao.drzave().size());
        dao.dodajGrad(new Grad(dao.gradovi().size()+1,Integer.parseInt(fieldBrojStanovnika.getText()),
                dao.nadjiDrzavu(choiceDrzava.getValue().toString()).getId(),fieldNaziv.getText().toString(),dao.nadjiDrzavu(choiceDrzava.getValue().toString())));
        System.out.println(dao.drzave().size());
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    public void cancelClose(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public Grad getGrad() {
        return null;
    }
}