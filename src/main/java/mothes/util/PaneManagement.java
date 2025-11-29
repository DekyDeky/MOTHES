package mothes.util;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class PaneManagement {

    private List<Button> navButtons;
    private List<Node> menuPanes;

    public PaneManagement(List<Button> navButtons, List<Node> menuPanes) {
        this.navButtons = navButtons;
        this.menuPanes = menuPanes;
    }

    public void selectNavButton(Button selected, boolean isAlt) {
        for (Button btn : navButtons) {
            if (btn == selected) {
                if(!isAlt){
                    if (!btn.getStyleClass().contains("selectedButton")) {
                        btn.getStyleClass().add("selectedButton");
                    }
                }else {
                    if (!btn.getStyleClass().contains("selectedAltButton")) {
                        btn.getStyleClass().add("selectedAltButton");
                    }
                }
            } else {
                btn.getStyleClass().remove("selectedButton");
                btn.getStyleClass().remove("selectedAltButton");
            }
        }
    }

    public void actualPane(Node selected){
        for (Node pane : menuPanes) {
            boolean active = pane == selected;
            pane.setVisible(active);
            pane.setManaged(active);
        }
    }


}
