package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegistratieEnLoginPaneel extends VBox {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private TextField txtNaam;
    private TextField txtGeboortejaar;
    private Label lblFeedback;

    public RegistratieEnLoginPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
        final Text header = new Text(domeinController.getTaal().getLocalisatie("REGISTRATIE") + " / " + domeinController.getTaal().getLocalisatie("REGISTRATIE") );
        final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
        txtNaam = new TextField();
        txtNaam.setMaxWidth(200);

        final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
        txtGeboortejaar = new TextField();
        txtGeboortejaar.setMaxWidth(200);
        HBox alignmentBoxEersteRij = new HBox();
        HBox alignmentBoxTweedeRij = new HBox();
        Button btnRegistreer = new Button(domeinController.getTaal().getLocalisatie("REGISTREER_KNOP"));
        Button btnLogin = new Button(domeinController.getTaal().getLocalisatie("LOGIN_KNOP"));
        Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
        alignmentBoxEersteRij.getChildren().addAll(btnRegistreer,btnLogin);
        alignmentBoxTweedeRij.getChildren().addAll(btnBack,btnQuit);

        btnRegistreer.setOnAction(this::registreer);
        btnLogin.setOnAction(this::login);
        btnBack.setOnAction(this::back);
        btnQuit.setOnAction(this::quit);

        lblFeedback = new Label();
        lblFeedback.setVisible(false);

        this.getChildren().addAll(header, naam, txtNaam, jaar, txtGeboortejaar,alignmentBoxEersteRij,alignmentBoxTweedeRij, lblFeedback);
    }

    private void registreer(ActionEvent actionEvent) {
        String gebruikersnaam = this.txtNaam.getText();
        int geboortejaar = Integer.parseInt(this.txtGeboortejaar.getText());

        try {
            domeinController.registreer(gebruikersnaam, geboortejaar);
            txtNaam.setText("");
            txtGeboortejaar.setText("");
            lblFeedback.setText((String.format("%s%n%s", domeinController.getTaal().getLocalisatie("CORRECT_GEREGISTREERD"), domeinController.geefSpeler(gebruikersnaam, geboortejaar))));
            lblFeedback.setVisible(true);

        } catch (IllegalArgumentException e) {
            lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
            txtNaam.setText("");
            txtGeboortejaar.setText("");
            lblFeedback.setVisible(true);
        }
    }

    private void login(ActionEvent actionEvent) {

        try {
            domeinController.meldAan(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
            lblFeedback.setText(domeinController.getTaal().getLocalisatie("CORRECT_AANGEMELD"));
            lblFeedback.setVisible(true);

            if (domeinController.geefSpelers().size() >= 4) {
                menuPaneel.updateLoggedOnPlayerLabel();
                hoofdPaneel.setCenter(menuPaneel);
            }
            else {
                lblFeedback.setText(domeinController.getTaal().getLocalisatie("NOG_AANMELDEN2"));
                menuPaneel.updateLoggedOnPlayerLabel();
                txtNaam.setText("");
                txtGeboortejaar.setText("");
            }

        } catch (IllegalArgumentException e) {
            lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
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


