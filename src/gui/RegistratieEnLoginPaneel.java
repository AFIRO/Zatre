package gui;

import java.nio.charset.IllegalCharsetNameException;

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

public class RegistratieEnLoginPaneel extends VBox {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private TextField txtNaam;
    private TextField txtGeboortejaar;
    private Label lblFeedback;
    private Button btnSpelStarten;

    public RegistratieEnLoginPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
    	this.setStyle("-fx-background-color: #566454");
    	this.setSpacing(5);
    	  this.setAlignment(Pos.TOP_CENTER);
        final Text header = new Text(domeinController.getTaal().getLocalisatie("REGISTRATIE") + " / " + domeinController.getTaal().getLocalisatie("LOGIN") );
        header.setStyle("-fx-font-size: 2em;");
       
        
        final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
        naam.setStyle("-fx-font-size: 2em;");
        txtNaam = new TextField();
        txtNaam.setMaxWidth(250);
        txtNaam.setMaxHeight(100);
        txtNaam.setStyle("-fx-font-size: 1.5em; -fx-alignment: center");
        

        final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
        jaar.setStyle("-fx-font-size: 2em;");
        txtGeboortejaar = new TextField();
        txtGeboortejaar.setMaxWidth(250);
        txtGeboortejaar.setMaxHeight(100);
        txtGeboortejaar.setStyle("-fx-font-size: 1.5em; -fx-alignment: center");
        HBox alignmentBoxEersteRij = new HBox();
        HBox alignmentBoxTweedeRij = new HBox();
        Button btnRegistreer = new Button(domeinController.getTaal().getLocalisatie("REGISTREER_KNOP"));
        Button btnLogin = new Button(domeinController.getTaal().getLocalisatie("LOGIN_KNOP"));
        Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
        btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
        alignmentBoxEersteRij.getChildren().addAll(btnRegistreer, btnSpelStarten,btnLogin);
        alignmentBoxTweedeRij.getChildren().addAll(btnBack, btnQuit);
        alignmentBoxEersteRij.setAlignment(Pos.CENTER);
        alignmentBoxEersteRij.setSpacing(20.0);
        alignmentBoxTweedeRij.setAlignment(Pos.CENTER);
        alignmentBoxTweedeRij.setSpacing(20.0);

        btnRegistreer.setOnAction(this::registreer);
        btnRegistreer.setPadding(new Insets(5, 5, 5, 5));
        btnRegistreer.setLineSpacing(100);
        btnRegistreer.setMaxWidth(1500);
        btnRegistreer.setAlignment(Pos.CENTER_LEFT);
        btnRegistreer.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 1em");
        
        btnLogin.setOnAction(this::login);
        btnLogin.setPadding(new Insets(5, 5, 5, 5));
        btnLogin.setLineSpacing(100);
        btnLogin.setMaxWidth(1500);
        btnLogin.setAlignment(Pos.CENTER);
        btnLogin.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 1em");
        
        btnSpelStarten.setOnAction(this::starten);
        btnSpelStarten.setPadding(new Insets(5, 5, 5, 5));
        btnSpelStarten.setLineSpacing(100);
        btnSpelStarten.setMaxWidth(1500);
        btnSpelStarten.setAlignment(Pos.CENTER_RIGHT);
        btnSpelStarten.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 1em");
        
        btnBack.setOnAction(this::back);
        btnBack.setPadding(new Insets(5, 5, 5, 5));
        btnBack.setLineSpacing(100);
        btnBack.setMaxWidth(1500);
        btnBack.setAlignment(Pos.CENTER);
        btnBack.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 1em");
        
        btnQuit.setOnAction(this::quit);
        btnQuit.setPadding(new Insets(5, 5, 5, 5));
        btnQuit.setLineSpacing(100);
        btnQuit.setMaxWidth(1500);
        btnQuit.setAlignment(Pos.CENTER);
        btnQuit.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 1em");

        lblFeedback = new Label();
        lblFeedback.setVisible(false);
       

        this.getChildren().addAll(header, naam, txtNaam, jaar, txtGeboortejaar,alignmentBoxEersteRij,alignmentBoxTweedeRij, lblFeedback);
        btnSpelStarten.setVisible(false);
    }

    private void registreer(ActionEvent actionEvent) {
    	
        try {
        	controleerOfInputNietBlancoIs(this.txtNaam.getText());
        	controleerOfInputNietBlancoIs(this.txtGeboortejaar.getText());
        	      
            domeinController.registreer(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
            txtNaam.setText("");
            txtGeboortejaar.setText("");
            lblFeedback.setText((String.format("%s%n%s", domeinController.getTaal().getLocalisatie("CORRECT_GEREGISTREERD"), domeinController.geefSpeler(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText())))));
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
        	controleerOfInputNietBlancoIs(this.txtNaam.getText());
        	controleerOfInputNietBlancoIs(this.txtGeboortejaar.getText());
        	
            domeinController.meldAan(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
            lblFeedback.setText(domeinController.getTaal().getLocalisatie("CORRECT_AANGEMELD"));
            lblFeedback.setVisible(true);
           
            if(domeinController.geefSpelers().size()>=2) {
            	btnSpelStarten.setVisible(true);
            }

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
    
    private void starten(ActionEvent actionEvent) {
    	menuPaneel.starten(actionEvent);
    }
    
    private void controleerOfInputNietBlancoIs(String input) {
    	
    if(input.isBlank() || input.isEmpty() || input.equals(null)) {
    	throw new IllegalArgumentException("BLANCO_INPUT");
    }
    }
}


