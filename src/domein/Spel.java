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
     * we gaan de speelkansen verminderen met -1 en er wordt een Scoreblad aangemaakt per Speler. 
     * Gezien Speler scoreblad kent kan ook Spel dit nu aanmaken (NA TE VRAGEN OF DIT STATEMENT CORRECT IS)
     * Vervolgens gaan we met bepaalVolgordeSpelers shuffelen en de SpelStaat op GESTART zetten
     * checkOfSpelNogBezig (om te zetten van naam) controleert of er nog stenen zijn en nadien kijken we of de staat op gedaan of gecanceled staat. 
     * indien ja, wordt het spel afgebroken. 
     * Vervolgens wordt ingeval van einde spel de winnaar geregistreerd, bepaal winnaar wordt als parameter meegegeven omdat die de winnende speler bepaalt met returntype Speler.
     */
    public void startSpel() {
        if (spelers.size() < 2)
            throw new IllegalArgumentException("TE_WEINIG_SPELERS");

        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() - 1));

        bepaalVolgordeSpelers();
        this.spelStaat = SpelStaat.GESTART;

        while (checkStenenOver()) { 
            for (Speler speler : spelers) {
                //speelBeurt(speler);
            	checkStenenOver();
                if (this.spelStaat.equals(SpelStaat.GEDAAN) || this.spelStaat.equals(SpelStaat.GECANCELED))
                    break;
            } //Tess: klopt dit? met breakstatement word tot nu toe enkel de speelbeurtloop gestopt en vervolgens wordt er in geval van gedaan de winnaar opgeroepen.
            //Wat als het spel gecanceld wordt? Moet hiervoor nog een optie toegevoegd worden? 
            if (this.spelStaat.equals(SpelStaat.GEDAAN))
            	pasSpeelkansenWinnaarAan(bepaalWinnaar());
        }
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
     * UC3/4: wordt aangeroepen wanneer speler spel wil stoppen
     * SpelStaat word ingesteld op gecanceled
     * we kiezen ervoor om de spelers in dit geval hun speelkansen terug te geven. 
     */
    private void cancelSpel() {
        this.spelStaat = SpelStaat.GECANCELED;
        spelers.forEach((e) -> e.setSpeelkansen(e.getSpeelkansen() + 1));
        //navragen: het lijkt onlogisch om alle spelers te straffen voor een cancel.
    }

    /**
     * UC4
     * @param gebruikersnaam gebruikernaam van speler die beurt speelt
     * @param geboortejaar geboortejaar van speler die beurt speelt
     * @param vak vak waarop speler een tegel zet
     * @param steen steen die speler op vak zet
     */
    public void speelBeurt(String gebruikersnaam, String geboortejaar, String vak, int steen) {
        //todo
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
    private boolean checkStenenOver() {
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
     * @return de naam van de winnaar als string
     */
    public String toonWinnaar(){
        return bepaalWinnaar().getGebruikersnaam();
    }

}
