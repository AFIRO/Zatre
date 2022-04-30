package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;


public class HoofdPaneel extends BorderPane {
    private DomeinController domeinController;
    private final TaalPaneel taalPaneel;


    public HoofdPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        taalPaneel = new TaalPaneel(domeinController, this);
        voegComponentenToe();
    }

    /**
     *UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen op de juiste plaats.
     */

    private void voegComponentenToe() {
        setCenter(taalPaneel);

    }

    public void taalGekozen() {
        MenuPaneel menuPaneel = new MenuPaneel(this, domeinController, new TaalPaneel(domeinController, this));
        setCenter(menuPaneel);
    }

    public void setDomeincontroller(DomeinController domeinController) {
        this.domeinController = domeinController;
    }
}
