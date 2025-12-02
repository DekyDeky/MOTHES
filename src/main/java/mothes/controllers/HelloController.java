package mothes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.model.dao.MariposaDAO;
import mothes.model.dao.UsuarioDAO;
import mothes.util.LocalStorage;
import mothes.util.PasswordHash;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class HelloController {

    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label loginErrorLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    public boolean checkLogin(String email, String senha, Usuario loginUser, Mariposa mariposa) throws Exception {

        String senhaHash = loginUser.getSenha();
        String salt64 = loginUser.getSalt64();

        byte[] salt = Base64.getDecoder().decode(salt64);

        String senhaLoginHash = PasswordHash.hashPassword(senha, salt);

        if(senhaHash.equals(senhaLoginHash)){
            loginErrorLabel.setText("");

            if(mariposa == null){
                return false;
            }

            return true;
        }else {
            loginErrorLabel.setText("Email ou senha incorretos!");
            return false;
        }

    }

    public void login(ActionEvent event) throws Exception {

        byte[] saltTeste = PasswordHash.generateSalt();
        String senhaTest = PasswordHash.hashPassword("123Deky!Harvey", saltTeste);
        String saltTesteStr = Base64.getEncoder().encodeToString(saltTeste);

        //Pega o email e senha do usuário
        String email = emailField.getText();
        String senha = passwordField.getText();

        UsuarioDAO loginUsuarios = new UsuarioDAO();
        Usuario loginUser = loginUsuarios.getUserByEmail(email);

        if (loginUser == null){
            loginErrorLabel.setText("Email ou senha incorretos!");
            return;
        }

        Mariposa mariposa = MariposaDAO.getMariposaByUsuarioID(loginUser.getId());

        if(this.checkLogin(email, senha, loginUser, mariposa)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/home.fxml"));

            loader.setControllerFactory(_ -> new HomeController(loginUser, mariposa));

            root = loader.load();

            HomeController homePage = loader.getController();

            root.setOnMousePressed(Mevent -> {
                xOffset = Mevent.getSceneX();
                yOffset = Mevent.getSceneY();
            });

            root.setOnMouseDragged(Mevent -> {
                stage.setX(Mevent.getScreenX() - xOffset);
                stage.setY(Mevent.getScreenY() - yOffset);
            });

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
