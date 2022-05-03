package domein;

import java.util.*;

public class Spel {
    private final List<Speler> spelers;
    private final Spelbord spelbord;
    private final List<Steen> stenen;
    private SpelStaat spelStaat;
    private int indexVanSpelerAanBeurt;
    private Speler huidigeActieveSpeler;
    /**
     * UC3: constructor van Spel lijst van spelers wordt hieraan doorgegeven. In de
     * domeincontroller wordt hiervoor de lijst van aangemelde spelers uit de
     * repository gehaald. De spelstaat wordt op IN_VOORBEREIDING ingesteld een
     * nieuw spelbord wordt gegenereerd de stenen worden gegenereerd door middel van
     * methode in deze klasse. er wordt een arraylist ingesteld voor de scorebladen.
     *
     * @param spelers de lijst spelers die het spel gaan spelen.
     */
    public Spel(List<Speler> spelers) {
        this.spelers = spelers;
        this.spelStaat = SpelStaat.IN_VOORBEREIDING;
        this.spelbord = new Spelbord();
        this.stenen = genereerStenen();
        indexVanSpelerAanBeurt = 0;
    }

    /**
     * UC3: methode genereerSteen lijst van stenen wordt aangemaakt: eerst 20 stenen
     * van elk (1-6), vervolgens nog een extra steen Dit maakt een lijst van 121
     * stenen. Vervolgens worden deze door elkaar gegooid. De methode geeft de
     * geshuffelde lijst stenen terug.
     *
     * @return de lijst gegenereerde stenen
     */
    private List<Steen> genereerStenen() {
        ArrayList<Steen> stenen = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            stenen.add(new Steen(1));
            stenen.add(new Steen(2));
            stenen.add(new Steen(3));
            stenen.add(new Steen(4));
            stenen.add(new Steen(5));
            stenen.add(new Steen(6));
        }

        stenen.add(new Steen(1));
        Collections.shuffle(stenen);
        return stenen;
    }

    /**
     * UC3: startSpel methode in de domeincontroller wordt eerst een constructor van
     * Spel aangeroepen en vervolgens volgende methode toegepast. we gaan kijken of
     * de lijst minimum 2 spelers bevat (het maximum wordt reeds getest in de
     * repository) we gaan de speelkansen verminderen met -1 Gezien Speler scoreblad
     * kent kan ook Spel daar nu ook aan. Vervolgens gaan we met
     * bepaalVolgordeSpelers shuffelen en de SpelStaat op GESTART zetten
     */
    public void startSpel() {
        checkOfVoldoendeSpelersOmSpelTeStarten();
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() - 1));
        bepaalVolgordeSpelers();
        huidigeActieveSpeler = spelers.get(0);
        this.spelStaat = SpelStaat.GESTART;
    }

    /**
     * UC3: Checkt of er voldoende spelers in spel zitten
     *
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
     * UC4: gaat volgorde van spelers af op basis van index en past de huidige
     * speler aan
     */

    public void volgendeSpeler() {
        if (indexVanSpelerAanBeurt == spelers.size() - 1) {
            indexVanSpelerAanBeurt = 0;
        } else {
            indexVanSpelerAanBeurt++;
        }
        huidigeActieveSpeler = spelers.get(indexVanSpelerAanBeurt);
    }

    /**
     * UC4: geeft actieve speler terug
     */

    public Speler getHuidigeActieveSpeler() {
        return huidigeActieveSpeler;
    }

    /**
     * UC3: speelkansen van winnende speler worden +2 toegevoegd
     *
     * @param speler de winnaar van het spel
     */
    private void pasSpeelkansenWinnaarAan(Speler speler) {
        speler.setSpeelkansen(speler.getSpeelkansen() + 2);
    }

    /**
     * UC4: regelt de logica van een beurt. De string[] zetten bevat 2 of 3 strings
     * met de zet in de vorm "kolomVak.rijVak waardeSteen". Deze worden
     * geïnterpreteerd en omgezet naar de correcte info voor punt berekening. We gaan
     * ervan uit dat de info correct moet zijn dankzij validatie in de GUI qua
     * zetten Methode genereert een arraylist aan strings. Deze strings bevatten de
     * scores per zet. Op index 0, 1 en 2 komen respectievelijk de scores van beurt
     * 1, 2 en 3. Deze strings bevatten de volgende structuur: 0/1 (dubbele score of
     * niet voor die zet) gevolgd door de horizontale en verticale score van de
     * tegel in een komma seperated formaat (.csv) voor snelle parsing.
     *
     * @param gebruikersnaam gebruikersnaam van speler die beurt speelt
     * @param geboortejaar   geboortejaar van speler die beurt speelt
     * @param zetten         bevat 2 of 3 strings met de zet in de vorm
     *                       "kolomVak.rijVak waardeSteen"
     * @return een lijst aan Strings die het scoreblad na het spelen van de beurt
     * moeten voorstellen
     */
    public List<String> speelBeurt(String gebruikersnaam, String geboortejaar, List<String> zetten) {
        checkOfSpelerInHetSpelZit(gebruikersnaam, geboortejaar);
        Speler actieveSpeler = spelers.get(spelers.indexOf(new Speler(gebruikersnaam, Integer.parseInt(geboortejaar))));
        ArrayList<String> puntenArraysVoorAlleZetten = new ArrayList<>();
        // itereer door alle doorgegeven zetten
        for (String zet : zetten) {
            // haal het correct vakje uit het spelbord op basis van eerste 3 tekens in
            // string zijde kolomVak.rijVak.
            Vak vakWaaropSteenWerdGelegd = spelbord.getVakjes().get(zet.substring(2));
            // leg het correct steentje op het doorgegeven vak op basis van 5de teken. 4de
            // teken is een spatie.
            vakWaaropSteenWerdGelegd.setSteen(new Steen(Integer.parseInt(zet.substring(0, 1))));
            // update punten array met de berekening van de zet. Dit zal twee of drie keer
            // updaten afhankelijk van aantal zetten.
            puntenArraysVoorAlleZetten.add(berekenScoreVoorGelegdeSteen(vakWaaropSteenWerdGelegd));
        }
        // genereer scorebladRegels op basis van de string per zet.
        actieveSpeler.getScoreblad().voegRegelsToeAanScoreblad(puntenArraysVoorAlleZetten);

        // geef een String representatie van de geupdatet scoreblad voor de GUI.
        return actieveSpeler.getScoreblad().getRegels();

    }

    /**
     * UC4: roept hulpfuncties op om een string representatie te maken van de score
     * van de zet
     *
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     * @return punten string representatie van score eerste getal 0/1 is afhankelijk
     * van of het een dubbele score is, gevolgd door de horizontale en
     * verticale score.
     */

    private String berekenScoreVoorGelegdeSteen(Vak vakWaaropSteenWerdGelegd) {
        String punten = "";
        // bereken horizontale punten
        punten = berekenScoreHorizontaal(vakWaaropSteenWerdGelegd, punten);
        // bereken verticale punten
        punten = berekenScoreVerticaal(vakWaaropSteenWerdGelegd, punten);

        return punten;
    }

    /**
     * UC4: roept hulpfuncties om links en rechts te kijken op het bord voor de
     * horizontale score te berekenen. Het past de doorgegeven String aan met de
     * dubbele punten modifier en het gevonden horizontaal punt.
     *
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     * @param punten                   de aan te passen string representatie
     * @return punten string representatie van score eerste getal 0/1 is afhankelijk
     * van of het een dubbele score is, gevolgd door de horizontale.
     */

    private String berekenScoreHorizontaal(Vak vakWaaropSteenWerdGelegd, String punten) {
        int score = vakWaaropSteenWerdGelegd.getSteen().getWaarde();
        // kijk links
        score += telSteentjesInSpecifiekeRichtingOp(vakWaaropSteenWerdGelegd, "links");
        // kijk rechts
        score += telSteentjesInSpecifiekeRichtingOp(vakWaaropSteenWerdGelegd, "rechts");
        // indien 10+ en startvakje wit, zet dubbele score boolean als 1
        if (score >= 10 && vakWaaropSteenWerdGelegd.getKleur().equals(Vak.Kleur.WIT)) {
            punten = punten.concat("1,");
        } else {
            punten = punten.concat("0,");
        }

        return punten.concat(score + ",");
    }

    /**
     * UC4: roept hulpfuncties om boven en onder te kijken op het bord voor de
     * verticale score te berekenen. het past de doorgegeven String aan met de
     * dubbele punten modifier en het gevonden verticale punt.
     *
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     * @param punten                   de aan te passen string representatie
     * @return punten string representatie van score eerste getal 0/1 is afhankelijk
     * van of het een dubbele score is, gevolgd door de horizontale en
     * verticale score
     */

    private String berekenScoreVerticaal(Vak vakWaaropSteenWerdGelegd, String punten) {
        int score = vakWaaropSteenWerdGelegd.getSteen().getWaarde();
        // kijk boven
        score += telSteentjesInSpecifiekeRichtingOp(vakWaaropSteenWerdGelegd, "boven");
        // kijk onder
        score += telSteentjesInSpecifiekeRichtingOp(vakWaaropSteenWerdGelegd, "onder");
        // indien 10+ en startvakje wit en dubbele score boolean nog niet 1, zet dubbele
        // score boolean als 1
        if (score >= 10 && vakWaaropSteenWerdGelegd.getKleur().equals(Vak.Kleur.WIT) && punten.charAt(0) == '0') {
            punten = punten.replace('0', '1');
        }
        return punten.concat(String.valueOf(score));
    }

    /**
     * UC4: genereert een map met de coördinaten van de naburige vakken en loopt
     * deze af in een specifieke richting als een linked list. Deze telt alle
     * gevonden stenen op tot het oftewel botst tegen rand van het spelbord of een
     * vak ontdekt zonder steen erop.
     *
     * @param vakWaaropSteenWerdGelegd het vak waarop een steen wordt gelegd.
     * @param richting                 waarin het algoritme moet zoeken.
     * @return de berekende score voor die richting.
     */

    private int telSteentjesInSpecifiekeRichtingOp(Vak vakWaaropSteenWerdGelegd, String richting) {
        // genereer map
        Map<String, String> naburigeVakken = vakWaaropSteenWerdGelegd.geefVakjesNaastVak();
        int scoreOmTerugTeGeven = 0;
        // zolang als in die richting een steen bestaat
        while (!naburigeVakken.get(richting).equals("bestaat niet")) {
            // haal die steen op uit het bord
            Vak teCheckenVak = spelbord.getVakjes().get(naburigeVakken.get(richting));
            // indien er een steentje op staat, voeg deze toe aan de score.
            if (Objects.nonNull(teCheckenVak.getSteen())) {
                scoreOmTerugTeGeven += teCheckenVak.getSteen().getWaarde();
                naburigeVakken = teCheckenVak.geefVakjesNaastVak();
            }
            // indien geen steentje erop staat, stopt de loop
            else
                break;
        }
        return scoreOmTerugTeGeven;
    }

    /**
     * UC4: Checkt of het speler in het spel zit of niet.
     *
     * @throws IllegalArgumentException indien speler niet in spel.
     */

    private void checkOfSpelerInHetSpelZit(String gebruikersnaam, String geboortejaar) {
        if (!spelers.contains(new Speler(gebruikersnaam, Integer.parseInt(geboortejaar)))) {
            throw new IllegalArgumentException("SPELER_ZIT_NIET_IN_SPEL");
        }
    }

    /**
     * UC4: sorteert de stenen in de zak, haalt er twee uit, verwijdert deze uit het
     * zakje en geeft hun waarden terug.
     *
     * @return int[] met daarin de waarden van de twee stenen op index 0 en 1.
     * Indien stenen op zijn, geeft het 0,0 terug. Dit is het teken om het
     * spel te stoppen.
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
        } else {
            Steen eersteSteen = stenen.get(0);
            stenen.remove(0);
            return new int[]{eersteSteen.getWaarde()};
        }
    }

    /**
     * UC4: sorteert de stenen in de zak, haalt er drie uit, verwijdert deze uit het
     * zakje en geeft hun waarden terug. Deze methode wordt opgeroepen bij start van
     * spel.
     *
     * @return int[] met daarin de waarden van de drie stenen op index 0,1 en 2.
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
     *
     * @param waardeVanSteen waarde van het steentje dat terug in zak moet
     */

    public void steekSteentjeTerugInZak(int waardeVanSteen) {
        stenen.add(new Steen(waardeVanSteen));
    }

    /**
     * UC3: de lijst van scorebladen wordt overlopen met een stream TESS: graag rest
     * van de methode toelichten
     *
     * @return de winnaar van het spel
     */
    private Speler bepaalWinnaar() {
        Speler winnaar = spelers.stream()
                .sorted(Comparator.comparing(Speler::getScoreBinnenHuidigSpel))
                .reduce((first, second) -> second).get();
        // isPresent check irrelevant. Spel gaat nooit starten met minder dan twee
        // spelers.
        pasSpeelkansenWinnaarAan(winnaar);
        return winnaar;
    }

    /**
     * UC3: Controleert of er nog stenen zijn indien niet wordt SpelStaat op gedaan
     * gezet boolean wordt teruggegeven
     *
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
     *
     * @param speler speler wiens scoreblad terug wordt gegeven
     * @return gevraagde scoreblad
     */
    public Scoreblad geefScoreblad(Speler speler) {
        return speler.getScoreblad();
    }

    /**
     * UC3: toont de score per Speler 
     *
     * @param speler speler wiens score moet teruggeven worden
     * @return score van speler
     */
    public int toonScore(Speler speler) {
        return geefScoreblad(speler).berekenScoreVanScoreblad();
    }

    /**
     * UC3: methode om de winnaar weer te geven op einde van Spel.
     *
     * @return de winnende speler
     */
    public Speler geefWinnaar() {
        return bepaalWinnaar();
    }

    /**
     * UC3/4: wordt aangeroepen wanneer speler spel wil stoppen SpelStaat word
     * ingesteld op gecanceled we kiezen ervoor om de spelers in dit geval hun
     * speelkansen terug te geven.
     */

    public void cancelSpel() {
        this.spelStaat = SpelStaat.GECANCELED;
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() + 1));
    }

    /**
     * UC3: bepaalt wanneer het spel beï¿½indigd is of wordt
     */

    public boolean isEindeSpel() {
        return !checkOfErNogStenenInHetZakjeZijn();
    }

    /**
     * UC4: tussentijdse evaluatie op legaliteit van zet
     *
     * @param vak het vakje waar steen op komt
     * @return of de zet op eerste zicht zou mogen
     */

    public boolean checkOfZetLegaalIsTussenTijdseValidatie(boolean eersteSteen, String vak, String steen) {
        //indien het de eerste steen is en vak is 8.8 = OK
        if (eersteSteen && vak.equals("8.8")) {
            zetSteenOpVak(vak, steen);
            return true;
        }
        //indien niet eerste steen en steen op leeg vakje = OK
        if (!eersteSteen && !spelbord.getVakjes().get(vak).bevatSteen()) {
            zetSteenOpVak(vak, steen);
            return true;
        }
        return false;
    }

    /**
     * UC4: eind evaluatie op legaliteit van zet op basis van een simulatie. Na
     * methode wordt bord staat gereset.
     *
     * @param zetten de geplande zetten in String formaat
     * @return of de zet legaal is
     */

    public boolean checkOfZettenLegaalZijnEindValidatie(List<String> zetten) {
        //lijsten voor vakken en stenen voor validatie
        List<Vak> vakkenPerZet = new ArrayList<>();
        List<Steen> stenenPerZet = new ArrayList<>();
        //vul deze lijsten met info uit de doorgegeven zetten
        for (String zet : zetten) {
            haalVakkenEnStenenUitDeZet(vakkenPerZet, stenenPerZet, zet);
        }
        // eerste beurt
        if (zetten.size() == 3) {
            // case aangrenzende stenen binnen eerste beurt
            return checkOfDeDrieVakkenVanDeEersteZetElkaarBegrenzen(vakkenPerZet);
        }
        // alle andere beurten
        else {
            // nodig om steen aan vak lijst te koppelen
            int steenCounter = 0;
            //check per vak
            for (Vak vak : vakkenPerZet) {
                //zet steen op vak voor simulatie
                vak.setSteen(stenenPerZet.get(steenCounter));
                //check of een steen altijd een andere steen niet raakt
                if (!raaktVakMinstensEenAndereSteen(vak)) {
                    //haal alle stenen uit domein bord want zet ongeldig
                    verwijderStenenVanVakkenNaValidatie(vakkenPerZet);
                    //boolean false want zet illegaal
                    return false;
                }
                // check de legaliteit rond score en ligging
                if (checkOfDeGevormdePuntenStrokenMetDeLiggingVanHetSteentje(vak)) {
                    //haal alle stenen uit domein bord want zet ongeldig
                    verwijderStenenVanVakkenNaValidatie(vakkenPerZet);
                    //boolean false want zet illegaal
                    return false;
                }
                //haal steen van vak want einde simulatie
                vak.setSteen(null);
                steenCounter++;
            }
        }
        //haal alle stenen uit domein bord want einde simulatie (voor de zekerheid)
        verwijderStenenVanVakkenNaValidatie(vakkenPerZet);
        //zet is geldig
        return true;
    }

    /**
     * UC4: controleer of de zet of dit legaal is qua score en ligging
     *
     * @param vak het vak dat onderzocht gaat worden
     * @return of de zet legaal is
     */

    private boolean checkOfDeGevormdePuntenStrokenMetDeLiggingVanHetSteentje(Vak vak) {
        //Gebruik de echte methodes voor scoreberekening om de score te berekenen
        List<Integer> puntenLijst = Arrays
                .stream(berekenScoreVerticaal(vak, berekenScoreHorizontaal(vak, "")).split(",")).map(Integer::parseInt)
                .toList();
        //ga alle scores af
        for (Integer punt : puntenLijst) {
            // case gevormde score is hoger dan 12
            if (punt > 12) {
                //zet illegaal
                return true;
            }
            // case vakje wit maar gevormde score < 10;
            if (checkOfSteenOpWitVakLigtEnScoreHeeftOnder10(vak, punt)) {
                //zet illegaal
                return true;
            }
        }
        //zet legaal
        return false;
    }

    /**
     * UC4: controleer of de zet op een witte steen mag liggen op basis van gevormde
     * score
     *
     * @param vak  het vak dat onderzocht gaat worden
     * @param punt de score
     * @return of de zet legaal is
     */

    private boolean checkOfSteenOpWitVakLigtEnScoreHeeftOnder10(Vak vak, Integer punt) {
        // de 1 of 0 is de dubbele score multiplier en moet genegeerd worden.
        return vak.getKleur().equals(Vak.Kleur.WIT) && punt > 1 && punt < 10;
    }

    /**
     * UC4: controleer of voor de eerste zet de drie zetten aangrenzen
     *
     * @param vakkenPerZet de vakken die worden onderzocht
     * @return of de zet legaal is
     */

    private boolean checkOfDeDrieVakkenVanDeEersteZetElkaarBegrenzen(List<Vak> vakkenPerZet) {
        boolean steenEenEnTweeAangrenzend = vakkenPerZet.get(0).geefVakjesNaastVak()
                .containsValue(vakkenPerZet.get(1).getCoordinatenVanVak())
                || vakkenPerZet.get(0).geefVakjesNaastVak().containsValue(vakkenPerZet.get(1).getCoordinatenVanVak());
        boolean steenTweeEnDrieAangrenzend = vakkenPerZet.get(1).geefVakjesNaastVak()
                .containsValue(vakkenPerZet.get(2).getCoordinatenVanVak())
                || vakkenPerZet.get(1).geefVakjesNaastVak().containsValue(vakkenPerZet.get(0).getCoordinatenVanVak());
        return steenEenEnTweeAangrenzend && steenTweeEnDrieAangrenzend;
    }

    /**
     * UC4: parset de zetten om zo bruikbare objecten te maken voor onderzoek
     *
     * @param vakkenPerZet de lijst die de vakken zal bevatten na parsing
     * @param stenenPerZet de lijst die de stenen zal bevatten na parsing
     * @param zet          de te parsen zet
     */

    private void haalVakkenEnStenenUitDeZet(List<Vak> vakkenPerZet, List<Steen> stenenPerZet, String zet) {
        Vak vakWaaropSteenWerdGelegd = spelbord.getVakjes().get(zet.substring(2));
        stenenPerZet.add(new Steen(Integer.parseInt(zet.substring(0, 1))));
        vakkenPerZet.add(vakWaaropSteenWerdGelegd);
    }

    /**
     * UC4: kijkt of het gespeelde vak minstens een andere steen raakt
     *
     * @param vak de lijst die de vakken zal bevatten na parsing
     * @return of deze een steen raakt
     */

    private boolean raaktVakMinstensEenAndereSteen(Vak vak) {
        return vak.geefVakjesNaastVak().values().stream().filter(coordinaat -> !coordinaat.equals("bestaat niet"))
                .map((coordinaat) -> spelbord.getVakjes().get(coordinaat)).map(Vak::bevatSteen).toList().contains(true);
    }

    /**
     * UC4: reset methode voor staat na validatie
     *
     * @param vakkenPerZet de vakken die gereset moeten worden
     */

    private void verwijderStenenVanVakkenNaValidatie(List<Vak> vakkenPerZet) {
        for (Vak vak : vakkenPerZet) {
            vak.setSteen(null);
        }
    }

    /**
     * UC4: zet een steen op domein bord voor verder onderzoek in legaliteit
     *
     * @param vak   gespeeld vak
     * @param steen gespeelde steen
     */

    private void zetSteenOpVak(String vak, String steen) {
        spelbord.getVakjes().get(vak).setSteen(new Steen(Integer.parseInt(steen)));
    }

    /**
     * UC4: getter voor spelers in spel
     *
     * @return lijst met speler objecten
     */

    public List<Speler> getSpelers() {
        return spelers;
    }

    private enum SpelStaat {
        IN_VOORBEREIDING, GESTART, GEDAAN, GECANCELED
    }
}
