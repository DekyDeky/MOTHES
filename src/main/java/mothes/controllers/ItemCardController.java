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
import mothes.model.bean.Usuario;
import mothes.model.dao.CosmeticoDao;

public class ItemCardController {

    @FXML private ImageView itemImg;
    @FXML private Label itemNome;
    @FXML private Button itemBtnComprar;
    @FXML private Button itemBtnEquipar;
    private Cosmetico cosmetico;
    private Usuario usuario;
    private ImageView mothEquipedHatImageView;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMothEquipedHatImageView(ImageView mothEquipedHatImageView) {
        this.mothEquipedHatImageView = mothEquipedHatImageView;
    }

    public void setItemCard(Cosmetico item) {
        this.cosmetico = item;

        Image img = new Image(item.getImagemUrl());

        itemNome.setText(item.getNome());

        if(item.isComprado()){
            itemBtnComprar.setVisible(false);
            itemBtnEquipar.setVisible(true);
        }else {
            itemBtnComprar.setText("$ " + item.getPreco());
        }

        itemBtnComprar.setCursor(Cursor.HAND);
        itemBtnEquipar.setCursor(Cursor.HAND);

        itemImg.setImage(img);
        itemImg.setFitWidth(100);
        itemImg.setFitHeight(100);
        itemImg.setPreserveRatio(true);
    }

    public void comprarItem(ActionEvent event) {
        if(usuario.getQntMoeda() >= cosmetico.getPreco()){

            itemBtnComprar.setVisible(false);
            itemBtnEquipar.setVisible(true);
            usuario.setQntMoeda(usuario.getQntMoeda() - cosmetico.getPreco());
            cosmetico.setComprado(true);

            if(cosmetico instanceof Acessorio acessorio){
                CosmeticoDao.saveBuyedAcessorios(acessorio, usuario);
            }

        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Dinheiro insuficiente!"
            ).showAndWait();
        }
    }

    public void equiparItem(ActionEvent event) {
        if(cosmetico instanceof Acessorio acessorio){
            if(!acessorio.isUsando()){
                Image hatImg = new Image(acessorio.getImagemUrl());
                cosmetico.setUsando(true);
                mothEquipedHatImageView.setImage(hatImg);
                itemBtnEquipar.setText("Desequipar");
            }else {
                mothEquipedHatImageView.setImage(null);
                itemBtnEquipar.setText("Equipar");
                cosmetico.setUsando(false);
            }
        }
    }

}
