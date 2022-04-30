package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.Taal;

public class TaalPaneel extends VBox {
    private final HoofdPaneel hoofdPaneel;
    private final DomeinController domeinController;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     * @param hoofdPaneel      voor aanpassing actief paneel
     */

    public TaalPaneel(DomeinController domeinController, HoofdPaneel hoofdPaneel) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     */

    private void voegComponentenToe() {
        // instantie elementen
        Button btnNederlands = new Button("Nederlands");
        Button btnEngels = new Button("English");
        Text txtHeader = new Text("Kies uw taal / Choose your language:");

        // eventlisteners
        btnNederlands.setOnAction(this::setTaalNederlands);
        btnEngels.setOnAction(this::setTaalEngels);

        // styling
        zetCSSVanKnopGoed(btnEngels);
        zetCSSVanKnopGoed(btnNederlands);
        GridPane.setHalignment(txtHeader, HPos.CENTER);
        txtHeader.setStyle("-fx-font-size: 2em;");
        this.setSpacing(20.0);
        this.setStyle("-fx-background-color: #566454");
        this.setAlignment(Pos.TOP_CENTER);

        // insert in GUI
        this.getChildren().addAll(txtHeader, btnNederlands, btnEngels);

    }

    /**
     * UC3: zet CSS van knop goed
     *
     * @param knop de knop
     */

    private void zetCSSVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(10, 10, 10, 10));
        knop.setLineSpacing(100);
        knop.setMaxWidth(200);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #8DFC79;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;" + "-fx-border-radius: 50px;" + "-fx-background-radius: 50px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
    }

    /**
     * UC3: set de applicatie naar Nederlands
     */

    private void setTaalNederlands(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.NEDERLANDS));
        hoofdPaneel.taalGekozen();
    }

    /**
     * UC3: set de applicatie naar Nederlands
     */

    private void setTaalEngels(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.ENGELS));
        hoofdPaneel.taalGekozen();
    }
}
