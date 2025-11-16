package mothes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private AnchorPane homePane;
    @FXML
    private Button exitBtn;

    @FXML
    private Label emailTeste;

    @FXML
    private Label senhaTeste;

    Stage stage;

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

    public void displayTeste(String email, String senha){
        emailTeste.setText("Email: " + email);
        senhaTeste.setText("Senha: " + senha);
    }

}
