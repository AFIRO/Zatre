package domein;

import java.util.*;

public class Spel {
    private enum SpelStaat {IN_VOORBEREIDING, GESTART, GEDAAN, GECANCELED}

    private SpelStaat spelStaat;
    private final List<Speler> spelers;
    private final Spelbord spelbord;
    private final List<Steen> stenen;

    /**
     * UC3: constructor van Spel
     * lijst van spelers wordt hieraan doorgegeven. 
     * In de domeincontroller wordt hiervoor de lijst van aangemelde spelers uit de repository gehaald.
     * De spelstaat wordt op .IN_VOORBEREIDING ingesteld
     * een nieuw spelbord wordt gegenereerd
     * de stenen worden gegenereerd door middel van methode in deze klasse.
     * er wordt een arraylist ingesteld voor de scorebladen. 
     * @param spelers de lijst spelers die het spel gaan spelen.
     */
    public Spel(List<Speler> spelers) {
        this.spelers = spelers;
        this.spelStaat = SpelStaat.IN_VOORBEREIDING;
        this.spelbord = new Spelbord();
        this.stenen = genereerStenen();
    }
    
    /**
     * UC3: methode genereerSteen
     * Lijst van stenen wordt aangemaakt: eerst 20 stenen van elk (1-6), vervolgens nog een extra 1 steen
     * Dit maakt een lijst van 121 stenen. 
     * Vervolgens worden deze door elkaar gegooid. 
     * De methode geeft de geshuffelde lijst stenen terug. 
     * @return de lijst gegenereerde stenen
     */
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

    /**
     * UC3: startSpel methode
     * in de domeincontroller wordt eerst een constructor van Spel aangeroepen en vervolgens volgende methode toegepast. 
     * we gaan kijken of de lijst minimum 2 spelers bevat (het maximum wordt reeds getest in de repository)
     * we gaan de speelkansen verminderen met -1
     * Gezien Speler scoreblad kent kan ook Spel daar nu ook aan.
     * Vervolgens gaan we met bepaalVolgordeSpelers shuffelen en de SpelStaat op GESTART zetten
     */
    public void startSpel() {
        checkOfVoldoendeSpelersOmSpelTeStarten();
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() - 1));
        bepaalVolgordeSpelers();
        this.spelStaat = SpelStaat.GESTART;
    }

    /**
     * UC3: Checked of er voldoende spelers in spel zitten
     * @throws IllegalArgumentException indien onvoldoende spelers
     */

    private void checkOfVoldoendeSpelersOmSpelTeStarten() {
        if (spelers.size() < 2)
            throw new IllegalArgumentException("TE_WEINIG_SPELERS");
    }

    /**
     * UC3: methode om volgorde spelers willekeurig te zetten
     */
    private void bepaalVolgordeSpelers() {
        Collections.shuffle(spelers);
    }
    /**
     * UC3: speelkansen van winnende speler worden +2 toegevoegd
     * @param speler de winnaar van het spel
     */
    private void pasSpeelkansenWinnaarAan(Speler speler) {
        speler.setSpeelkansen(speler.getSpeelkansen() + 2);
    }


    /**
     * UC4: regelt de logica van een beurt. De string[] zetten bevat 2 of 3 strings met de zet
     * in de vorm "kolomVak.rijVak waardeSteen". Deze worden geïnterpreteerd en omzet naar de correcte info voor punt
     * berekening. We gaan ervan uit dat de info correct moet zijn dankzij validatie in de GUI qua zetten
     * @param gebruikersnaam gebruikersnaam van speler die beurt speelt
     * @param geboortejaar geboortejaar van speler die beurt speelt
     * @param zetten bevat 2 of 3 strings met de zet in de vorm "kolomVak.rijVak waardeSteen"
     * @return een lijst aan Strings die het scoreblad na het spelen van de beurt moeten voorstellen
     */
    public List<String> speelBeurt(String gebruikersnaam, String geboortejaar, String[] zetten) {
        checkOfSpelerInHetSpelZit(gebruikersnaam, geboortejaar);
        Speler actieveSpeler = spelers.get(spelers.indexOf(new Speler(gebruikersnaam,Integer.parseInt(geboortejaar))));
        ArrayList<Boolean[]> puntenArraysVoorAlleZetten = new ArrayList<>();
        //itereer door alle doorgegeven zetten
        for (String zet: zetten) {
            //haal het correct vakje uit het spelbord op basis van eerste 3 tekens in string zijde kolomVak.rijVak.
            Vak vakWaaropSteenWerdGelegd = spelbord.getVakjes().get(zet.substring(0, 2));
            CheckOfVakjeLeegIs(vakWaaropSteenWerdGelegd);
            //leg het correct steentje op het doorgegeven vak op basis van 5de teken. 4de teken is een spatie.
            vakWaaropSteenWerdGelegd.setSteen(new Steen(Integer.parseInt(zet.substring(4))));
            //update punten array met de berekening van de zet. Dit zal twee of drie keer updaten afhankelijk van aantal zetten.
            puntenArraysVoorAlleZetten.addAll(berekenScoreVoorGelegdeSteen(vakWaaropSteenWerdGelegd));
        }
        //genereer scorebladRegels op basis van vier booleans in de boolean arrays per zet.
        actieveSpeler.getScoreblad().voegRegelsToeAanScoreblad(puntenArraysVoorAlleZetten);

        //geef een String representatie van de geupdatet scoreblad voor de GUI.
        return actieveSpeler.getScoreblad().getRegels();
    }

    /**
     * UC4: vraagt de naburige stenen van het vak, controleert of daar een steen op ligt en indien wel,
     * berekent de totale score van de twee vakken. Indien 10, 11 of 12, wordt dit bijgehouden.
     * Dit wordt dan doorgegeven aan het scoreblad voor berekening en opslag.
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     * @return punten Boolean array waarbij index 0 staat voor dubbele punten, index 1 voor tien punten,
     * index 2 voor elf punten en index 3 voor twaalf punten
     */

    private ArrayList<Boolean[]> berekenScoreVoorGelegdeSteen(Vak vakWaaropSteenWerdGelegd) {
        ArrayList<Boolean[]> puntenLijst = new ArrayList<>();
        Boolean[] punten;
        //genereer locaties van de vier nabije omringende stenen op basis van de doorgegeven steen
        Map<String,String> naburigeVakken = vakWaaropSteenWerdGelegd.geefVakjesNaastVak();

        //kijk in alle vier de richtingen startend van de oorspronkelijke steen
        for (String richtingVanMogelijkVak: naburigeVakken.keySet()) {
            punten = new Boolean[] {false,false,false,false};
            //indien er geen vak is in die richting (vb zijkant van bord), skip algoritme voor die richting
            if (!naburigeVakken.get(richtingVanMogelijkVak).equals("bestaat niet")) {
                //initialiseer score op basis van het gespeelde steentje op het vak.
                int score = vakWaaropSteenWerdGelegd.getSteen().getWaarde();
                //haal het vakje in die richting uit het spelbord op basis van de gegenereerde coordinaat
                Vak teCheckenVak = spelbord.getVakjes().get(naburigeVakken.get(richtingVanMogelijkVak));
                //indien er een steen op dat vakje is
                if (Objects.nonNull(teCheckenVak.getSteen())) {
                    //voeg waarde van dit vakje toe aan score
                    score += teCheckenVak.getSteen().getWaarde();
                    //zolang als de score niet 10, 11 of 12 is, kijken we naar het volgend vakje in die richting op de lijn.
                    while (score < 10) {
                        //als er geen vakje meer is,breek uit de loop
                        if (teCheckenVak.geefVakjesNaastVak().get(richtingVanMogelijkVak).equals("bestaat niet")) {
                            break;
                        }
                        //haal het volgend vakje op uit de richting
                        teCheckenVak = spelbord.getVakjes().get(teCheckenVak.geefVakjesNaastVak().get(richtingVanMogelijkVak));
                        //als er geen steen op dit vakje is,breek uit de loop
                        if (Objects.isNull(teCheckenVak.getSteen())) {
                            break;
                        }
                        //voeg de waarde van het aanwezig steentje toe aan score
                        score += teCheckenVak.getSteen().getWaarde();
                        //herhaal loop voor volgende vakken in die richting tot score > 10 of break statement.
                    }
                }
                //controleer de gevonden score. Indien hoger dan 10 en op wit vakje gestart, dubbele punten
                if (score >=10 && vakWaaropSteenWerdGelegd.getKleur().equals(Vak.Kleur.WIT)) {
                    punten[0] = true;
                }
                //controleer de gevonden score. Zet de correcte boolean op true voor 10, 11 of 12 punten.
                if (score >= 10) {
                    switch (score) {
                        case 10:
                            punten[1] = true;
                        case 11:
                            punten[2] = true;
                        case 12:
                            punten[3] = true;
                    }
                }
            }
            //sla de gevonden punten op in de arraylist
            puntenLijst.add(punten);
        } //herhaal dit algoritme voor alle vier de richtingen (boven, links, rechts, onder) om zo het punten array correct te zetten.
        return puntenLijst;
    }

    /**
     * UC4: Checked of het speler in het spel zit of niet.
     * @throws IllegalArgumentException indien speler niet in spel.
     */

    private void checkOfSpelerInHetSpelZit(String gebruikersnaam, String geboortejaar) {
        if (!spelers.contains(new Speler(gebruikersnaam,Integer.parseInt(geboortejaar)))) {
            throw new IllegalArgumentException("SPELER_ZIT_NIET_IN_SPEL");
        }
    }

    /**
     * UC4: Checked of het vakje leeg is opdat er een steen op kan gelegd worden
     * @throws IllegalArgumentException indien vakje al een steen bevat.
     */

    private void CheckOfVakjeLeegIs(Vak vakWaaropSteenWerdGelegd) {
        if (Objects.nonNull(vakWaaropSteenWerdGelegd.getSteen()))
            throw new IllegalArgumentException("VAKJE_IS_NIET_LEEG");
    }

    /**
     * UC4: sorteert de stenen in de zak, haalt er twee uit, verwijdert deze uit het zakje en geeft hun waarden terug.
     * @return  int[] met daarin de waarden van de twee stenen op index 0 en 1. Indien stenen op zijn, geeft het 0,0 terug.
     * Dit is het teken om het spel te stoppen.
     */
    public int[] haalTweeStenenUitSteenzakjeEnGeefAanSpeler() {
        if (!checkOfErNogStenenInHetZakjeZijn()) {
            pasSpeelkansenWinnaarAan(bepaalWinnaar());
            return new int[]{0, 0};
        }
        if (stenen.size() >= 2) {
            Collections.shuffle(stenen);
            Steen eersteSteen = stenen.get(0);
            Steen tweedeSteen = stenen.get(1);
            stenen.remove(1);
            stenen.remove(0);
            Collections.shuffle(stenen);
            return new int[]{eersteSteen.getWaarde(), tweedeSteen.getWaarde()};
        }
        else {
            Steen eersteSteen = stenen.get(0);
            stenen.remove(0);
            return new int[]{eersteSteen.getWaarde()};
        }
    }

    /**
     * UC4: sorteert de stenen in de zak, haalt er drie uit, verwijdert deze uit het zakje en geeft hun waarden terug.
     * Deze methode wordt opgeroepen bij start van spel.
     * @return  int[] met daarin de waarden van de drie stenen op index 0,1 en 2.
     */
    public int[] haalDrieStenenUitSteenzakjeEnGeefAanSpelerBijEersteBeurt() {
        Collections.shuffle(stenen);
        Steen eersteSteen = stenen.get(0);
        Steen tweedeSteen = stenen.get(1);
        Steen derdeSteen = stenen.get(2);
        stenen.remove(2);
        stenen.remove(1);
        stenen.remove(0);
        Collections.shuffle(stenen);
        return new int[]{eersteSteen.getWaarde(), tweedeSteen.getWaarde(), derdeSteen.getWaarde()};
    }


    /**
     * UC4: steekt steen terug in zakje indien speler deze niet kan leggen.
     * @param waardeVanSteen waarde van het steentje dat terug in zak moet
     */

    public void steekSteentjeTerugInZak(int waardeVanSteen) {
        stenen.add(new Steen(waardeVanSteen));
    }


    /**
     * UC3: de lijst van scorebladen wordt overlopen met een stream
     * TESS: graag rest van de methode toelichten
     * @return de winnaar van het spel
     */
    private Speler bepaalWinnaar() {
        return spelers.stream()
                .sorted(Comparator.comparingInt((e)->e.getScoreblad().berekenScoreVanScoreblad()))
                .reduce((first,second) -> second)
                .get();
        //isPresent check irrelevant. Spel gaat nooit starten met minder dan twee spelers.
    }
    
    /**
     * UC3: Controleert of er nog stenen zijn
     * indien niet wordt SpelStaat op gedaan gezet
     * boolean wordt teruggegeven
     * @return boolean die voorstelt of er nog stenen speelbaar zijn
     */
    private boolean checkOfErNogStenenInHetZakjeZijn() {
        if (stenen.isEmpty()) {
            this.spelStaat = SpelStaat.GEDAAN;
            return false;
        }
        return true;

    }
    
    /**
     * UC3: geeft de scorebladen per speler
     * @param speler speler wiens scoreblad terug wordt gegeven
     * @return gevraagde scoreblad
     */
    public Scoreblad geefScoreblad(Speler speler){
        return speler.getScoreblad();
    }

    /**
     * UC3: toont de score per Speler
     * TESS: opnieuw graag toelichting in kader van bovenstaande methode
     * @param speler speler wiens score moet teruggeven worden
     * @return score van speler
     */
    public int toonScore(Speler speler){
        return geefScoreblad(speler).berekenScoreVanScoreblad();
    }
    
    /**
     * UC3: methode om de winnaar weer te geven op einde van Spel. 
     * @return de winnende speler
     */
    public Speler toonWinnaar(){
        return bepaalWinnaar();
    }

    /**
     * UC3/4: wordt aangeroepen wanneer speler spel wil stoppen
     * SpelStaat word ingesteld op gecanceled
     * we kiezen ervoor om de spelers in dit geval hun speelkansen terug te geven.
     */

    public void cancelSpel() {
        this.spelStaat = SpelStaat.GECANCELED;
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() + 1));
    }
}
