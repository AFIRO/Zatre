package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuPaneel extends VBox {
    private final HoofdPaneel hoofdPaneel;
    private final DomeinController domeinController;

    public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController) {
        this.hoofdPaneel = hoofdPaneel;
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    private void voegComponentenToe()
    {

        Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
        GridPane.setHalignment(header, HPos.LEFT);
        Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
        GridPane.setHalignment(subheader, HPos.RIGHT);
        Button BtnRegistreer = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3"));
        Button BtnAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
        Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
        //BtnRegistreer.setOnAction();
        //BtnAanmelden.setOnAction();
        btnQuit.setOnAction(this::quit);
        this.getChildren().addAll(header,subheader,BtnRegistreer,BtnAanmelden,btnQuit);


    }


    public void quit(ActionEvent actionEvent) {
        System.exit(0);

    }
}
