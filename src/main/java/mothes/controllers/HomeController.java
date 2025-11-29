package mothes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mothes.model.bean.Acessorio;
import mothes.model.bean.Estudo;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.model.dao.EstudoDAO;
import mothes.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeController {

    @FXML private AnchorPane homePane; //Tela total

    //Sessão Timer
    @FXML private Label mothNameLabel;
    @FXML private ComboBox<String> studiesComboBox;
    @FXML private Label timerPrincipalLabel;
    @FXML private Label timerNextLabel;
    private ObservableList<String> options = FXCollections.observableArrayList();

    @FXML private Pane homeMenuPane; //Tela de Menus

    //Classe de menus principais e sub-menus
    private PaneManagement allPanes;
    private PaneManagement studyPanes;

    //Todos os Menus
    @FXML private Pane timeConfigPane;
    @FXML private Pane configPane;
    @FXML private Pane furnitureShopMenu;
    @FXML private Pane hatsShopScrollPane;
    @FXML private FlowPane hatsShopContentPane;
    @FXML private Pane infoStagePane;

    //Botão de acesso dos menus
    @FXML private Button hatsShopBtn;
    @FXML private Button furnitureShopBtn;
    @FXML private Button timerConfigBtn;
    @FXML private Button configBtn;
    @FXML private Button infoStageBtn;
    private List<Button> navButtons; //Lista com todos os botões do nav

    //Sub-menus de timeConfig
    @FXML private ScrollPane allStudiesScrollPane;
    @FXML private ScrollPane timeCreatePane;
    @FXML private Button allStudiesBtn;
    @FXML private Button createStudyBtn;

    //Elementos da tela createStudy de timeConfig
    @FXML private TextField titleStudyTextField;
    @FXML private TextField restHourTextField;
    @FXML private TextField restMinTextField;
    @FXML private TextField restSecTextField;
    @FXML private TextField workHourTextField;
    @FXML private TextField workMinTextField;
    @FXML private TextField workSecTextField;
    @FXML private TextField cyclesStudyTextField;
    @FXML private Label timerErroLabel;

    //Elementos da loja
    @FXML private Label moneyLabel;

    //Info Moth Labels
    @FXML private Label actualStageLabel;
    @FXML private Label nextStageLabel;
    @FXML private Label nectarQuantityLabel;
    @FXML private Label stageErrorLabel;
    @FXML private Button feedMothBtn;

    //Dados da sessão atual
    private Usuario sessaoAtual = LocalStorage.loadUser();
    private Mariposa mariposa = LocalStorage.loadMariposa();
    private List<Estudo> estudos;

    private Estudo estudoAtual; // Estudo selecionado atual
    private Temporizador temporizador; //Temporizador do usuário

    private ArrayList<Acessorio> acessorios = new ArrayList<>(); //debug teste


    Stage stage;

    // Inicializador
    public void initialize() throws IOException, SQLException {
        navButtons = List.of(
            timerConfigBtn, hatsShopBtn, /*furnitureShopBtn,*/ configBtn, infoStageBtn
        );

        List<Node> menuPanes = List.of(
                timeConfigPane, configPane, /*furnitureShopMenu,*/ hatsShopScrollPane, infoStagePane
        );

        List<Node> studyMenuPanes = List.of(
                allStudiesScrollPane,
                timeCreatePane
        );

        allPanes = new PaneManagement(navButtons, menuPanes);

        studyPanes = new PaneManagement(
                List.of(allStudiesBtn, createStudyBtn),
                studyMenuPanes
        );

        for (Node pane : menuPanes) {
            pane.setManaged(false);
            pane.setVisible(false);
        }

        for (Node pane : studyMenuPanes) {
            pane.setManaged(false);
            pane.setVisible(false);
        }

        estudos = EstudoDAO.getEstudoByUsuarioID(sessaoAtual.getId());

        for (Estudo e : estudos) {
            options.add(e.getNome());
        }

        studiesComboBox.setItems(options);

        loadItems();

        for(Button btn : navButtons){
            btn.setCursor(Cursor.HAND);
        }

        moneyLabel.setText("$ " + sessaoAtual.getQntMoeda());

        actualStageLabel.setText("Estágio Atual: " + mariposa.getEstagio());
        mothNameLabel.setText(mariposa.getNome());

        allPanes.actualPane(timeConfigPane);
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

    public void showTimeConfig(){
        allPanes.actualPane(timeConfigPane);
        allPanes.selectNavButton(timerConfigBtn, false);

        // Submenu inicial dentro do timeConfigPane
        studyPanes.actualPane(allStudiesScrollPane);
        studyPanes.selectNavButton(allStudiesBtn, true);

//        bringToFront(timeConfigPane);
//        selectNavButton(timerConfigBtn);
    }

    public void showConfig(){
        allPanes.actualPane(configPane);
        allPanes.selectNavButton(configBtn, false);
//        bringToFront(configPane);
//        selectNavButton(configBtn);
    }

    public void showHatsShop(){
        allPanes.actualPane(hatsShopScrollPane);
        allPanes.selectNavButton(hatsShopBtn, false);
//        bringToFront(hatsShopScrollPane);
//        selectNavButton(hatsShopBtn);
    }

    //public void showFunirtureShop(){
    // bringToFront(furnitureShopMenu);
    // selectNavButton(furnitureShopBtn);
    // }

    public void showAllStudies(){
        studyPanes.actualPane(allStudiesScrollPane);
        studyPanes.selectNavButton(allStudiesBtn, true);
//        bringToFront(allStudiesScrollPane);
//        allStudiesBtn.getStyleClass().add("selectedAltButton");
//        createStudyBtn.getStyleClass().remove("selectedAltButton");
    }

    public void showCreateStudy(){
        studyPanes.actualPane(timeCreatePane);
        studyPanes.selectNavButton(createStudyBtn, true);
//        bringToFront(timeCreatePane);
//        allStudiesBtn.getStyleClass().remove("selectedAltButton");
//        createStudyBtn.getStyleClass().add("selectedAltButton");
    }

    public void showInfoMoth(){
        allPanes.actualPane(infoStagePane);
        allPanes.selectNavButton(infoStageBtn, false);
    }

    public void createEstudo() throws ParseException {
         String[] numberStrFields = {
                workHourTextField.getText().trim(), workMinTextField.getText().trim(), workSecTextField.getText().trim(),
                restHourTextField.getText().trim(), restMinTextField.getText().trim(), restSecTextField.getText().trim(),
                cyclesStudyTextField.getText().trim()
         };

        System.out.println(numberStrFields[0]);

        Estudo newEstudo = Validar.validateNewStudy(numberStrFields, titleStudyTextField.getText().trim(), timerErroLabel);

        if(newEstudo != null){
            
            newEstudo.createEstudo(
                    sessaoAtual, estudos,
                    options, studiesComboBox
            );

            titleStudyTextField.setText("");
            workHourTextField.setText("");
            workMinTextField.setText("");
            workSecTextField.setText("");
            restHourTextField.setText("");
            restMinTextField.setText("");
            restSecTextField.setText("");
            restSecTextField.setText("");
            cyclesStudyTextField.setText("");
        };
    }

    public void onComboChange(ActionEvent event){
        if(!estudos.isEmpty()){
            for(Estudo estudo : estudos) {
                if(Objects.equals(studiesComboBox.getValue(), estudo.getNome())){

                    this.estudoAtual = estudo;

                    String tempoEstudo = Converter.IntTimeToStrTime(estudo.getTempoEstudo());
                    String tempoDescanso = Converter.IntTimeToStrTime(estudo.getTempoDescanso());

                    timerPrincipalLabel.setText(tempoEstudo);
                    timerNextLabel.setText(tempoDescanso);

                }
            }
        }
    }

    public void iniciarPomodoro(){

        temporizador = new Temporizador(
                this.estudoAtual.getCiclos(),
                this.estudoAtual.getTempoEstudo(),
                this.estudoAtual.getTempoDescanso(),
                timerPrincipalLabel,
                timerNextLabel
        );

        temporizador.iniciarTemporizador();

    }

    public void pararPomodoro(){
        temporizador.parar();
    }

    public void pausarPomodoro(){
        temporizador.pausar();
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
