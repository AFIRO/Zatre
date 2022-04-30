package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SpelScorebladPaneel extends VBox {

    private final DomeinController domeinController;
    private Label lblTitel;
    private Label lblActieveSpeler;
    private TextField txfScoreblad;

    /**
     *UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     */

    public SpelScorebladPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    /**
     *UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen op de juiste plaats.
     */

    private void voegComponentenToe() {
        //instantie elementen
        lblTitel = new Label("Huidige actieve speler: ");
        lblActieveSpeler = new Label();
        txfScoreblad = new TextField();

        //styling
        lblTitel.setStyle("-fx-font-size: 2em");
        lblActieveSpeler.setStyle("-fx-font-size: 1.5em");
        txfScoreblad.setAlignment(Pos.CENTER);
        txfScoreblad.setMaxWidth(300);
        this.txfScoreblad.setEditable(false);
        this.setPrefHeight(1000);
        this.setPrefWidth(350);
        this.setAlignment(Pos.BASELINE_LEFT);

        //insert in GUI
        this.getChildren().addAll(lblTitel, lblActieveSpeler, txfScoreblad);

    }

    /**
     *UC4: eventhandler voor update van info in dit scherm
     *
     * @param spelerAanBeurt de actieve speler
     */

    public void updateInfo(int spelerAanBeurt) {
        this.lblActieveSpeler.setText("Speler: " + domeinController.geefSpelers().get(spelerAanBeurt).substring(10));
        this.txfScoreblad.setText(domeinController.geefScorebladen().get(spelerAanBeurt));

    }
}