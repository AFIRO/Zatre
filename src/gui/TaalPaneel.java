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
    private DomeinController domeinController;

    public TaalPaneel(DomeinController domeinController, HoofdPaneel hoofdPaneel) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
    	 
    	this.setStyle("-fx-background-color: #566454"); 
    	Text txtHeader = new Text("Kies uw taal / Choose your language:");
        GridPane.setHalignment(txtHeader, HPos.CENTER);  
        this.setAlignment(Pos.TOP_CENTER);
      
        txtHeader.setStyle("-fx-font-size: 2em;");
        
       
        Button btnNederlands = new Button("Nederlands");
        Button btnEngels = new Button("English");
        
        btnNederlands.setOnAction(this::setTaalNederlands);
        btnNederlands.setPadding(new Insets(10, 10, 10, 10));
        btnNederlands.setLineSpacing(100);
        btnNederlands.setMaxWidth(200);
        btnNederlands.setAlignment(Pos.CENTER);
        btnNederlands.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 2em;");
        
        
        btnEngels.setOnAction(this::setTaalEngels);
        btnEngels.setPadding(new Insets(10, 10, 10, 10));
        btnEngels.setLineSpacing(100);
        btnEngels.setMaxWidth(200);
        btnEngels.setAlignment(Pos.CENTER);
        btnEngels.setStyle("-fx-background-color: #8DFC79;"
        		+ "-fx-border-color: #000000;"
        		+ "-fx-border-width: 2px;"
        		+ "-fx-font-size: 2em");
        
        this.setSpacing(20.0);    
        
        this.getChildren().addAll(txtHeader, btnNederlands,btnEngels);
        
    }


   private void setTaalNederlands(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.NEDERLANDS));
    	hoofdPaneel.taalGekozen();
    }

    private void setTaalEngels(ActionEvent actionEvent) {
        domeinController.setTaal(new Taal(Taal.Taalkeuze.ENGELS));
        hoofdPaneel.taalGekozen();    }
}
