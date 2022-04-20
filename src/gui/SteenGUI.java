package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SteenGUI extends ImageView {
    private final int waarde;

    /**
     * UC3 constructor voor steen. Juiste foto wordt automatisch gekoppeld op basis van de waarde
     * Vakje heeft bij initialisatie een steen met waarde nul zonder foto.
     * @param waarde: waarde van de steen
     */

    public SteenGUI(int waarde) {
        this.waarde = waarde;
        Image image = koppelJuisteFotoAanWaarde(waarde);
        super.setImage(image);
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
            String pad = "file:src/assets/stenen/";
            String filename = this.getWaarde() + ".png";
            return new Image(pad + filename);
        }
    }

    public int getWaarde() {
        return waarde;
    }

}
