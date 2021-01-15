package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GlavnaController extends Application{
    @FXML private TableColumn colGradId;
    @FXML private TableColumn colGradNaziv;
    @FXML private TableColumn colGradStanovnika;
    @FXML private TableColumn colGradDrzava;
    @FXML private TableView tableViewGradovi;
    @FXML private Button btnDodajDrzavu;
    @FXML private Button btnDodajGrad;
    private GeografijaDAO dao;

    public GlavnaController() {
        dao=GeografijaDAO.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        loader.setController(new GlavnaController());
        Parent root = loader.load();
        primaryStage.setTitle("Gradovi svijeta");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
    @FXML
    public void initialize() {
        new Thread(() -> {
            ucitajBazu();
        }).start();
    }

    public void ucitajBazu(){
        colGradId.setCellValueFactory( new PropertyValueFactory<Grad,Integer>( "id" ) );
        colGradNaziv.setCellValueFactory(new PropertyValueFactory<Grad,String>("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory<Grad,Integer>("brojStanovnika"));
        colGradDrzava.setCellValueFactory(new PropertyValueFactory<Grad,String>("drzava"));
        tableViewGradovi.setItems(dajPodatke());
    }
    public ObservableList<Grad> dajPodatke(){
        ObservableList<Grad> povratni = FXCollections.observableArrayList(new ArrayList<Grad>());
        povratni.addAll(dao.gradovi());
        return povratni;
    }
    public void dodajDrzavu() throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        fxmlLoader.setController(new DrzavaController());
        Parent root = fxmlLoader.load();
        Stage stage= new Stage();
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
    }
    public void dodajGrad() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        fxmlLoader.setController(new GradController());
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
    }
    public void izmijeniGrad() throws IOException {
        Grad grad = (Grad) tableViewGradovi.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        fxmlLoader.setController(new GradController(grad,dao.drzave()));
        Parent root=fxmlLoader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
        stage.setOnHidden(a->{
            ucitajBazu();
        });


    }
    public void obrisiGrad(){
        Grad grad = (Grad) tableViewGradovi.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Potvrda brisanja grada!");
        alert.setContentText("Da li ste sigurni da želite obrisati grad "+grad.getNaziv()+"?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiGrad(grad.getNaziv());
            ucitajBazu();
        } else {

        }


    }

}
