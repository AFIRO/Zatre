package domein;

import java.util.Collections;
import java.util.List;

public class Spel {
    private enum SpelStaat {IN_VOORBEREIDING, GESTART, GEDAAN, GECANCELED}

    private SpelStaat spelStaat;
    private final List<Speler> spelers;
    private final Spelbord spelbord;
    private final List<Steen> stenen;


    public Spel(List<Speler> spelers) {
        this.spelers = spelers;
        this.spelStaat = SpelStaat.IN_VOORBEREIDING;
        this.spelbord = new Spelbord();
        this.stenen = genereerStenen();
    }

    private List<Steen> genereerStenen() {

        return  null;
    }


    public void startSpel() {
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() - 1));
        bepaalVolgordeSpelers();
        this.spelStaat = SpelStaat.GESTART;

        while (checkOfSpelNogBezig()) {
            for (Speler speler : spelers) {
                speelBeurt(speler);
                if (this.spelStaat.equals(SpelStaat.GEDAAN) || this.spelStaat.equals(SpelStaat.GECANCELED))
                    break;
            }
            if (this.spelStaat.equals(SpelStaat.GEDAAN))
                berekenScore();
            //registreerWinnaar(spelerMetHoogsteScore);
        }

    }

    private void bepaalVolgordeSpelers() {
        Collections.shuffle(spelers);
    }

    private void registreerWinnaar(Speler speler) {
        speler.setSpeelkansen(speler.getSpeelkansen() + 2);
    }

    private void cancelSpel() {
        this.spelStaat = SpelStaat.GECANCELED;
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() + 1));
    }

    private void speelBeurt(Speler speler) {
        throw new UnsupportedOperationException(); //todo
    }

    private void berekenScore() {
        throw new UnsupportedOperationException(); //todo
    }

    private boolean checkOfSpelNogBezig() {
        //if (gebeurtenis waardoorspel gedaan is){
        //  this.spelStaat = SpelStaat.GEDAAN;
        //  return false;
        //  }
        //return true;

        throw new UnsupportedOperationException(); //todo
    }

}
