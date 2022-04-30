package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SpelScorebladPaneel extends VBox {

    private final DomeinController domeinController;
    private final Label lblTitel = new Label("Huidige actieve speler: ");
    private final Label lblActieveSpeler = new Label();
    private final TextField txfScoreblad = new TextField();

    public SpelScorebladPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        this.txfScoreblad.setEditable(false);
        this.setPrefHeight(1000);
        this.setPrefWidth(350);
        this.setAlignment(Pos.BASELINE_LEFT);
        voegComponentenToe();
    }

    /**
     *UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen op de juiste plaats.
     */

    private void voegComponentenToe() {
        lblTitel.setStyle("-fx-font-size: 2em");
        lblActieveSpeler.setStyle("-fx-font-size: 1.5em");

        txfScoreblad.setAlignment(Pos.CENTER);
        txfScoreblad.setMaxWidth(300);


        this.getChildren().addAll(lblTitel, lblActieveSpeler, txfScoreblad);

    }

    public void updateInfo(int spelerAanBeurt) {
        this.lblActieveSpeler.setText("Speler: " + domeinController.geefSpelers().get(spelerAanBeurt).substring(10));
        this.txfScoreblad.setText(domeinController.geefScorebladen().get(spelerAanBeurt));

    }
}