package mothes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.model.dao.MariposaDAO;
import mothes.util.PasswordHash;

public class EditMothController {

    @FXML private TextField oldSenhaTextField;
    @FXML private Label oldSenhaErrorLabel;

    @FXML private TextField nomeMariposaTextField;
    @FXML private Label nomeMariposaErrorLabel;

    private Usuario sessaoAtual;
    private Mariposa mariposa;
    private Stage stage;
    private Label mothNameLabel;

    public void editMoth() throws Exception {
        oldSenhaErrorLabel.setText("");
        nomeMariposaErrorLabel.setText("");

        String oldPassword = oldSenhaTextField.getText().trim();
        String newMothName = nomeMariposaTextField.getText().trim();

        if(newMothName.isEmpty()){
            nomeMariposaErrorLabel.setText("Insira o novo nome!");
            return;
        }

        if (!PasswordHash.checkPassword(sessaoAtual.getSalt64(), oldPassword, sessaoAtual.getSenha())) {
            oldSenhaErrorLabel.setText("Senha incorreta!");
            return;
        }

        if(MariposaDAO.changeMariposaName(newMothName, mariposa.getIdMariposa())) {
            mariposa.setNome(newMothName);
            new Alert(Alert.AlertType.INFORMATION, "Mariposa editada com sucesso!").showAndWait();
            mothNameLabel.setText(newMothName);
        }

        if(stage != null){
            stage.close();
        }

    }

    public void setEditMoth(Usuario sessaoAtual, Mariposa mariposa, Stage stage, Label mothNameLabel){
        this.sessaoAtual = sessaoAtual;
        this.mariposa = mariposa;
        this.stage = stage;
        this.mothNameLabel = mothNameLabel;

        this.nomeMariposaTextField.setText(mariposa.getNome());
    }


}
