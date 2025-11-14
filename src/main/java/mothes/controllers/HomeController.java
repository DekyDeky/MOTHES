package mothes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    Label emailTeste;

    @FXML
    Label senhaTeste;

    public void displayTeste(String email, String senha){
        emailTeste.setText("Email: " + email);
        senhaTeste.setText("Senha: " + senha);
    }

}
