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
import mothes.model.bean.Usuario;
import mothes.model.dao.UsuarioDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RegistrarController {

    //Email Things
    @FXML
    Label registerEmailLabel;

    @FXML
    Label registerEmailErrorLabel;

    @FXML
    TextField registerEmailField;

    //Password Things
    @FXML
    Label registerPasswordLabel;

    @FXML
    Label registerPasswordErrorLabel;

    @FXML
    PasswordField registerPasswordField;

    //Confirm Password Things
    @FXML
    Label registerConfPasswordLabel;

    @FXML
    Label registerConfPasswordErrorLabel;

    @FXML
    PasswordField registerConfPasswordField;

    //Nickname Things
    @FXML
    Label registerNicknameLabel;

    @FXML
    Label registerNicknameErrorLabel;

    @FXML
    TextField registerNicknameField;

    //MothName Things

    @FXML
    Label registerMothNameLabel;

    @FXML
    Label registerMothNameErrorLabel;

    @FXML
    TextField registerMothNameField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void resetValidation(){
        Label[] registerLabels = {registerEmailLabel, registerPasswordLabel, registerConfPasswordLabel, registerNicknameLabel, registerMothNameLabel};
        Label[] registerErrorLabels = {registerEmailErrorLabel, registerPasswordErrorLabel, registerConfPasswordErrorLabel, registerNicknameErrorLabel, registerMothNameErrorLabel};

        for(Label label : registerLabels){
            label.getStyleClass().remove("error-label");
        }

        for(Label label : registerErrorLabels){
            label.setText("");
        }
    }

    public boolean validation(String email, String senha, String confSenha, String apelido, String nomeMariposa){

        this.resetValidation();

        boolean isError = false;

        registerEmailErrorLabel.setText("");

        if(!email.contains("@") || email.isEmpty()){
            isError = true;
            registerEmailLabel.getStyleClass().add("error-label");
            registerEmailErrorLabel.setText("Email Inválido!");
        }

        if(!Objects.equals(senha, confSenha)){
            isError = true;
            registerPasswordLabel.getStyleClass().add("error-label");
            registerConfPasswordLabel.getStyleClass().add("error-label");

            registerPasswordErrorLabel.setText("As senhas não são iguais!");
            registerConfPasswordErrorLabel.setText("As senhas não são iguais");
        }

        if(senha.isEmpty()){
            registerPasswordLabel.getStyleClass().add("error-label");
            registerPasswordErrorLabel.setText("A senha está vazia!");
        }

        if(confSenha.isEmpty()){
            registerConfPasswordLabel.getStyleClass().add("error-label");
            registerConfPasswordErrorLabel.setText("As senha está vazia");
        }

        if(apelido.length() > 45 || apelido.isEmpty()){
            isError = true;
            registerNicknameLabel.getStyleClass().add("error-label");
            registerNicknameErrorLabel.setText("Apelido é inválido!");
        }

        if(nomeMariposa.length() > 45 || nomeMariposa.isEmpty()){
            isError = true;
            registerMothNameLabel.getStyleClass().add("error-label");
            registerMothNameErrorLabel.setText("O Nome da Mariposa é inválido!");
        }

        return isError;

    }

    public void cancel(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/hello-view.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void register(ActionEvent event) throws IOException {

        String email = registerEmailField.getText();
        String senha = registerPasswordField.getText();
        String confSenha = registerConfPasswordField.getText();
        String apelido = registerNicknameField.getText();
        String nomeMariposa = registerMothNameField.getText();

        if(!validation(email, senha, confSenha, apelido, nomeMariposa)){
            Usuario novoUsuario = new Usuario(0, apelido, email, senha, 0);
            UsuarioDAO registarUsuario = new UsuarioDAO();

            registarUsuario.createUser(novoUsuario);

            this.cancel(event);
        }

    }
}
