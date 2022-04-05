package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Scoreblad {
    private final List<ScorebladRegel> regels;

    /**
     * UC3: constructor van Scoreblad
     */
    public Scoreblad() {
        this.regels = new ArrayList<>();
    }

    /**
     * UC3: geeft een lijst van String weergaves van de individuele regels terug voor gebruik in GUI.
     * @return de lijst aan string weergaves.
     */
    
    public List<String> getRegels() {
        return regels.stream()
                .map(ScorebladRegel::toString)
                .collect(Collectors.toList());
    }
    /**
     * UC3: Voegt een ScorebladRegel object toe aan de lijst regels.
     * Genereert bonuspunten op basis van aantal regel op scoreblad.
     * @param dubbeleScore boolean die bijhoudt of in die beurt de score moet verdubbeld worden omdat de steen op een wit vak stond.
     * @param tienPunten boolean die bijhoudt of in die beurt de score 10 werd bereikt
     * @param elfPunten boolean die bijhoudt of in die beurt de score 11 werd bereikt
     * @param twaalfPunten boolean die bijhoudt of in die beurt de score 12 werd bereikt
     */
    public void voegRegelToeAanScoreblad(boolean dubbeleScore, boolean tienPunten, boolean elfPunten, boolean twaalfPunten) {
        int BonusPunten = 0;

        if (this.regels.size() < 4)
            BonusPunten = 3;

        if (this.regels.size() >= 4 &&this.regels.size() < 8)
            BonusPunten = 4;

        if (this.regels.size() >= 8 &&this.regels.size() < 12)
            BonusPunten = 5;

        if (this.regels.size() >= 12)
            BonusPunten = 6;

        regels.add(new ScorebladRegel(dubbeleScore,tienPunten,elfPunten,twaalfPunten,BonusPunten));
    }
    
    /**
     * UC3: gebruikt stream om per opgeslagen ScorebladRegel uit lijst regels de punten op te vragen en telt deze dan op. 
     * Hierdoor wordt de uiteindelijke som berekend. 
     * @return int score
     */
    public int berekenScoreVanScoreblad(){
        return regels.stream()
                .map(ScorebladRegel::getScoreVoorRegel)
                .reduce(0, Integer::sum);
    }

}
