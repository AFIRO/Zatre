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

    public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController, TaalPaneel taalPaneel) {
        this.hoofdPaneel = hoofdPaneel;
        this.domeinController = domeinController;
        this.taalPaneel = taalPaneel;
        this.registratieEnLoginPaneel = new RegistratieEnLoginPaneel(hoofdPaneel, this, domeinController);
        this.spelPaneel = new SpelPaneel(hoofdPaneel, this, domeinController);

        voegComponentenToe();
    }

    private void voegComponentenToe() {

        this.setStyle("-fx-background-color: #566454");
        this.setAlignment(Pos.TOP_CENTER);

        Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
        header.setStyle("-fx-font-size: 3em;");

        Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
        subheader.setStyle("-fx-font-size: 2em");

        VBox alignmentBoxButtons = new VBox();

        Button btnRegistreerAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3") + "/" + domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
        Button btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
        Button btnKiesTaal = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_8"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
        alignmentBoxButtons.getChildren().addAll(btnRegistreerAanmelden, btnSpelStarten, btnKiesTaal, btnQuit);

        alignmentBoxButtons.setAlignment(Pos.CENTER);
        alignmentBoxButtons.setSpacing(20.0);

        lblLoggedOn = new Label();
        btnRegistreerAanmelden.setOnAction(this::registreerAanmelden);
        btnRegistreerAanmelden.setPadding(new Insets(10, 10, 10, 10));
        btnRegistreerAanmelden.setLineSpacing(100);
        btnRegistreerAanmelden.setMaxWidth(250);
        btnRegistreerAanmelden.setAlignment(Pos.CENTER);
        btnRegistreerAanmelden.setStyle("-fx-background-color: #8DFC79;"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;");

        btnSpelStarten.setOnAction(this::starten);
        btnSpelStarten.setPadding(new Insets(10, 10, 10, 10));
        btnSpelStarten.setLineSpacing(100);
        btnSpelStarten.setMaxWidth(250);
        btnSpelStarten.setAlignment(Pos.CENTER);
        btnSpelStarten.setStyle("-fx-background-color: #8DFC79;"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;");

        btnKiesTaal.setOnAction(this::kiesTaal);
        btnKiesTaal.setPadding(new Insets(10, 10, 10, 10));
        btnKiesTaal.setLineSpacing(100);
        btnKiesTaal.setMaxWidth(250);
        btnKiesTaal.setAlignment(Pos.CENTER);
        btnKiesTaal.setStyle("-fx-background-color: #8DFC79;"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;");


        btnQuit.setOnAction(this::quit);
        btnQuit.setPadding(new Insets(10, 10, 10, 10));
        btnQuit.setLineSpacing(100);
        btnQuit.setMaxWidth(250);
        btnQuit.setAlignment(Pos.CENTER);
        btnQuit.setStyle("-fx-background-color: #8DFC79;"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 2px;"
                + "-fx-font-size: 2em;");

        lblLoggedOn.setVisible(false);

        this.getChildren().addAll(header, subheader, alignmentBoxButtons,
                lblLoggedOn);

    }

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

    private void registreerAanmelden(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(registratieEnLoginPaneel);
    }

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

    private void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public Label getLblLoggedOn() {
        return lblLoggedOn;
    }

    public void setSpelPaneel(SpelPaneel spelPaneel) {
        this.spelPaneel = spelPaneel;
    }
}
