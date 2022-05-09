package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class SpelPaneel extends BorderPane {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private SpelBordPaneel spelBordPaneel;
    private SpelScorebladPaneel spelScorebladPaneel;

    /**
     * UC3: constructor voor het spelpaneel
     *
     * @param hoofdPaneel      om hoofdscherm aan te passen
     * @param menuPaneel       voor terugkeer naar menupaneel
     * @param domeinController de dc voor gebruik
     */
    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        voegComponentenToe();
    }

    /**
     * UC4, normaal verloop: creeert de nodige elementen die deel zullen uitmaken van het spelpaneel
     * en zet ze op de juiste plaatsen.
     * 
     * Hulpmethode voor constructor
     */
    private void voegComponentenToe() {
        // instantie elementen
        SpelLogoPaneel spelLogoPaneel = new SpelLogoPaneel();
        this.spelScorebladPaneel = new SpelScorebladPaneel(domeinController);
        SpelSpelerPaneel spelSpelerPaneel = new SpelSpelerPaneel(hoofdPaneel, menuPaneel, spelScorebladPaneel,
                domeinController, this);
        spelBordPaneel = new SpelBordPaneel(domeinController, spelSpelerPaneel);

        // styling
        setAlignment(spelScorebladPaneel, Pos.BASELINE_LEFT);
        setAlignment(spelBordPaneel, Pos.CENTER);
        this.setStyle("-fx-background-color: #59981A");

        // insert in GUI
        this.setTop(spelLogoPaneel);
        this.setCenter(spelBordPaneel);
        this.setLeft(spelSpelerPaneel);
        this.setRight(spelScorebladPaneel);
    }

    /**
     * UC4, normaal verloop: getter voor SpelScoreBladPaneel voor dataflow.
     */
    public SpelScorebladPaneel getSpelScorebladPaneel() {
        return spelScorebladPaneel;
    }

    /**
     * UC4, normaal verloop: setter voor nieuw spelbord indien spel gecanceled wordt.
     */
    public SpelBordPaneel getSpelBordPaneel() {
        return spelBordPaneel;
    }
}