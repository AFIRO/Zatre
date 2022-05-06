package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class SpelScorebladPaneel extends VBox {

    private final DomeinController domeinController;
    private Label lblTitel;
    private Label lblActieveSpeler;
    private Text txfScoreblad;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     */

    public SpelScorebladPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     */

    private void voegComponentenToe() {
        // instantie elementen
        lblTitel = new Label(domeinController.getTaal().getLocalisatie("HUIDIGE_SPELER"));
        lblActieveSpeler = new Label();
        txfScoreblad = new Text();
        //maak hier een TableView


        // styling
        lblTitel.setStyle("-fx-font-size: 2em");
        lblActieveSpeler.setStyle("-fx-font-size: 1.5em");
        this.setPrefHeight(1000);
        this.setPrefWidth(350);
        this.setAlignment(Pos.BASELINE_LEFT);

        // insert in GUI
        this.getChildren().addAll(lblTitel, lblActieveSpeler, txfScoreblad);

    }

    /**
     * UC4: eventhandler voor update van info in dit scherm
     */

    public void updateInfo() {
        this.lblActieveSpeler.setText(domeinController.geefActieveSpeler().get(0));

        for (String Regel : domeinController.geefScoreBladVanActieveSpeler()){
            String[] score  = Regel.split(" ");
            //new row
            //voeg row aan table toe
            for (String item : score){
                //voeg toe aan row
            }
        }
        this.txfScoreblad.setText(domeinController.geefScoreBladVanActieveSpeler());
    }
}