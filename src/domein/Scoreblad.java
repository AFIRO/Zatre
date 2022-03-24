package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scoreblad {
    private final Speler speler;
    private final List<ScorebladRegel> regels; //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.

    /**
     * UC3: constructor van Scoreblad
     * @param speler
     */
    public Scoreblad(Speler speler) {
        this.speler = speler;
        this.regels = new ArrayList<>();
    }
    /**
     * UC3: vraag speler op (via getter?) 
     * Vraag Tess: waarom hier getter als methode? 
     */
    public Speler getSpeler() {
        return speler;
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
     * @param scorebladRegel
     */
    //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.
    public void voegRegelToeAanScoreblad(ScorebladRegel scorebladRegel) {
        if (Objects.nonNull(scorebladRegel))
            regels.add(scorebladRegel);
    }
    
    /**
     * UC3: gebruikt stream om per opgeslagen ScorebladRegel uit lijst regels de punten op te vragen en telt deze dan op. 
     * Hierdoor wordt de uiteindelijke som berekend. 
     * @return int score
     */
    //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.
    public int berekenScoreVanScoreblad(){
        return regels.stream()
                .map(ScorebladRegel::getScoreVoorRegel)
                .reduce(0, Integer::sum);
    }

}
