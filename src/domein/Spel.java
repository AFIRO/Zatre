package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spel {
    private enum SpelStaat {IN_VOORBEREIDING, GESTART, GEDAAN, GECANCELED}
    private SpelStaat spelStaat;
    private final List<Speler> spelers;


    public Spel() {
        this.spelers = new ArrayList<>();
        this.spelStaat = SpelStaat.IN_VOORBEREIDING;
        maakBord();
    }

    public void voegSpelerToeAanSpel(Speler speler){
        if (spelers.contains(speler)) {
            throw new IllegalArgumentException("SPELER_AL_AANGEMELD");
        }

        if (speler.getSpeelkansen() < 1) {
            throw  new IllegalArgumentException("GEEN_SPEELKANSEN_MEER");
        }

        if (spelers.size() >= 4)
            throw new IllegalArgumentException("MAX_AANTAL_SPELERS_BEREIKT");

        spelers.add(speler);
    }

    public void startSpel() {
        spelers.forEach((e)-> e.setSpeelkansen(e.getSpeelkansen()-1));
        bepaalVolgordeSpelers();
        this.spelStaat = SpelStaat.GESTART;

        while (checkOfSpelNogBezig()){
            for (Speler speler : spelers){
                speelBeurt(speler);
                if (this.spelStaat.equals(SpelStaat.GEDAAN) || this.spelStaat.equals(SpelStaat.GECANCELED))
                    break;
            }
        if (this.spelStaat.equals(SpelStaat.GEDAAN))
        berekenScore();
        //registreerWinnaar(spelerMetHoogsteScore);
        }

    }

    public void bepaalVolgordeSpelers() {
        Collections.shuffle(spelers);
    }

    public Speler registreerWinnaar(Speler speler) {
        speler.setSpeelkansen(speler.getSpeelkansen()+2);
        return speler;
    }

    private void cancelSpel(){
        this.spelStaat = SpelStaat.GECANCELED;
        spelers.forEach((e)-> e.setSpeelkansen(e.getSpeelkansen()+1));
    }

    private void speelBeurt(Speler speler){
        throw new UnsupportedOperationException(); //todo
    }

    private void berekenScore() {
        throw new UnsupportedOperationException(); //todo
    }

    private boolean checkOfSpelNogBezig(){
        //if (gebeurtenis waardoorspel gedaan is){
        //  this.spelStaat = SpelStaat.GEDAAN;
        //  return false;
        //  }
        //return true;

        throw new UnsupportedOperationException(); //todo
    }

    private void maakBord() {
        throw new UnsupportedOperationException(); //todo
    }
}
