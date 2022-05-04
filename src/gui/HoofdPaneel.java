package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;

public class HoofdPaneel extends BorderPane {
    private final TaalPaneel taalPaneel;
    private DomeinController domeinController;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     */

    public HoofdPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        
        taalPaneel = new TaalPaneel(domeinController, this);
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     */

    private void voegComponentenToe() {
        setCenter(taalPaneel);

    }

    /**
     * UC3: methode die verder spel aanmaakt op basis van gekozen taal
     */

    public void taalGekozen() {
        MenuPaneel menuPaneel = new MenuPaneel(this, domeinController, new TaalPaneel(domeinController, this));
        setCenter(menuPaneel);
    }

    /**
     * UC3: setter van domeincontroller. Kan niet anders omdat hoofdpaneel wordt
     * gemaakt voordat taal gekozen wordt. Hoofdpaneel maakt immers taalpaneel voor
     * die keuze
     *
     * @param domeinController dc (wordt maar eenmalig gedaan om singleton te
     *                         garanderen)
     */

    public void setDomeincontroller(DomeinController domeinController) {
        this.domeinController = domeinController;
    }
}
