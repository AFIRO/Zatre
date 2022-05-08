package gui;

import domein.DomeinController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuPaneel extends VBox {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final RegistratieEnLoginPaneel registratieEnLoginPaneel;
    private final TaalPaneel taalPaneel;
    private SpelPaneel spelPaneel;
    private Label lblLoggedOn;
    private HBox aangemeldeSpelersBox;
    private ScrollBar sc;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     * @param hoofdPaneel      voor aanpassing scherm
     * @param taalPaneel       voor terugkeer naar taalkeuze
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
     * UC3: initialiseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     * <p>
     * Hulpmethode voor constructor
     */

    private void voegComponentenToe() {
        //aanmaak scrollbar voor lblLoggedOn
        sc = new ScrollBar();
        sc.setMin(0);
        sc.setMax(200);
        sc.setValue(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setStyle("-fx-background-color: #020470;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
        lblLoggedOn = new Label();
        Label gap = new Label("");

        // instantie elementen
        Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
        Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
        VBox alignmentBoxButtons = new VBox();
        //Lbl en scrollbar toegevoegd aan HBox
        aangemeldeSpelersBox = new HBox(lblLoggedOn, sc);

        Button btnRegistreerAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3") + "/"
                + domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
        Button btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
        Button btnKiesTaal = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_8"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));


        // eventlisteners
        btnRegistreerAanmelden.setOnAction(this::registreerAanmelden);
        btnSpelStarten.setOnAction(this::starten);
        btnKiesTaal.setOnAction(this::kiesTaal);
        btnQuit.setOnAction(this::quit);

        // styling
        zetCssVanKnopGoed(btnQuit);
        zetCssVanKnopGoed(btnKiesTaal);
        zetCssVanKnopGoed(btnSpelStarten);
        zetCssVanKnopGoed(btnRegistreerAanmelden);
        this.setStyle("-fx-background-color: #59981A");
        this.setAlignment(Pos.TOP_CENTER);

        header.setStyle("-fx-font-size: 3em;");
        subheader.setStyle("-fx-font-size: 2em");
        alignmentBoxButtons.setAlignment(Pos.CENTER);
        alignmentBoxButtons.setSpacing(20.0);
        aangemeldeSpelersBox.setSpacing(15);
        aangemeldeSpelersBox.setVisible(false);
        aangemeldeSpelersBox.setAlignment(Pos.CENTER);


        // insert in GUI
        this.getChildren().addAll(header, subheader, alignmentBoxButtons, gap, aangemeldeSpelersBox);
        // aangemeldeSpelersBox.getChildren().add(lblLoggedOn);
        alignmentBoxButtons.getChildren().addAll(btnRegistreerAanmelden, btnSpelStarten, btnKiesTaal, btnQuit);

    }

    /**
     * UC3: zet styling van knoppen goed
     * <p>
     * Hulpmethode voor constructor
     */

    private void zetCssVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(10, 10, 10, 10));
        knop.setLineSpacing(100);
        knop.setMaxWidth(250);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #020470;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;" + "-fx-text-fill: #ffffff;" + "-fx-border-radius: 50px;" + "-fx-background-radius: 50px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
    }

    /**
     * UC3: eventhandler voor overgang naar de spelschermen
     * <p>
     * Hulpmethode voor spel starten
     */

    public void starten(ActionEvent actionEvent) {
        try {
            domeinController.startSpel();
            Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
            stage.setMaximized(true);
            stage.setWidth(1750);
            stage.setHeight(1070);

            this.spelPaneel.getSpelScorebladPaneel().updateInfo();
            hoofdPaneel.setCenter(spelPaneel);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            alert.showAndWait();
        }

    }

    /**
     * UC3: eventhandler voor overgang naar de registratie en aanmelden
     * <p>
     * Hulpmethode voor registreren en aanmelden
     */

    private void registreerAanmelden(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(registratieEnLoginPaneel);
    }

    /**
     * UC3: eventhandler voor overgang naar kies taal scherm
     * <p>
     * Hulpmethode voor taal kiezen
     */

    private void kiesTaal(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(taalPaneel);
    }

    /**
     * UC3: updated het logged on player label die de ingelogde spelers op het menupaneel laat zien
     */

    public void updateLoggedOnPlayerLabel() {
        if (!domeinController.geefSpelers().isEmpty()) {
            this.aangemeldeSpelersBox.setVisible(true);
            this.lblLoggedOn.setVisible(true);
            this.lblLoggedOn.setText(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_6")
                    + String.format("%n%n") + String.join("", domeinController.geefSpelers()));

            sc.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                    aangemeldeSpelersBox.setLayoutY(-new_val.doubleValue());
                }
            });

        }
    }

    /**
     * UC3: eventhandler om spel te sluiten
     */

    private void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * UC3: getter voor aanpassing van label door andere schermen
     */

    public Label getLblLoggedOn() {
        return lblLoggedOn;
    }

    /**
     * UC3: setter voor spelpaneel. Nodig indien een spel wordt opgestart na
     * cancel of na een geslaagd spel
     */

    public void setSpelPaneel(SpelPaneel spelPaneel) {
        this.spelPaneel = spelPaneel;
    }
}
