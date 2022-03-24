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

    /**
     * UC3: constructor ScorebladRegel, om aan te geven welke punten er gehaald zijn gebruiken we een boolean per puntencategorie, 
     * deze wordt doorgegeven als parameter.
     * Tess: vraagje: het zal uiteindelijk spel zelf zijn die de punten als boolean doorgeeft? via scoreblad aan scorebladRegel? 
     * ik veronderstel niet dat we kunnen spreken van overerving tussen Scoreblad en ScorebladRegel, 
     * Moet er dan op het DCD nog een verbinding worden gemaakt tussen ScorebladRegel en Spel of tussen Scoreblad en Speler? 
     * Graag hier extra aandacht voor want heb het zelf moeilijk om dit te begrijpen. 
     * @param dubbeleScore
     * @param tienPunten
     * @param elfPunten
     * @param twaalfPunten
     * @param bonusPunten
     */
    public ScorebladRegel(boolean dubbeleScore, boolean tienPunten, boolean elfPunten, boolean twaalfPunten, int bonusPunten) {
        this.dubbeleScore = dubbeleScore;
        this.tienPunten = tienPunten;
        this.elfPunten = elfPunten;
        this.twaalfPunten = twaalfPunten;
        //Tess: kan onderstaande uitgelegd worden, en helpt het eventueel om dit in een aparte methode te steken? zoals we gedaan hebben bij Speler? 
        if (!List.of(3,4,5,6).contains(bonusPunten)) {
            throw new IllegalArgumentException("ONGELDIGE_BONUSPUNTEN");
        }

        this.bonusPunten = bonusPunten;
        this.scoreVoorRegel = berekenScore();
    }
    
    /**
     * UC3: methode berekent de Score op basis van de booleans
     *  
     * @return
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


    /**
     * getter om score per regel op te vragen. 
     * @return
     */
    public int getScoreVoorRegel() {
        return scoreVoorRegel;
    }
}
