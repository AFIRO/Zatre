package domein;

import java.util.List;

public class ScorebladRegel {
    private final boolean dubbeleScore;
    private final boolean tienPunten;
    private final boolean elfPunten;
    private final boolean twaalfPunten;
    private final int bonusPunten;
    private final int scoreVoorRegel;

    /**
     * UC3: constructor ScorebladRegel, om aan te geven welke punten er gehaald zijn gebruiken we een boolean per puntencategorie, 
     * deze worden doorgegeven als parameter samen met de bonuspunten. Bij creatie wordt score berekend.
     * @param dubbeleScore boolean die bijhoudt of in die beurt de score moet verdubbeld worden omdat de steen op een wit vak stond.
     * @param tienPunten boolean die bijhoudt of in die beurt de score 10 werd bereikt
     * @param elfPunten boolean die bijhoudt of in die beurt de score 11 werd bereikt
     * @param twaalfPunten boolean die bijhoudt of in die beurt de score 12 werd bereikt
     * @param bonusPunten de bonuspunten voor die beurt.
     */
    public ScorebladRegel(boolean dubbeleScore, boolean tienPunten, boolean elfPunten, boolean twaalfPunten, int bonusPunten) {
        this.dubbeleScore = dubbeleScore;
        this.tienPunten = tienPunten;
        this.elfPunten = elfPunten;
        this.twaalfPunten = twaalfPunten;

        controleerOfBonusPuntenEenToegelatenWaardeHebben(bonusPunten);

        this.bonusPunten = bonusPunten;
        this.scoreVoorRegel = berekenScore();
    }

    /**
     * UC3: methode die controleert of de bonuspunten een toegelaten waarde hebben van 3, 4, 5 of 6.
     * @throws IllegalArgumentException indien de waarde niet toegelaten is.
     */

    private void controleerOfBonusPuntenEenToegelatenWaardeHebben(int bonusPunten) {
        if (!List.of(3, 4, 5, 6).contains(bonusPunten))
            throw new IllegalArgumentException("ONGELDIGE_BONUSPUNTEN");
    }

    /**
     * UC3: methode berekent de Score op basis van de booleans
     * @return de berekende score op basis van interne gegevens van de regel.
     */
    private int berekenScore() {
        int score = 0;

        if (tienPunten)
            score+= 1;

        if (elfPunten)
            score+= 2;

        if (twaalfPunten)
            score+= 4;

        if (tienPunten && elfPunten && twaalfPunten)
            score+= bonusPunten;

        if (dubbeleScore)
            score = score*2;

        return score;
    }

    @Override
    public String toString() {

        String dubbeleScore = this.dubbeleScore ? "X" : " ";
        String tienPunten = this.tienPunten ? "X" : " ";
        String elfPunten = this.elfPunten ? "X" : " ";
        String twaalfPunten = this.twaalfPunten ? "X" : " ";

        return dubbeleScore + " "
                + tienPunten + " "
                + elfPunten + " "
                + twaalfPunten + " "
                + bonusPunten + " "
                + scoreVoorRegel;
    }

    /**
     * UC3: Getter om score per regel op te vragen.
     * @return score per regel
     */
    public int getScoreVoorRegel() {
        return scoreVoorRegel;
    }
}
