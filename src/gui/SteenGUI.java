package gui;

import javafx.scene.image.Image;

public class SteenGUI {
    private final int waarde;
    private final Image image;

    /**
     * UC3 constructor voor steen. Juiste foto wordt automatisch gekoppeld op basis van de waarde
     * Vakje heeft bij initialisatie een steen met waarde nul zonder foto.
     * @param waarde: waarde van de steen
     */

    public SteenGUI(int waarde) {
        this.waarde = waarde;
        this.image = koppelJuisteFotoAanWaarde(waarde);
    }


    /**
     * UC3 haal de foto van de juiste steen uit assets op basis van de waarde van de steen en koppelt deze aan de steen.
     * Bij waarde 0 is de foto blanco en dus doorzichtig.
     * @param waarde: waarde van de steen
     */

    private Image koppelJuisteFotoAanWaarde(int waarde) {
        if (waarde == 0)
            return null;
        else {
            String pad = "assets/stenen/";
            String filename = this.getWaarde() + ".png";
            return new Image(pad + filename);
        }
    }

    public int getWaarde() {
        return waarde;
    }

    public Image getImage() {
        return image;
    }
}
