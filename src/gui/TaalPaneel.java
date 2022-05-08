package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.Taal;

public class TaalPaneel extends VBox {
    private static final String LOGO_LOCATIE = "file:src/assets/logo/ZatreLogo.png";
    private final HoofdPaneel hoofdPaneel;
    private final DomeinController domeinController;
    private final ImageView imLogo = new ImageView();

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
     * UC3: initialiseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     * <p>
     * Hulpmethode voor constructor
     */

    private void voegComponentenToe() {
        // instantie elementen
        Image logo = new Image(LOGO_LOCATIE);
        Button btnNederlands = new Button("Nederlands");
        Button btnEngels = new Button("English");
        Text logoHeader = new Text("");
        Text txtHeader = new Text("Kies uw taal / Choose your language:");

        // eventlisteners
        btnNederlands.setOnAction(this::setTaalNederlands);
        btnEngels.setOnAction(this::setTaalEngels);

        // styling
        imLogo.setImage(logo);
        imLogo.setFitWidth(400);
        imLogo.setFitHeight(100);
        StackPane.setAlignment(imLogo, Pos.CENTER);


        zetCSSVanKnopGoed(btnEngels);
        zetCSSVanKnopGoed(btnNederlands);
        GridPane.setHalignment(txtHeader, HPos.CENTER);
        txtHeader.setStyle("-fx-font-size: 2em;");
        this.setSpacing(20.0);
        this.setStyle("-fx-background-color: #59981A");
        this.setAlignment(Pos.TOP_CENTER);

        // insert in GUI
        this.getChildren().addAll(logoHeader, imLogo, txtHeader, btnNederlands, btnEngels);

    }

    /**
     * UC3: zet CSS van knop goed
     * Hulpmethode voor constructor
     *
     * @param knop de knop
     */

    private void zetCSSVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(10, 10, 10, 10));
        knop.setLineSpacing(100);
        knop.setMaxWidth(200);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #020470;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;" + "-fx-text-fill: #ffffff;" + "-fx-border-radius: 50px;" + "-fx-background-radius: 50px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
    }

    /**
     * UC3: set de applicatie naar Nederlands
     * Hulpmethode voor taal correct instellen
     */

    private void setTaalNederlands(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.NEDERLANDS));
        hoofdPaneel.taalGekozen();
    }

    /**
     * UC3: set de applicatie naar Engels
     * Hulpmethode voor taal correct instellen
     */

    private void setTaalEngels(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.ENGELS));
        hoofdPaneel.taalGekozen();
    }
}
