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

    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        voegComponentenToe();
    }

    /**
     * UC4: creert de nodige elementen die deel zullen uitmaken van het spelpaneel
     * en zet ze op de juiste plaatsen.
     */

    private void voegComponentenToe() {
        // instantie elementen
        SpelLogoPaneel spelLogoPaneel = new SpelLogoPaneel(domeinController);
        this.spelScorebladPaneel = new SpelScorebladPaneel(domeinController);
        SpelSpelerPaneel spelSpelerPaneel = new SpelSpelerPaneel(hoofdPaneel, menuPaneel, spelScorebladPaneel,
                domeinController, this);
        spelBordPaneel = new SpelBordPaneel(domeinController, spelSpelerPaneel);

        // styling
        setAlignment(spelScorebladPaneel, Pos.BASELINE_LEFT);
        setAlignment(spelBordPaneel, Pos.CENTER);
        this.setStyle("-fx-background-color: #566454");

        // insert in GUI
        this.setTop(spelLogoPaneel);
        this.setCenter(spelBordPaneel);
        this.setLeft(spelSpelerPaneel);
        this.setRight(spelScorebladPaneel);
        ;
    }

    /**
     * UC4: getter voor SpelScoreBladPaneel voor dataflow.
     */

    public SpelScorebladPaneel getSpelScorebladPaneel() {
        return spelScorebladPaneel;
    }

    /**
     * UC4: setter voor nieuw spelbord indien spel gecanceled wordt.
     */

    public SpelBordPaneel getSpelBordPaneel() {
        return spelBordPaneel;
    }
}