package gui;

import domein.DomeinController;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public class SpelPaneel extends GridPane {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;

    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
    }
}
