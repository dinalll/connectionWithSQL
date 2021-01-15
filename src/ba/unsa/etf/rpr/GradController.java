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

public class GradController{
    @FXML private Button btnOK;
    @FXML private Button btnCancel;
    @FXML private TextField fieldNaziv;
    @FXML private TextField fieldBrojStanovnika;
    @FXML private ChoiceBox choiceDrzava;
    private GeografijaDAO dao=GeografijaDAO.getInstance();
    private Grad zadnjiDodan=null;

    public GradController(Grad o, ArrayList<Drzava> drzave) {
        zadnjiDodan=o;
    }
    public GradController(){}
    @FXML
    public void initialize() {
        if(zadnjiDodan!=null){
            fieldNaziv.setText(zadnjiDodan.getNaziv());
            fieldBrojStanovnika.setText(zadnjiDodan.getBrojStanovnika()+"");
        }
        fieldNaziv.textProperty().addListener((observableValue, old, novi) -> {
            if(novi.length()!=0)fieldNaziv.setId("fieldNazivOk");
            else{fieldNaziv.setId("fieldNaziv");}
        });
        fieldBrojStanovnika.textProperty().addListener((observableValue, s, t1) ->{
            if(t1.length()!=0)fieldBrojStanovnika.setId("fieldBrojStanovnikaOk");
            else{fieldBrojStanovnika.setId("fieldBrojStanovnika");}
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
        if(provjeri()) {
            if(zadnjiDodan!=null){
                zadnjiDodan.setNaziv(fieldNaziv.getText());
                zadnjiDodan.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
                dao.izmijeniGrad(zadnjiDodan);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
            else {
                zadnjiDodan = new Grad(dao.gradovi().size() + 1, Integer.parseInt(fieldBrojStanovnika.getText()),
                        dao.nadjiDrzavu(choiceDrzava.getValue().toString()).getId(), fieldNaziv.getText().toString(),
                        dao.nadjiDrzavu(choiceDrzava.getValue().toString()));
                dao.dodajGrad(zadnjiDodan);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }
    public void cancelClose(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    public boolean provjeri(){
        if(fieldBrojStanovnika.getText().length()!=0 && fieldNaziv.getText().length()!=0){
            if(fieldBrojStanovnika.getText().contains("-"))return false;
            return true;
        }
        return false;
    }
    public Grad getGrad() {
        return zadnjiDodan;
    }
}