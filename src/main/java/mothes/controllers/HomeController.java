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
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;
import mothes.util.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController {

    @FXML private AnchorPane homePane;
    @FXML private ComboBox<String> StudiesComboBox;

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

    @FXML private Label moneyLabel;

    private Usuario sessaoAtual = LocalStorage.loadUser();

    ArrayList<Acessorio> acessorios = new ArrayList<>();

    Stage stage;

    public void initialize() throws IOException {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Teste 1",
                "Teste 2"
        );

        StudiesComboBox.setItems(options);

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
