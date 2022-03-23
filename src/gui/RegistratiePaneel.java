package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegistratiePaneel extends VBox {

    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private final DomeinController domeinController;
    private String gebruikersnaam;
    int geboortejaar;
    private TextField txtNaam;
    private TextField txtGeboortejaar;
    private Label lblFeedback;

    public RegistratiePaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
        final Text header = new Text(domeinController.getTaal().getLocalisatie("REGISTRATIE"));
        GridPane.setHalignment(header, HPos.LEFT);

        final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
        txtNaam = new TextField();
        txtNaam.setMaxWidth(200);

        final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
        txtGeboortejaar = new TextField();
        txtGeboortejaar.setMaxWidth(200);

        Button btnSubmit = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
        Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));

        btnSubmit.setOnAction(this::submit);
        btnBack.setOnAction(this::back);
        btnQuit.setOnAction(this::quit);

        lblFeedback = new Label();
        lblFeedback.setVisible(false);

        this.getChildren().addAll(header, naam, txtNaam, jaar, txtGeboortejaar, btnSubmit,btnBack, btnQuit, lblFeedback);
    }

    private void submit(ActionEvent actionEvent) {
        this.gebruikersnaam = this.txtNaam.getText();
        this.geboortejaar = Integer.parseInt(this.txtGeboortejaar.getText());

        try {
            domeinController.registreer(gebruikersnaam, geboortejaar);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(String.format("%s%n%s",domeinController.getTaal().getLocalisatie("CORRECT_GEREGISTREERD"), domeinController.geefSpeler(gebruikersnaam,geboortejaar)));
            alert.showAndWait();
            hoofdPaneel.setCenter(menuPaneel);

        } catch (IllegalArgumentException e) {
            lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            alert.showAndWait();
            lblFeedback.setVisible(true);
        }
    }

    private void back(ActionEvent actionEvent) {
        hoofdPaneel.setCenter(menuPaneel);
    }

    private void quit(ActionEvent actionEvent) {
        System.exit(0);

    }
}
