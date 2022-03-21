package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scoreblad {
    private final Speler speler;
    private final List<ScorebladRegel> regels; //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.

    public Scoreblad(Speler speler) {
        this.speler = speler;
        this.regels = new ArrayList<>();
    }

    public Speler getSpeler() {
        return speler;
    }

    public List<ScorebladRegel> getRegels() {
        return regels;
    }

    //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.
    public void voegRegelToeAanScoreblad(ScorebladRegel scorebladRegel) {
        if (Objects.nonNull(scorebladRegel))
            regels.add(scorebladRegel);
    }

    //Andreeas: afwijking DCD te bespreken na feedback. Voorstel voor code.
    public int berekenScoreVanScoreblad(){
        return regels.stream()
                .map(ScorebladRegel::getScoreVoorRegel)
                .reduce(0, Integer::sum);
    }

}
