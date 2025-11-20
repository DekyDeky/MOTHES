package mothes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mothes.model.bean.Mariposa;
import mothes.util.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    @FXML
    private AnchorPane homePane;
    @FXML
    private Button exitBtn;
    @FXML
    private Label nomeMariposaLabel;

    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        Mariposa mariposa = LocalStorage.loadMariposa();

        if (mariposa != null) {
            nomeMariposaLabel.setText(mariposa.getNome());
        } else {
            nomeMariposaLabel.setText("Erro");
        }
    }

    public void closeProgram(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Saindo...");
        alert.setHeaderText("Tem certeza que deseja sair?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) homePane.getScene().getWindow();
            System.out.println("Você saiu do programa!");
            stage.close();
        }

    }


}
