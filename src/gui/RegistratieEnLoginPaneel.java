package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class RegistratieEnLoginPaneel extends VBox {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private TextField txtNaam;
    private TextField txtGeboortejaar;
    private Label lblFeedback;
    private Button btnSpelStarten;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     * @param hoofdPaneel      voor aanpassing scherm
     * @param menuPaneel       voor terugkeer naar menu
     */

    public RegistratieEnLoginPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     */

    private void voegComponentenToe() {
        // instantie objecten
        final Text header = new Text(domeinController.getTaal().getLocalisatie("REGISTRATIE") + " / "
                + domeinController.getTaal().getLocalisatie("LOGIN"));
        final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
        final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
        txtNaam = new TextField();
        txtGeboortejaar = new TextField();
        lblFeedback = new Label();
        HBox alignmentBoxEersteRij = new HBox();
        HBox alignmentBoxTweedeRij = new HBox();
        Button btnRegistreer = new Button(domeinController.getTaal().getLocalisatie("REGISTREER_KNOP"));
        Button btnLogin = new Button(domeinController.getTaal().getLocalisatie("LOGIN_KNOP"));
        Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
        btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));

        // eventlisteners
        btnRegistreer.setOnAction(this::registreer);
        btnLogin.setOnAction(this::login);
        btnSpelStarten.setOnAction(this::starten);
        btnBack.setOnAction(this::back);
        btnQuit.setOnAction(this::quit);

        // styling
        zetCSSTekstveldenGoed(txtNaam);
        zetCSSTekstveldenGoed(txtGeboortejaar);
        zetAlignmentBoxenGoed(alignmentBoxEersteRij);
        zetAlignmentBoxenGoed(alignmentBoxTweedeRij);
        zetCSSVanKnopGoed(btnQuit);
        zetCSSVanKnopGoed(btnBack);
        zetCSSVanKnopGoed(btnSpelStarten);
        zetCSSVanKnopGoed(btnLogin);
        zetCSSVanKnopGoed(btnRegistreer);
        zetCSSLabelsGoed(jaar);
        zetCSSLabelsGoed(naam);
        this.setStyle("-fx-background-color: #59981A");
        this.setSpacing(5);
        this.setAlignment(Pos.TOP_CENTER);
        header.setStyle("-fx-font-size: 2em;");
        lblFeedback.setVisible(false);
        lblFeedback.setVisible(false);
        btnSpelStarten.setVisible(false);

        // insert in GUI
        this.getChildren().addAll(header, naam, txtNaam, jaar, txtGeboortejaar, alignmentBoxEersteRij,
                alignmentBoxTweedeRij, lblFeedback);
        alignmentBoxEersteRij.getChildren().addAll(btnRegistreer, btnSpelStarten, btnLogin);
        alignmentBoxTweedeRij.getChildren().addAll(btnBack, btnQuit);
    }

    /**
     * UC3: zet CSS van labels goed
     *
     * @param label de label
     */

    private void zetCSSLabelsGoed(Label label) {
        label.setStyle("-fx-font-size: 2em;");
    }

    /**
     * UC3: zet CSS van boxen goed
     *
     * @param box de box
     */

    private void zetAlignmentBoxenGoed(HBox box) {
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20.0);
    }

    /**
     * UC3: zet CSS van tekstvelden goed
     *
     * @param txt het tekstveld
     */

    private void zetCSSTekstveldenGoed(TextField txt) {
        txt.setMaxWidth(250);
        txt.setMaxHeight(100);
        txt.setStyle("-fx-font-size: 1.5em; -fx-alignment: center; -fx-border-color: #000000;-fx-border-width: 2px;"
                + "-fx-border-radius: 30px; -fx-background-radius: 30px;");
    }

    /**
     * UC3: zet CSS van knoppen goed
     *
     * @param knop de knop
     */

    private void zetCSSVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(5, 5, 5, 5));
        knop.setLineSpacing(100);
        knop.setMaxWidth(1500);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #020470;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 1em;" + "-fx-text-fill: #ffffff;" + "-fx-border-radius: 25px;" + "-fx-background-radius: 25px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
    }

    /**
     * UC3: eventhandler voor registratie van nieuwe speler
     */

    private void registreer(ActionEvent actionEvent) {

        try {
            controleerOfInputNietBlancoIs(this.txtNaam.getText());
            controleerOfInputNietBlancoIs(this.txtGeboortejaar.getText());

            domeinController.registreer(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
            lblFeedback.setText((String.format("%s%n%s",
                    domeinController.getTaal().getLocalisatie("CORRECT_GEREGISTREERD"), domeinController
                            .geefSpeler(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText())))));
            txtNaam.setText("");
            txtGeboortejaar.setText("");
            lblFeedback.setVisible(true);
            txtNaam.requestFocus();

        } catch (IllegalArgumentException e) {
            lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            txtNaam.setText("");
            txtGeboortejaar.setText("");
            lblFeedback.setVisible(true);
            txtNaam.requestFocus();
        }
    }

    /**
     * UC3: eventhandler voor login van speler
     */

    private void login(ActionEvent actionEvent) {

        try {
            controleerOfInputNietBlancoIs(this.txtNaam.getText());
            controleerOfInputNietBlancoIs(this.txtGeboortejaar.getText());

            domeinController.meldAan(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
            lblFeedback.setText(domeinController.getTaal().getLocalisatie("CORRECT_AANGEMELD"));
            lblFeedback.setVisible(true);

            if (domeinController.geefSpelers().size() >= 2) {
                btnSpelStarten.setVisible(true);
            }

            if (domeinController.geefSpelers().size() >= 4) {
                menuPaneel.updateLoggedOnPlayerLabel();
                hoofdPaneel.setCenter(menuPaneel);
            } else {
                lblFeedback.setText(domeinController.getTaal().getLocalisatie("NOG_AANMELDEN2"));
                menuPaneel.updateLoggedOnPlayerLabel();
                txtNaam.setText("");
                txtGeboortejaar.setText("");
                txtNaam.requestFocus();
            }

        } catch (IllegalArgumentException e) {
            lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            lblFeedback.setVisible(true);
            txtNaam.requestFocus();
        }
    }

    /**
     * UC3: eventhandler voor terugkeer naar menu
     */

    private void back(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(menuPaneel);
    }

    /**
     * UC3: eventhandler voor uit spel te gaan
     */

    private void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * UC3: eventhandler voor spel starten
     */

    private void starten(ActionEvent actionEvent) {
        menuPaneel.starten(actionEvent);
        btnSpelStarten.setVisible(false);
    }

    /**
     * UC3: hulpmethode voor input sanering
     */

    private void controleerOfInputNietBlancoIs(String input) {
        if (Objects.isNull(input) || input.isBlank()) {
            throw new IllegalArgumentException("BLANCO_INPUT");
        }
    }
}
