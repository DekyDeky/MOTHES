package mothes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mothes.model.bean.Estudo;
import mothes.model.dao.EstudoDAO;
import mothes.util.Converter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EstudoCardController {

    @FXML private Label estudoNome;
    @FXML private Label estudoTempo;
    @FXML private Label estudoDescancoTempo;
    @FXML private Label estudoCiclos;
    private Estudo estudo;
    private List<Estudo> estudos;
    private Estudo estudoAtual;
    private HomeController homeController;

    public void setEstudoCard(Estudo estudo, List<Estudo> estudos, Estudo estudoAtual, HomeController homeController){
        estudoNome.setText(estudo.getNome());
        estudoTempo.setText("Estudar por: " + Converter.IntTimeToStrTime(estudo.getTempoEstudo()));
        estudoDescancoTempo.setText("Descançar por: " + Converter.IntTimeToStrTime(estudo.getTempoDescanso()));
        estudoCiclos.setText("Repetir por: " + estudo.getCiclos());
        this.estudo = estudo;
        this.estudos = estudos;
        this.estudoAtual = estudoAtual;
        this.homeController = homeController;
    }

    public void editEstudo() {
        homeController.showEditEscudo(this.estudo);
    }

    public void deleteEstudo() throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletando " + estudo.getNome() + "...");
        alert.setHeaderText("Tem certeza que deseja deletar esse estudo?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            estudos.remove(estudo);

            if(estudoAtual == estudo){
                estudoAtual = null;
            }

            EstudoDAO.deleteEstudo(estudo);

            homeController.loadEstudos();

        }
    }


}
