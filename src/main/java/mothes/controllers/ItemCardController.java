package mothes.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mothes.model.bean.Cosmetico;

public class ItemCardController {

    @FXML private ImageView itemImg;
    @FXML private Label itemNome;
    @FXML private Button itemBtnComprar;

    public void setItemCard(Cosmetico item) {
        Image img = new Image(item.getImagemUrl());

        itemNome.setText(item.getNome());
        itemBtnComprar.setText("$ " + item.getPreco());
        itemBtnComprar.setCursor(Cursor.HAND);
        itemImg.setImage(img);
        itemImg.setFitWidth(100);
        itemImg.setFitHeight(100);
        itemImg.setPreserveRatio(true);
    }
}
