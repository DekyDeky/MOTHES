package mothes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mothes.model.bean.Acessorio;
import mothes.model.bean.Cosmetico;
import mothes.model.bean.Decoracao;
import mothes.model.bean.Usuario;
import mothes.model.dao.CosmeticoDAO;

public class ItemCardController {

    @FXML private ImageView itemImg;
    @FXML private Label itemNome;
    @FXML private Button itemBtnComprar;
    @FXML private Button itemBtnEquipar;
    private Cosmetico cosmetico;
    private Usuario usuario;
    private ImageView imageUsed;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setImageUsed(ImageView imageUsed) {
        this.imageUsed = imageUsed;
    }

    public void setItemCard(Cosmetico item) {
        this.cosmetico = item;

        Image img = new Image(item.getImagemUrl());

        if(item instanceof Acessorio){
            itemImg.setImage(img);
            itemImg.setFitWidth(100);
            itemImg.setFitHeight(100);
            itemImg.setPreserveRatio(true);
        }else if (item instanceof Decoracao){
            itemImg.setImage(img);
            itemImg.setFitWidth(100);
            itemImg.setFitHeight(160);
            itemImg.setPreserveRatio(true);
        }

        itemNome.setText(item.getNome());

        if(item.isComprado()){
            itemBtnComprar.setVisible(false);
            itemBtnEquipar.setVisible(true);
        }else {
            itemBtnComprar.setText("$ " + item.getPreco());
        }

        itemBtnComprar.setCursor(Cursor.HAND);
        itemBtnEquipar.setCursor(Cursor.HAND);


    }

    public void comprarItem(ActionEvent event) {
        if(this.usuario.getQntMoeda() >= this.cosmetico.getPreco()){

            this.itemBtnComprar.setVisible(false);
            this.itemBtnEquipar.setVisible(true);
            this.usuario.setQntMoeda(this.usuario.getQntMoeda() - this.cosmetico.getPreco());
            this.cosmetico.setComprado(true);

            CosmeticoDAO.saveBuyedCosmetic(this.cosmetico, this.usuario);


        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Dinheiro insuficiente!"
            ).showAndWait();
        }
    }

    public void equiparItem(ActionEvent event) {

        if(!cosmetico.isUsando()){
            Image cosmImg = new Image(cosmetico.getImagemUrl());
            cosmetico.setUsando(true);
            imageUsed.setImage(cosmImg);
            itemBtnEquipar.setText("Desequipar");
        }else {
            imageUsed.setImage(null);
            itemBtnEquipar.setText("Equipar");
            cosmetico.setUsando(false);
        }

    }

}
