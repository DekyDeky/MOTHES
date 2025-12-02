package mothes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mothes.model.bean.Usuario;
import mothes.model.dao.UsuarioDAO;
import mothes.util.PasswordHash;
import mothes.util.Validar;

import java.util.Base64;

public class EditUserController {

    @FXML private TextField apelidoTextField;
    @FXML private Label apelidoErrorLabel;

    @FXML private TextField emailTextField;
    @FXML private Label emailErrorLabel;

    @FXML private TextField oldSenhaTextField;
    @FXML private Label oldSenhaErrorLabel;

    @FXML private TextField senhaTextField;
    @FXML private Label senhaErrorLabel;

    @FXML private TextField confirmSenhaTextField;
    @FXML private Label confirmSenhaErrorLabel;

    private Usuario sessaoAtual;
    private Stage stage;

    public void editUser() throws Exception {
        apelidoErrorLabel.setText("");
        emailErrorLabel.setText("");
        senhaErrorLabel.setText("");
        confirmSenhaErrorLabel.setText("");
        oldSenhaErrorLabel.setText("");

        String newApelido = apelidoTextField.getText().trim();
        String newEmail = emailTextField.getText().trim();
        String newPassword = senhaTextField.getText().trim();
        String newConfirmPassword = confirmSenhaTextField.getText().trim();
        String oldPassword = oldSenhaTextField.getText().trim();

        boolean valid = true;

        String apelidoError = Validar.apelidoIsValid(newApelido);
        if (!apelidoError.isEmpty()) {
            apelidoErrorLabel.setText(apelidoError);
            valid = false;
        }

        String emailError = Validar.emailIsValid(newEmail);
        if (!emailError.isEmpty()) {
            emailErrorLabel.setText(emailError);
            valid = false;
        }

        String passwordError = Validar.passwordIsValid(newPassword, newConfirmPassword);
        if (!passwordError.isEmpty()) {
            senhaErrorLabel.setText(passwordError);
            confirmSenhaErrorLabel.setText(passwordError);
            valid = false;
        }

        String oldPasswordError = Validar.oldPassworldIsValid(oldPassword);
        if (!oldPasswordError.isEmpty()) {
            oldSenhaErrorLabel.setText(oldPasswordError);
            valid = false;
        }

        if (!valid) {
            return;
        }

        if (!PasswordHash.checkPassword(sessaoAtual.getSalt64(), oldPassword, sessaoAtual.getSenha())) {
            oldSenhaErrorLabel.setText("Senha incorreta!");
            return;
        }

        if (!newApelido.isEmpty()) {
            sessaoAtual.setApelido(newApelido);
        }

        if (!newEmail.isEmpty()) {
            sessaoAtual.setEmail(newEmail);
        }

        if (!newPassword.isEmpty()) {
            byte[] salt = PasswordHash.generateSalt();
            String newHashedPassword = PasswordHash.hashPassword(newPassword, salt);
            String newSaltBase64 = Base64.getEncoder().encodeToString(salt);

            sessaoAtual.setSenha(newHashedPassword);
            sessaoAtual.setSalt64(newSaltBase64);
        }

        if (UsuarioDAO.editUser(sessaoAtual)) {
            new Alert(Alert.AlertType.INFORMATION, "Usuário editado com sucesso!").showAndWait();
        }

        if (stage != null) {
            stage.close();
        }

    }

    public void setSessaoAtual(Usuario sessaoAtual) {
        this.sessaoAtual = sessaoAtual;
        this.apelidoTextField.setText(sessaoAtual.getApelido());
        this.emailTextField.setText(sessaoAtual.getEmail());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
