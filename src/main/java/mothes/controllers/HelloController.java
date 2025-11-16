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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloController {
    /*
    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;*/

    private Stage stage;
    private Scene scene;
    private Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    public void login(ActionEvent event) throws IOException {

        /*String email = emailField.getText();
        String senha = passwordField.getText();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/home.fxml"));
        root = loader.load();

        root.setOnMousePressed(Mevent -> {
            xOffset = Mevent.getSceneX();
            yOffset = Mevent.getSceneY();
        });

        root.setOnMouseDragged(Mevent -> {
            stage.setX(Mevent.getScreenX() - xOffset);
            stage.setY(Mevent.getScreenY() - yOffset);
        });

        HomeController homePage = loader.getController();
        //homePage.displayTeste(email, senha);

        stage = new Stage();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        String style = this.getClass().getResource("/mothes/styles/home.css").toExternalForm();
        scene.getStylesheets().add(style);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

        Stage stageAnterior = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAnterior.close();

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
