package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import util.Taal;


public class TaalPaneel extends HBox {
    private final HoofdPaneel hoofdPaneel;
    private DomeinController domeinController;

    public TaalPaneel(DomeinController domeinController, HoofdPaneel hoofdPaneel) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
    	
    
    	
    	Text txtHeader = new Text("Kies uw taal / Choose your language:");
        GridPane.setHalignment(txtHeader, HPos.LEFT);   
        
       
        Button btnNederlands = new Button("Nederlands");
        Button btnEngels = new Button("English");
        
        btnNederlands.setOnAction(this::setTaalNederlands);
        btnNederlands.setPadding(new Insets(10, 10, 10, 10));
        btnNederlands.setLineSpacing(100);
        
        btnEngels.setOnAction(this::setTaalEngels);
        btnEngels.setPadding(new Insets(10, 10, 10, 10));
        btnEngels.setLineSpacing(100);
        
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
