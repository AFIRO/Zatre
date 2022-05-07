package domein;

import java.util.ArrayList;
import java.util.List;

public class ScorebladRegel {
    private final boolean dubbeleScore;
    private final ArrayList<Boolean> tienPunten;
    private final ArrayList<Boolean> elfPunten;
    private final ArrayList<Boolean> twaalfPunten;
    private final int bonusPunten;
    private int scoreVoorRegel;

    /**
     * UC3: constructor ScorebladRegel, om aan te geven welke punten er gehaald zijn
     * gebruiken we een boolean per puntencategorie, bij creatie wordt score
     * berekend.
     *
     * @param dubbeleScore boolean die bijhoudt of in die beurt de score moet
     *                     verdubbeld worden omdat de steen op een wit vak stond.
     * @param bonusPunten  de bonuspunten voor die beurt.
     */
    public ScorebladRegel(boolean dubbeleScore, int bonusPunten) {
        this.dubbeleScore = dubbeleScore;

        tienPunten = new ArrayList<>();
        elfPunten = new ArrayList<>();
        twaalfPunten = new ArrayList<>();

        controleerOfBonusPuntenEenToegelatenWaardeHebben(bonusPunten);

        this.bonusPunten = bonusPunten;
        this.scoreVoorRegel = 0;
    }

    /**
     * UC3: methode die controleert of de bonuspunten een toegelaten waarde hebben
     * van 3, 4, 5 of 6.
     *
     * @throws IllegalArgumentException indien de waarde niet toegelaten is.
     */

    private void controleerOfBonusPuntenEenToegelatenWaardeHebben(int bonusPunten) {
        if (!List.of(3, 4, 5, 6).contains(bonusPunten))
            throw new IllegalArgumentException("ONGELDIGE_BONUSPUNTEN");
    }

    /**
     * UC3: methode berekent de Score op basis van de booleans
     *
     * @return de berekende score op basis van interne gegevens van de regel.
     */
    private int berekenScore() {
        int score = 0;

        for (Boolean tienpunten : this.tienPunten)
            if (tienpunten) {
                score += 1;
            }

        for (Boolean elfpunten : this.elfPunten)
            if (elfpunten) {
                score += 2;
            }

        for (Boolean twaalfpunten : this.twaalfPunten)
            if (twaalfpunten) {
                score += 4;
            }

        if (tienPunten.contains(Boolean.TRUE) && elfPunten.contains(Boolean.TRUE)
                && twaalfPunten.contains(Boolean.TRUE))
            score += bonusPunten;

        if (dubbeleScore)
            score = score * 2;

        return score;
    }

    /**
     * UC3: genereert een passende toString op basis van de inhoud van de regel
     *
     * @return string weergave van de regel
     */

    @Override
    public String toString() {

        String dubbeleScore = this.dubbeleScore ? "X" : " ";
        String tienPuntenString = " ";
        String elfPuntenString = " ";
        String twaalfPuntenString = " ";

        for (Boolean tienpunten : this.tienPunten)
            if (tienpunten) {
                tienPuntenString = tienPuntenString.trim().concat("X");
            }

        for (Boolean elfpunten : this.elfPunten)
            if (elfpunten) {
                elfPuntenString = elfPuntenString.trim().concat("X");
            }

        for (Boolean twaalfpunten : this.twaalfPunten)
            if (twaalfpunten) {
                twaalfPuntenString = twaalfPuntenString.trim().concat("X");
            }

        return dubbeleScore + "," + tienPuntenString + "," + elfPuntenString + "," + twaalfPuntenString + ","
                + bonusPunten + "," + scoreVoorRegel;
    }

    /**
     * UC3: Getter om score per regel op te vragen.
     *
     * @return score per regel
     */
    public int getScoreVoorRegel() {
        return scoreVoorRegel;
    }

    /**
     * UC4: Getter om te checken of dit een bonusregel is of niet.
     *
     * @return of dit een bonusregel is
     */
    public boolean isDubbeleScore() {
        return dubbeleScore;
    }

    /**
     * UC4: Pas de actieve ronde regel aan met kruisjes uit een volgende zet. De
     * score wordt op het einde herberekend.
     *
     * @param score de toe te voegen score
     */
    public void pasRegelAanMetVerdereScores(int score) {
        switch (score) {
            case 10 -> this.tienPunten.add(true);
            case 11 -> this.elfPunten.add(true);
            case 12 -> this.twaalfPunten.add(true);
        }
        this.scoreVoorRegel = berekenScore();
    }

}
