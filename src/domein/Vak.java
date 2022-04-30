package domein;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Vak {
    public enum Kleur {WIT, ZWART}

    private final int kolom;
    private final int rij;
    private Kleur kleur;
    private Steen steen;

    /**
     * UC3 constructor voor vak, kleur wordt standaard op zwart ingesteld
     *
     * @param kolom: kolom van vakje
     * @param rij:   rij van vakje
     */
    public Vak(int kolom, int rij) {
        controleerOfVakjeKanBestaan(kolom, rij);

        this.kolom = kolom;
        this.rij = rij;
        setKleur(Kleur.ZWART);
    }

    /**
     * UC3 controleert of het vakje wel degelijk kan bestaan.
     *
     * @param kolom kolom van vakje
     * @param rij   rij van vakje
     * @throws IllegalArgumentException indien vakje onmogelijk kan bestaan op bord.
     */

    private void controleerOfVakjeKanBestaan(int kolom, int rij) {
        List<Integer> controleLijst = List.of(2, 3, 4, 8, 12, 13, 14);

        //case vakje buiten bord
        if (kolom > 15 || kolom < 0 || rij < 0 || rij > 15) {
            throw new IllegalArgumentException("VAKJE_KAN_NIET_BESTAAN");
        }

        //case vakje uit bovenste rij bestaat niet
        if (rij == 1 && controleLijst.contains(kolom)) {
            throw new IllegalArgumentException("VAKJE_KAN_NIET_BESTAAN");
        }

        //case vakje uit onderste rij bestaat niet
        if (rij == 15 && controleLijst.contains(kolom)) {
            throw new IllegalArgumentException("VAKJE_KAN_NIET_BESTAAN");
        }

        //case vakje uit linker rij bestaat niet
        if (kolom == 1 && controleLijst.contains(rij)) {
            throw new IllegalArgumentException("VAKJE_KAN_NIET_BESTAAN");
        }

        //case vakje uit rechter rij bestaat niet
        if (kolom == 15 && controleLijst.contains(rij)) {
            throw new IllegalArgumentException("VAKJE_KAN_NIET_BESTAAN");
        }
    }


    /**
     * UC4 geeft de vakjes rond het vakje terug als een map. De map heeft volgende structuur:
     * key: boven, onder, links, rechts => de positie tegenover het vakje waarop deze methode is aangeroepen
     * value: de coordinaten van dit vakje in kolom.rij vorm. Indien vakje niet bestaat, bevat het de value "bestaat niet"
     *
     * @return Map met daarin de omringende steentjes en hun coordinaten.
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
            omringendeVakjes.put("boven", String.format("%d.%d", kolom, rij - 1));
        }

        if (!omringendeVakjes.containsKey("onder")) {
            omringendeVakjes.put("onder", String.format("%d.%d", kolom, rij + 1));
        }

        if (!omringendeVakjes.containsKey("links")) {
            omringendeVakjes.put("links", String.format("%d.%d", kolom - 1, rij));
        }

        if (!omringendeVakjes.containsKey("rechts")) {
            omringendeVakjes.put("rechts", String.format("%d.%d", kolom + 1, rij));
        }

        return omringendeVakjes;
    }

    /**
     * UC3: geeft terug of er een steen op vak staat
     *
     * @return of er een steen op vak staat
     */


    public boolean bevatSteen() {
        return Objects.nonNull(steen);
    }

    /**
     * UC3: geeft coordinaten van vak terug als string
     *
     * @return coordinaten
     */


    public String getCoordinatenVanVak() {
        return String.format("%d.%d", kolom, rij);
    }

    /**
     * UC3: getter voor kolom
     *
     * @return kolom
     */

    public int getKolom() {
        return kolom;
    }

    /**
     * UC3: getter voor rij
     *
     * @return rij
     */


    public int getRij() {
        return rij;
    }

    /**
     * UC3: getter voor kleur
     *
     * @return kleur
     */


    public Kleur getKleur() {
        return kleur;
    }

    /**
     * UC3: getter voor kleur
     *
     * @return kleur
     */


    public Steen getSteen() {
        return steen;
    }

    /**
     * UC3: setter voor steen
     *
     * @param kleur nieuwe kleur
     */


    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
    }

    /**
     * UC3: setter voor kolom
     *
     * @param steen nieuwe steen
     */


    public void setSteen(Steen steen) {
        this.steen = steen;
    }
}
