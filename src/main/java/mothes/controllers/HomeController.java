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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mothes.model.bean.Acessorio;
import mothes.model.bean.Estudo;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.model.dao.CosmeticoDao;
import mothes.model.dao.EstudoDAO;
import mothes.model.dao.MariposaDAO;
import mothes.model.dao.UsuarioDAO;
import mothes.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeController {

    @FXML private AnchorPane homePane; //Tela total

    @FXML private ImageView mothEquipedHatImageView;

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
    @FXML private ScrollPane timeEditPane;
    @FXML private Button allStudiesBtn;
    @FXML private Button createStudyBtn;
    @FXML private VBox allStudiesVbox;

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

    //Elementos da tela editEstudo de timeConfig
    @FXML private TextField editTitleStudyTextField;
    @FXML private TextField editRestHourTextField;
    @FXML private TextField editRestMinTextField;
    @FXML private TextField editRestSecTextField;
    @FXML private TextField editWorkHourTextField;
    @FXML private TextField editWorkMinTextField;
    @FXML private TextField editWorkSecTextField;
    @FXML private TextField editCyclesStudyTextField;
    @FXML private Label timerEditErroLabel;
    @FXML private Label hiddenIDlabel;


    //Elementos da loja
    @FXML private Label moneyLabel;

    //Info Moth Labels
    @FXML private Label actualStageLabel;
    @FXML private Label nextStageLabel;
    @FXML private Label nectarQuantityLabel;
    @FXML private Label stageErrorLabel;
    @FXML private Label errorFeedLabel;
    @FXML private Button feedMothBtn;

    //Dados da sessão atual
    private Usuario sessaoAtual = LocalStorage.loadUser();
    private Mariposa mariposa = LocalStorage.loadMariposa();
    private List<Estudo> estudos;

    private Estudo estudoAtual; // Estudo selecionado atual
    private Temporizador temporizador; //Temporizador do usuário

    private List<Acessorio> acessorios = new ArrayList<>(); //debug teste

    Stage stage;

    // Inicializador
    public void initialize() throws IOException, SQLException {
        sessaoAtual.setMoneyLabel(moneyLabel);
        mariposa.setNectarQuantityLabel(nectarQuantityLabel);
        nextStageLabel.setText("Quantidade Necessária para o próximo nível: " + mariposa.getPrecoEstagio());

        navButtons = List.of(
            timerConfigBtn, hatsShopBtn, /*furnitureShopBtn,*/ configBtn, infoStageBtn
        );

        List<Node> menuPanes = List.of(
                timeConfigPane, configPane, /*furnitureShopMenu,*/ hatsShopScrollPane, infoStagePane
        );

        List<Node> studyMenuPanes = List.of(
                allStudiesScrollPane,
                timeCreatePane,
                timeEditPane
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

        loadEstudos();
        loadItems();

        for(Button btn : navButtons){
            btn.setCursor(Cursor.HAND);
        }

        moneyLabel.setText("$ " + sessaoAtual.getQntMoeda());

        actualStageLabel.setText("Estágio Atual: " + mariposa.getEstagio());
        mothNameLabel.setText(mariposa.getNome());

        showTimeConfig();
    }

    private void loadItems() throws IOException, SQLException {

        acessorios = CosmeticoDao.getAcessorios(sessaoAtual);

        for(Acessorio acessorio : acessorios) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/itemCard.fxml"));
            Parent card = loader.load();

            ItemCardController controller = loader.getController();
            controller.setItemCard(acessorio);
            controller.setUsuario(sessaoAtual);
            controller.setMothEquipedHatImageView(mothEquipedHatImageView);

            hatsShopContentPane.getChildren().add(card);
        }
    }

    public void loadEstudos() throws IOException {
        allStudiesVbox.getChildren().clear();

        for(Estudo estudo : estudos) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mothes/estudoCard.fxml"));
            Parent card = loader.load();

            EstudoCardController controller = loader.getController();
            controller.setEstudoCard(estudo, estudos, estudoAtual, this);

            allStudiesVbox.getChildren().add(card);

            options.add(estudo.getNome());
        }

        studiesComboBox.setItems(options);
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
    }

    public void showConfig(){
        allPanes.actualPane(configPane);
        allPanes.selectNavButton(configBtn, false);
    }

    public void showHatsShop(){
        allPanes.actualPane(hatsShopScrollPane);
        allPanes.selectNavButton(hatsShopBtn, false);
    }

    public void showAllStudies(){
        studyPanes.actualPane(allStudiesScrollPane);
        studyPanes.selectNavButton(allStudiesBtn, true);
    }

    public void showEditEscudo(Estudo estudo){
        studyPanes.actualPane(timeEditPane);
        studyPanes.selectNavButton(null, true);

        editTitleStudyTextField.setText(estudo.getNome());
        hiddenIDlabel.setText(String.valueOf(estudo.getIdEstudo()));

        editRestHourTextField.setText(Converter.getHoursFromStrTime(estudo.getTempoDescanso()));
        editRestMinTextField.setText(Converter.getMinutesFromStrTime(estudo.getTempoDescanso()));
        editRestSecTextField.setText(Converter.getSecondsFromStrTime(estudo.getTempoDescanso()));

        editWorkHourTextField.setText(Converter.getHoursFromStrTime(estudo.getTempoEstudo()));
        editWorkMinTextField.setText(Converter.getMinutesFromStrTime(estudo.getTempoEstudo()));
        editWorkSecTextField.setText(Converter.getSecondsFromStrTime(estudo.getTempoEstudo()));

        editCyclesStudyTextField.setText(String.valueOf(estudo.getCiclos()));

    }

    public void showCreateStudy(){
        studyPanes.actualPane(timeCreatePane);
        studyPanes.selectNavButton(createStudyBtn, true);
    }

    public void showInfoMoth(){
        allPanes.actualPane(infoStagePane);
        allPanes.selectNavButton(infoStageBtn, false);
    }

    public void createEstudo() throws ParseException, IOException {
         String[] numberStrFields = {
                workHourTextField.getText().trim(), workMinTextField.getText().trim(), workSecTextField.getText().trim(),
                restHourTextField.getText().trim(), restMinTextField.getText().trim(), restSecTextField.getText().trim(),
                cyclesStudyTextField.getText().trim()
         };

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

        loadEstudos();
    }

    public void editEstudo() throws SQLException, IOException {
        String[] numberStrFields = {
                editWorkHourTextField.getText().trim(), editWorkMinTextField.getText().trim(), editWorkSecTextField.getText().trim(),
                editRestHourTextField.getText().trim(), editRestMinTextField.getText().trim(), editRestSecTextField.getText().trim(),
                editCyclesStudyTextField.getText().trim()
        };

        boolean haveErrors = false;
        String errorLabel = "";
        int idEstudo = Integer.parseInt(hiddenIDlabel.getText().trim());

        for (int i = 0; i < numberStrFields.length; i++) {
            if(Validar.validateNumberStrFields(numberStrFields[i]) == null){
                errorLabel = "Campo numérico inválido";
                haveErrors = true;
                break;
            }else {
                numberStrFields[i] = Validar.validateNumberStrFields(numberStrFields[i]);
            }
        }

        if(editTitleStudyTextField.getText().trim().isEmpty()){
            if(haveErrors){
                errorLabel = errorLabel + ", o título é obrigatório";
            }else {
                haveErrors = true;
                errorLabel = "O título é obrigatório";
            }
        }

        if(numberStrFields[6].isEmpty()){
            if(haveErrors){
                errorLabel = errorLabel + ", os ciclos são obrigatórios";
            }else {
                haveErrors = true;
                errorLabel = "Os ciclos são obrigatórios";
            }
        }

        if(Integer.parseInt(numberStrFields[6]) <= 0){
            if(haveErrors){
                errorLabel = errorLabel + ", a quant. de ciclos deve ser maior que 0";
            }else {
                haveErrors = true;
                errorLabel = "A quant. de ciclos deve ser maior que 0";
            }
        }

        if(haveErrors) {
            timerErroLabel.setText(errorLabel + "!");
            return;
        }

        Estudo oldEstudo = null;

        for(Estudo e : estudos){
            if(e.getIdEstudo() == idEstudo){
                oldEstudo = e;
                break;
            }
        }

        String novoNome = editTitleStudyTextField.getText().trim();
        int novoCiclo = Integer.parseInt(numberStrFields[6]);
        int novoTempoEstudo = Converter.valuesToSeconds(
                Integer.parseInt(numberStrFields[0]),
                Integer.parseInt(numberStrFields[1]),
                Integer.parseInt(numberStrFields[2])
        );
        int novoTempoDescanco = Converter.valuesToSeconds(
                Integer.parseInt(numberStrFields[3]),
                Integer.parseInt(numberStrFields[4]),
                Integer.parseInt(numberStrFields[5])
        );

        if(oldEstudo == null){
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao editar estudo.\nErro: Estudo antigo não encontrado."
            ).showAndWait();
        }else {
            if(EstudoDAO.editEstudo(novoNome, novoCiclo, novoTempoEstudo, novoTempoDescanco, idEstudo)){
                oldEstudo.editEstudo(
                        novoNome,
                        novoCiclo,
                        novoTempoEstudo,
                        novoTempoDescanco
                );

                loadEstudos();

                new Alert(Alert.AlertType.INFORMATION,
                        "Estudo editado com sucesso!"
                ).showAndWait();

                showAllStudies();
            }

        }











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
                timerNextLabel,
                sessaoAtual,
                mariposa
        );

        temporizador.iniciarTemporizador();

    }

    public void pararPomodoro(){
        temporizador.parar();
    }

    public void pausarPomodoro(){
        temporizador.pausar();
    }

    public void alimentarMariposa() {
        mariposa.alimentar(nectarQuantityLabel, nextStageLabel, actualStageLabel, errorFeedLabel);
    }

    public void closeProgram(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Saindo...");
        alert.setHeaderText("Tem certeza que deseja sair?");

        if(alert.showAndWait().get() == ButtonType.OK){
            UsuarioDAO.updateUser(sessaoAtual);
            MariposaDAO.updateMariposa(mariposa);
            stage = (Stage) homePane.getScene().getWindow();
            System.out.println("Você saiu do programa!");
            stage.close();
        }

    }

}
