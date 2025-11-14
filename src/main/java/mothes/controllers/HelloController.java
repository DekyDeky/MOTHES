package mothes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {

        String email = emailField.getText();
        String senha = passwordField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/home.fxml"));
        root = loader.load();

        HomeController homePage = loader.getController();
        homePage.displayTeste(email, senha);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void gotoRegister(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/registrar.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String style = this.getClass().getResource("/mothes/styles/registrar.css").toExternalForm();
        scene.getStylesheets().add(style);

        stage.setScene(scene);
        stage.show();

    }
}
