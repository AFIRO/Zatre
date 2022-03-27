package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scoreblad {
    private final List<ScorebladRegel> regels;

    /**
     * UC3: constructor van Scoreblad
     */
    public Scoreblad() {
        this.regels = new ArrayList<>();
    }

    /**
     * UC3: getter: geeft de lijst van ScorebladRegel terug. 
     * (wat in principe gelijk is aan het volledige scoreblad per speler?) 
     * Tess: kunnen we dan de naam niet anders vermelden? 
     */
    
    public List<ScorebladRegel> getRegels() {
        return regels;
    }
    /**
     * UC3: Voegt een ScorebladRegel object toe aan de lijst regels. 
     * @param scorebladRegel de toe te voegen regel
     */
    public void voegRegelToeAanScoreblad(ScorebladRegel scorebladRegel) {
        if (Objects.nonNull(scorebladRegel))
            regels.add(scorebladRegel);
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
