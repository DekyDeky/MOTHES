package mothes.controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import mothes.model.bean.Acessorio;
import mothes.model.bean.Estudo;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.model.dao.EstudoDAO;
import mothes.util.Converter;
import mothes.util.LocalStorage;
import mothes.util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController {

    @FXML private AnchorPane homePane;
    @FXML private ComboBox<String> studiesComboBox;
    @FXML private Label timerPrincipalLabel;
    @FXML private Label timerNextLabel;

    @FXML private Pane homeMenuPane;

    @FXML private Pane timeConfigPane;
    @FXML private Pane configPane;
    @FXML private Pane furnitureShopMenu;
    @FXML private Pane hatsShopScrollPane;
    @FXML private FlowPane hatsShopContentPane;

    @FXML private Button hatsShopBtn;
    @FXML private Button furnitureShopBtn;
    @FXML private Button timerConfigBtn;
    @FXML private Button configBtn;

    @FXML private TextField titleStudyTextField;
    @FXML private TextField restHourTextField;
    @FXML private TextField restMinTextField;
    @FXML private TextField restSecTextField;
    @FXML private TextField workHourTextField;
    @FXML private TextField workMinTextField;
    @FXML private TextField workSecTextField;
    @FXML private TextField cyclesStudyTextField;
    @FXML private Label timerErroLabel;
    @FXML private Button saveStudyBtn;


    @FXML private Label moneyLabel;

    private Usuario sessaoAtual = LocalStorage.loadUser();
    private List<Estudo> estudos;

    ArrayList<Acessorio> acessorios = new ArrayList<>();

    Stage stage;

    public void initialize() throws IOException, SQLException {
        estudos = EstudoDAO.getEstudoByUsuarioID(sessaoAtual.getId());

        ObservableList<String> options = FXCollections.observableArrayList();

        for (Estudo e : estudos) {
            options.add(e.getNome());
        }

        studiesComboBox.setItems(options);

        loadItems();

        hatsShopBtn.setCursor(Cursor.HAND);
        furnitureShopBtn.setCursor(Cursor.HAND);
        timerConfigBtn.setCursor(Cursor.HAND);
        configBtn.setCursor(Cursor.HAND);

        moneyLabel.setText("$ " + sessaoAtual.getQntMoeda());
    }

    private void loadItems() throws IOException {
        Acessorio sillyHat = new Acessorio(0, "Silly Hat", "mothes/assets/hats/sillyhat.png", 10, false, false, "head");
        Acessorio scaniaHat = new Acessorio(0, "Scania Hat", "mothes/assets/hats/scania.png", 25, false, false, "head");
        acessorios.add(sillyHat);
        acessorios.add(scaniaHat);

        for(Acessorio acessorio : acessorios) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/itemCard.fxml"));
            Parent card = loader.load();

            ItemCardController controller = loader.getController();
            controller.setItemCard(acessorio);

            hatsShopContentPane.getChildren().add(card);
        }
    }

    public void showMenu(){
        homeMenuPane.setVisible(!homeMenuPane.isVisible());
    }

    public void bringToFront(Pane pane){
        pane.toFront();
        pane.setVisible(true);
    }

    public void showTimeConfig(){
        bringToFront(timeConfigPane);
    }
    public void showConfig(){ bringToFront(configPane); }
    public void showHatsShop(){bringToFront(hatsShopScrollPane);}
    //public void showFunirtureShop(){bringToFront(furnitureShopMenu);}

    public void createEstudo() throws ParseException {
        String estudoNome = titleStudyTextField.getText().trim();

        String horaTrabStr = workHourTextField.getText().trim();
        String minTrabStr = workMinTextField.getText().trim();
        String secTrabStr = workSecTextField.getText().trim();

        String horaDescStr = restHourTextField.getText().trim();
        String minDescStr = restMinTextField.getText().trim();
        String secDescStr = restSecTextField.getText().trim();

        String cyclesStr = cyclesStudyTextField.getText().trim();

        String[] numberStrFields = {horaTrabStr, minTrabStr, secTrabStr, horaDescStr, minDescStr, secDescStr, cyclesStr};

        if(horaTrabStr.isEmpty()){
            horaTrabStr = "00";
        }

        if(horaDescStr.isEmpty()){
            horaDescStr = "00";
        }

        for(String str : numberStrFields) {
            if(!Validation.isNumeric(str)){
                timerErroLabel.setText("Campo numérico inválido!");
                return;
            }
        }

        if(estudoNome.isEmpty()){
            timerErroLabel.setText("O título é obrigatório");
            return;
        }


        Estudo newEstudo = new Estudo(
                estudoNome,
                Integer.parseInt(cyclesStr),
                Converter.StrToSQLTime(horaTrabStr, minTrabStr, secTrabStr),
                Converter.StrToSQLTime(horaDescStr, minDescStr, secDescStr)
        );

        EstudoDAO.createEstudo(newEstudo, sessaoAtual.getId());

    }

    public void onComboChange(ActionEvent event){
        for(Estudo estudo : estudos) {
            if(Objects.equals(studiesComboBox.getValue(), estudo.getNome())){

                String tempoEstudo = estudo.getTempoEstudo().toString();
                String tempoDescanso = estudo.getTempoDescanso().toString();

                if(tempoEstudo.startsWith("00")){
                    tempoEstudo = tempoEstudo.substring(3);
                }

                if(tempoDescanso.startsWith("00")){
                    tempoDescanso = tempoDescanso.substring(3);
                }

                timerPrincipalLabel.setText(tempoEstudo);
                timerNextLabel.setText(tempoDescanso);
            }
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
