package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuPaneel extends VBox {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final RegistratieEnLoginPaneel registratieEnLoginPaneel;
    private SpelPaneel spelPaneel;
    private final TaalPaneel taalPaneel;
    private Label lblLoggedOn;

    /**
     *UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     * @param hoofdPaneel voor aanpassing scherm
     * @param taalPaneel voor terugkeer naar taalkeuze
     */


    public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController, TaalPaneel taalPaneel) {
        this.hoofdPaneel = hoofdPaneel;
        this.domeinController = domeinController;
        this.taalPaneel = taalPaneel;
        this.registratieEnLoginPaneel = new RegistratieEnLoginPaneel(hoofdPaneel, this, domeinController);
        this.spelPaneel = new SpelPaneel(hoofdPaneel, this, domeinController);

        voegComponentenToe();
    }

    /**
     *UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen op de juiste plaats.
     */

    private void voegComponentenToe() {
        //instantie elementen
        Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
        Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
        VBox alignmentBoxButtons = new VBox();
        Button btnRegistreerAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3") + "/" + domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
        Button btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
        Button btnKiesTaal = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_8"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
        lblLoggedOn = new Label();

        //eventhandlers
        btnRegistreerAanmelden.setOnAction(this::registreerAanmelden);
        btnSpelStarten.setOnAction(this::starten);
        btnKiesTaal.setOnAction(this::kiesTaal);
        btnQuit.setOnAction(this::quit);

        //styling
        zetCssVanKnopGoed(btnQuit);
        zetCssVanKnopGoed(btnKiesTaal);
        zetCssVanKnopGoed(btnSpelStarten);
        zetCssVanKnopGoed(btnRegistreerAanmelden);
        this.setStyle("-fx-background-color: #566454");
        this.setAlignment(Pos.TOP_CENTER);
        lblLoggedOn.setVisible(false);
        header.setStyle("-fx-font-size: 3em;");
        subheader.setStyle("-fx-font-size: 2em");
        alignmentBoxButtons.setAlignment(Pos.CENTER);
        alignmentBoxButtons.setSpacing(20.0);

        //insert in GUI
        this.getChildren().addAll(header, subheader, alignmentBoxButtons,
                lblLoggedOn);
        alignmentBoxButtons.getChildren().addAll(btnRegistreerAanmelden, btnSpelStarten, btnKiesTaal, btnQuit);

    }

    /**
     *UC3: zet styling van knoppen goed
     */

    private void zetCssVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(10, 10, 10, 10));
        knop.setLineSpacing(100);
        knop.setMaxWidth(250);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #8DFC79;"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;");
    }

    /**
     *UC3: eventhandler voor overgang naar de spelschermen
     *
     */

    public void starten(ActionEvent actionEvent) {
        try {
            domeinController.startSpel();
            Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
            stage.setMaximized(true);
            stage.setWidth(1750);
            stage.setHeight(1070);

            this.spelPaneel.getSpelScorebladPaneel().updateInfo(0);
            hoofdPaneel.setCenter(spelPaneel);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            alert.showAndWait();
        }

    }

    /**
     *UC3: eventhandler voor overgang naar de registratie en aanmelden
     *
     */

    private void registreerAanmelden(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(registratieEnLoginPaneel);
    }

    /**
     *UC3: eventhandler voor overgang naar kies taal scherm
     *
     */

    private void kiesTaal(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(taalPaneel);
    }

    public void updateLoggedOnPlayerLabel() {
        if (!domeinController.geefSpelers().isEmpty()) {
            this.lblLoggedOn.setVisible(true);
            this.lblLoggedOn.setText(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_6")
                    + String.format("%n%n") + String.join("", domeinController.geefSpelers()));
        }
    }

    /**
     *UC3: eventhandler om spel te sluiten
     *
     */

    private void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     *UC3: getter voor aanpassing van label door andere schermen
     *
     */


    public Label getLblLoggedOn() {
        return lblLoggedOn;
    }

    /**
     *UC3: setter voor spelpaneel. Nodig indien een spel wordt opgestart na cancelatie of na een geslaagd spel
     *
     */

    public void setSpelPaneel(SpelPaneel spelPaneel) {
        this.spelPaneel = spelPaneel;
    }
}
