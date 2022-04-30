package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VakGUI extends Rectangle {
    private SteenGUI steen;
    private final int rij;
    private final int kolom;

    /**
     * UC3 constructor voor vak, kleur wordt standaard op zwart ingesteld
     * Vakje heeft bij initialisatie een steen met waarde nul zonder foto.
     *
     * @param zijkant: de zijkant van vierkant
     * @param kolom:   kolom van vakje
     * @param rij:     rij van vakje
     */

    public VakGUI(int zijkant, int rij, int kolom) {
        this.rij = rij;
        this.kolom = kolom;
        setHeight(zijkant);
        setWidth(zijkant);
        relocate(rij * zijkant, kolom * zijkant);
    }

    /**
     * UC3 verandert kleur van vakje naar wit
     */

    public void veranderKleurNaarWit() {
        this.setFill(Color.WHITE);
    }

    /**
     * UC3 genereert de coordinaten van alle vakjes rond dit vakje.
     */
    public Map<String, String> geefVakjesNaastVak() {
        List<Integer> controleLijst = List.of(2, 3, 4, 8, 12, 13, 14);
        Map<String, String> omringendeVakjes = new HashMap<>();

        if (rij == 0) {
            omringendeVakjes.put("boven", "bestaat niet");
        }

        if (rij == 15) {
            omringendeVakjes.put("onder", "bestaat niet");
        }

        if (kolom == 0) {
            omringendeVakjes.put("links", "bestaat niet");
        }

        if (kolom == 15) {
            omringendeVakjes.put("rechts", "bestaat niet");
        }

        if (rij == 1 && controleLijst.contains(kolom)) {
            omringendeVakjes.put("boven", "bestaat niet");
        }

        if (rij == 14 && controleLijst.contains(kolom)) {
            omringendeVakjes.put("onder", "bestaat niet");
        }

        if (kolom == 1 && controleLijst.contains(rij)) {
            omringendeVakjes.put("links", "bestaat niet");
        }

        if (kolom == 14 && controleLijst.contains(rij)) {
            omringendeVakjes.put("rechts", "bestaat niet");
        }

        if (!omringendeVakjes.containsKey("boven")) {
            omringendeVakjes.put("boven", String.format("%d.%d", kolom - 1, rij));
        }

        if (!omringendeVakjes.containsKey("onder")) {
            omringendeVakjes.put("onder", String.format("%d.%d", kolom + 1, rij));
        }

        if (!omringendeVakjes.containsKey("links")) {
            omringendeVakjes.put("links", String.format("%d.%d", kolom, rij - 1));
        }

        if (!omringendeVakjes.containsKey("rechts")) {
            omringendeVakjes.put("rechts", String.format("%d.%d", kolom, rij + 1));
        }

        return omringendeVakjes;
    }

    /**
     * UC3 controleert of vakje een steen bevat.
     * By default bevat elk vakje een "doorzichtig" steentje met score 0.
     *
     * @return of het vakje een steen bevat.
     */

    public boolean bevatVakEenSteen() {
        return !(this.steen.getWaarde() == 0);
    }

    /**
     * UC3 Bij start van spel mag er enkel een tegeltje worden gezet op vakje 8.8.
     * Deze methode controleert dit.
     *
     * @return of het vakje effectief het start vakje is.
     */

    public boolean isEersteVakVanSpel() {
        return rij == 8 && kolom == 8;
    }

    public void setSteen(SteenGUI steen) {
        this.steen = steen;
    }

    public int getWaardeVanSteen() {
        return this.steen.getWaarde();
    }

    public int getRij() {
        return rij;
    }

    public int getKolom() {
        return kolom;
    }

    public String getCoordinaten() {
        return String.format("%d.%d", getKolom(), getRij());
    }

}
