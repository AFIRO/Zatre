package gui;

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
     * UC3 setter voor steen op vak
     */

    public void setSteen(SteenGUI steen) {
        this.steen = steen;
    }

    /**
     * UC3 getter voor rij
     */

    public int getRij() {
        return rij;
    }

    /**
     * UC3 getter voor kolom
     */


    public int getKolom() {
        return kolom;
    }

    /**
     * UC3 genereert de coordinaten van de steen voor gebruik in domein
     */


    public String getCoordinaten() {
        return String.format("%d.%d", getKolom(), getRij());
    }

}
