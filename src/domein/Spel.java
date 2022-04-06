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
     * UC4
     * @param gebruikersnaam gebruikernaam van speler die beurt speelt
     * @param geboortejaar geboortejaar van speler die beurt speelt
     * @param vak vak waarop speler een tegel zet
     * @param steen steen die speler op vak zet
     * @return een lijst aan Strings die het scoreblad na het spelen van de beurt moeten voorstellen
     */
    public List<String> speelBeurt(String gebruikersnaam, String geboortejaar, String vak, int steen) {
        checkOfSpelerInHetSpelZit(gebruikersnaam, geboortejaar);
        Speler actieveSpeler = spelers.get(spelers.indexOf(new Speler(gebruikersnaam,Integer.parseInt(geboortejaar))));
        Vak vakWaaropSteenWerdGelegd = spelbord.getVakjes().get(vak);
        CheckOfVakjeLeegIs(vakWaaropSteenWerdGelegd);
        vakWaaropSteenWerdGelegd.setSteen(new Steen(steen));
        berekenScoreVoorGelegdeSteen(actieveSpeler, vakWaaropSteenWerdGelegd);
        return actieveSpeler.getScoreblad().getRegels();
    }

    /**
     * UC4: vraagt de naburige stenen van het vak, controleert of daar een steen op ligt en indien wel,
     * berekent de totale score van de twee vakken. Indien 10, 11 of 12, wordt dit bijgehouden.
     * Dit wordt dan doorgegeven aan het scoreblad voor berekening en opslag
     * @param actieveSpeler speler die de beurt speelt
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     */

    private void berekenScoreVoorGelegdeSteen(Speler actieveSpeler, Vak vakWaaropSteenWerdGelegd) {
        Map<String,String> naburigeStenen = vakWaaropSteenWerdGelegd.geefVakjesNaastVak();
        boolean tienPunten = false;
        boolean elfPunten = false;
        boolean twaalfPunten = false;
        for (String steenCoordinaat: naburigeStenen.values()) {
            if (!steenCoordinaat.equals("bestaat niet")) {
                Vak teCheckenVak = spelbord.getVakjes().get(steenCoordinaat);
                if (Objects.nonNull(teCheckenVak.getSteen())) {
                    int score = vakWaaropSteenWerdGelegd.getSteen().getWaarde() + teCheckenVak.getSteen().getWaarde();
                    switch (score) {
                        case 10: tienPunten = true;
                        case 11: elfPunten = true;
                        case 12: twaalfPunten = true;
                    }
                }
            }
        }

        actieveSpeler.getScoreblad().voegRegelToeAanScoreblad(
                vakWaaropSteenWerdGelegd.getKleur().equals(Vak.Kleur.WIT),
                tienPunten,
                elfPunten,
                twaalfPunten);

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
     * @return  int[] met daarin de waarden van de twee stenen op index 0 en 1.
     */
    public int[] haalSteenUitSteenzakjeEnGeefAanSpeler() {
        if (checkOfErNogStenenInHetZakjeZijn()) {

            Collections.shuffle(stenen);
            Steen eersteSteen = stenen.get(0);
            Steen tweedeSteen = stenen.get(1);
            stenen.remove(1);
            stenen.remove(0);

            return new int[]{eersteSteen.getWaarde(), tweedeSteen.getWaarde()};
        }

        else {
            pasSpeelkansenWinnaarAan(bepaalWinnaar());
            return new int[]{0,0};
        }

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
