package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SpelPaneel extends BorderPane {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private SpelLogoPaneel spelLogoPaneel;
    private SpelScorebladPaneel spelScorebladPaneel;
    private SpelBordPaneel spelBordPaneel;
    private SpelSpelerPaneel spelSpelerPaneel;

    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        setSpelSchermen();
    }
    
    private void setSpelSchermen() {
        this.spelLogoPaneel = new SpelLogoPaneel(domeinController);
        this.spelScorebladPaneel = new SpelScorebladPaneel(domeinController);
        this.spelBordPaneel = new SpelBordPaneel(domeinController);
        this.spelSpelerPaneel = new SpelSpelerPaneel(hoofdPaneel, menuPaneel, domeinController);
        this.setTop(spelLogoPaneel);
        this.setCenter(spelBordPaneel);
        this.setLeft(spelSpelerPaneel);
        this.setRight(spelScorebladPaneel);
        setAlignment(spelScorebladPaneel, Pos.BASELINE_LEFT);
        setAlignment(spelBordPaneel,Pos.CENTER);
        this.setStyle("-fx-background-color: #566454");
    }

    public SpelScorebladPaneel getSpelScorebladPaneel() {
        return spelScorebladPaneel;
    }

    public SpelLogoPaneel getSpelLogoPaneel() {
        return spelLogoPaneel;
    }

    public SpelBordPaneel getSpelBordPaneel() {
        return spelBordPaneel;
    }

    public SpelSpelerPaneel getSpelSpelerPaneel() {
        return spelSpelerPaneel;
    }
}