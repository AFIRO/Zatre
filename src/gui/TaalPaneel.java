package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.Taal;


public class TaalPaneel extends VBox {
    private final HoofdPaneel hoofdPaneel;
    public TaalPaneel(HoofdPaneel hoofdPaneel) {
        this.hoofdPaneel = hoofdPaneel;
        voegComponentenToe();
    }

    private void voegComponentenToe() {
        Text header = new Text("Kies uw taal / Choose your language:");
        GridPane.setHalignment(header, HPos.LEFT);
        Button BtnNederlands = new Button("Nederlands");
        Button BtnEngels = new Button("Engels");
        BtnNederlands.setDefaultButton(true);
        BtnNederlands.setOnAction(this::setTaalNederlands);
        BtnEngels.setOnAction(this::setTaalEngels);
        this.getChildren().addAll(header,BtnNederlands,BtnEngels);
    }


    private void setTaalNederlands(ActionEvent actionEven) {
        hoofdPaneel.setDomeincontroller(new DomeinController(new Taal(Taal.Taalkeuze.NEDERLANDS)));
        hoofdPaneel.taalGekozen();

    }

    private void setTaalEngels(ActionEvent actionEven) {
        hoofdPaneel.setDomeincontroller(new DomeinController(new Taal(Taal.Taalkeuze.ENGELS)));
        hoofdPaneel.taalGekozen();    }
}
