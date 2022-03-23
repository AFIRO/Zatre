package domein;

import java.util.List;

//Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.
public class ScorebladRegel {
    private final boolean dubbeleScore;
    private final boolean tienPunten;
    private final boolean elfPunten;
    private final boolean twaalfPunten;
    private final int bonusPunten;
    private final int scoreVoorRegel;

    public ScorebladRegel(boolean dubbeleScore, boolean tienPunten, boolean elfPunten, boolean twaalfPunten, int bonusPunten) {
        this.dubbeleScore = dubbeleScore;
        this.tienPunten = tienPunten;
        this.elfPunten = elfPunten;
        this.twaalfPunten = twaalfPunten;

        if (!List.of(3,4,5,6).contains(bonusPunten)) {
            throw new IllegalArgumentException("ONGELDIGE_BONUSPUNTEN");
        }

        this.bonusPunten = bonusPunten;
        this.scoreVoorRegel = berekenScore();
    }

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



    public int getScoreVoorRegel() {
        return scoreVoorRegel;
    }
}
