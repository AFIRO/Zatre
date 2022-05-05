package gui;

import domein.DomeinController;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SpelScorebladPaneel extends VBox {

    private final DomeinController domeinController;
    private Label lblTitel;
    private Label lblActieveSpeler;
    private TextField txfScoreblad;

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
        txfScoreblad = new TextField();
           
        
        //mogelijks werkt dit om de textfield automatisch te laten groeien
     //  txfScoreblad.prefColumnCountProperty().bind(txfScoreblad.textProperty().length());
       //setVgrow(txfScoreblad, Priority.ALWAYS);

        // eventlisteners
        txfScoreblad.textProperty().addListener((ChangeListener<String>) (observable, oldValue,
                                                                          newValue) -> txfScoreblad.setPrefWidth(txfScoreblad.getText().length() * 7));

        // styling
        lblTitel.setStyle("-fx-font-size: 2em");
        lblActieveSpeler.setStyle("-fx-font-size: 1.5em");
       txfScoreblad.setAlignment(Pos.CENTER);
        txfScoreblad.setMaxWidth(300);
      //  txfScoreblad.setMinHeight(500);
        this.txfScoreblad.setEditable(false);
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
        this.txfScoreblad.setText(domeinController.geefScoreBladVanActieveSpeler());
    }
}