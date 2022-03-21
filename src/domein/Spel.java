package domein;

import java.util.*;

public class Spel {
    private enum SpelStaat {IN_VOORBEREIDING, GESTART, GEDAAN, GECANCELED}

    private SpelStaat spelStaat;
    private final List<Speler> spelers;
    private final Spelbord spelbord;
    private final List<Steen> stenen;
    private final List<Scoreblad> scorebladen;


    public Spel(List<Speler> spelers) {
        this.spelers = spelers;
        this.spelStaat = SpelStaat.IN_VOORBEREIDING;
        this.spelbord = new Spelbord();
        this.stenen = genereerStenen();
        this.scorebladen = new ArrayList<>();
    }

    private List<Steen> genereerStenen() {
        ArrayList<Steen> stenen = new ArrayList<>();

        for (int i = 0; i <20; i++){
            stenen.add(new Steen(1));
            stenen.add(new Steen(2));
            stenen.add(new Steen(3));
            stenen.add(new Steen(4));
            stenen.add(new Steen(5));
            stenen.add(new Steen(6));
        }

        stenen.add(new Steen(1));
        Collections.shuffle(stenen);
        return  stenen;
    }


    public void startSpel() {
        if (spelers.size() < 2)
            throw new IllegalArgumentException("TE_WEINIG_SPELERS");

        spelers.forEach((e) -> {
            e.setSpeelkansen(e.getSpeelkansen() - 1);
            scorebladen.add(new Scoreblad(e));});

        bepaalVolgordeSpelers();
        this.spelStaat = SpelStaat.GESTART;

        while (checkOfSpelNogBezig()) {
            for (Speler speler : spelers) {
                speelBeurt(speler);
                checkOfSpelNogBezig();
                if (this.spelStaat.equals(SpelStaat.GEDAAN) || this.spelStaat.equals(SpelStaat.GECANCELED))
                    break;
            }
            if (this.spelStaat.equals(SpelStaat.GEDAAN))
                registreerWinnaar(bepaalWinnaar());
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
        //navragen: het lijkt onlogisch om alle spelers te straffen voor een cancel.
    }

    private void speelBeurt(Speler speler) {
        throw new UnsupportedOperationException(); //todo
    }

    private Speler bepaalWinnaar() {
        return scorebladen.stream()
                .sorted(Comparator.comparing(Scoreblad::berekenScoreVanScoreblad))
                .map(Scoreblad::getSpeler)
                .findFirst()
                .get();
        //isPresent check irrelevant. Spel gaat nooit starten met minder dan twee spelers.
    }

    private boolean checkOfSpelNogBezig() {
        if (stenen.isEmpty()) {
            this.spelStaat = SpelStaat.GEDAAN;
            return false;
        }
        return true;

    }

    public Scoreblad geefScoreblad(Speler speler){
        Optional<Scoreblad> mogelijkGevondenScoreblad = scorebladen.stream()
                .filter((scoreblad)->scoreblad.getSpeler().equals(speler))
                .findFirst();

        if (mogelijkGevondenScoreblad.isPresent())
            return mogelijkGevondenScoreblad.get();
        else
            throw new IllegalArgumentException("GEEN_SCOREBLAD");
    }

    public int toonScore(Speler speler){
        return geefScoreblad(speler).berekenScoreVanScoreblad();
    }

    public Speler toonWinnaar(){
        return bepaalWinnaar();
    }

}
