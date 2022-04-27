package domein;

import persistence.SpelerMapper;
import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
    private final SpelerRepository spelerRepository;
    private Taal taal;
    private Spel spel;

    public DomeinController() {
        this.spelerRepository = new SpelerRepository(new SpelerMapper()); //Andreeas: Dependency injection nodig voor Mockito

    }

    /**
     * Alle UC's: getter voor localisatie
     */

    public Taal getTaal() {
        return taal;
    }

    public void setTaal(Taal taal) {
        this.taal = taal;
    }


    /**
     * UC1: speler registreren
     * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
     *
     * @param gebruikersnaam Gebruikersnaam van speler
     * @param geboortejaar   Geboortejaar van speler
     */

    public void registreer(String gebruikersnaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikersnaam, geboortejaar);
    }

    /**
     * UC2: speler aanmelden
     * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
     *
     * @param gebruikersnaam Gebruikersnaam van speler
     * @param geboortejaar   Geboortejaar van speler
     */

    public void meldAan(String gebruikersnaam, int geboortejaar) {
        spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
    }

    /**
     * UC1  en UC3: String representatie van een specifieke speler
     * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
     *
     * @param gebruikersnaam Gebruikersnaam van speler
     * @param geboortejaar   Geboortejaar van speler
     * @throws IllegalArgumentException indien gebruikersnaam te kort
     */

    public String geefSpeler(String gebruikersnaam, int geboortejaar) { // opmaak string in UC
        Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);

        return String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
                taal.getLocalisatie("GEBOORTEJAAR"), speler.getGeboortejaar(),
                taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
    }

    public String geefAangemeldeSpeler(String gebruikersnaam, int geboortejaar) { // opmaak string in UC
        Speler speler = spelerRepository.geefAangemeldeSpeler(gebruikersnaam, geboortejaar);

        return String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
                taal.getLocalisatie("GEBOORTEJAAR"), speler.getGeboortejaar(),
                taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
    }

    /**
     * UC2 en UC3: String representatie van alle aangemelde spelers een specifieke speler
     * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
     *
     * @throws IllegalArgumentException indien gebruikersnaam te kort
     */
    public List<String> geefSpelers() { // opmaak string in UC
        List<Speler> spelers = spelerRepository.geefSpelers();

        List<String> players = new ArrayList<>();

        String output;
        for (Speler speler : spelers) {
            output = String.format("%s %d: %s%n", taal.getLocalisatie("SPELER"), spelers.indexOf(speler) + 1, speler.getGebruikersnaam());
            output += String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
                    taal.getLocalisatie("GEBOORTEJAAR"), speler.getGeboortejaar(),
                    taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
            players.add(output);
        }
        return players;
    }

    /**
     * UC2: tonen hoeveel spelers er in het spel zitten
     *
     * @return aantalSpelersInSpel
     */
    public int geefAantalSpelersInSpel() {
        return spelerRepository.geefSpelers().size();
    }

    /**
     * UC3: Spel starten.
     * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc) TESS: toch graag extra toelichting
     */
    public void startSpel() {
        this.spel = new Spel(spelerRepository.geefSpelers());
        spel.startSpel();

    }

    private String geefWinnaar() {
        return String.format("%s%n%s", taal.getLocalisatie("WINNAAR"),
                spel.geefWinnaar());
    }

    /**
     * UC4: nog nader te bepalen na ontwerp UC3
     *
     * @param gebruikersnaam gebruikersnaam van de gebruiker
     * @param geboortejaar   geboortejaar van de gebruiker
     * @param zetten         de uit te voeren zetten.
     */
    public void speelBeurt(String gebruikersnaam, String geboortejaar, String[] zetten) {
        spel.speelBeurt(gebruikersnaam, geboortejaar, zetten);
    }

    /**
     * UC4: vraagt aan spel voor twee of drie willekeurige stenen uit het stenenzakje
     *
     * @param eersteBeurt boolean die aangeeft of het gaat om de eerste beurt of niet.
     * @return int[] met daarin de waarden van de twee stenen op index 0 en 1.
     */
    public int[] vraagSteentjes(boolean eersteBeurt) {
        if (eersteBeurt)
            return spel.haalDrieStenenUitSteenzakjeEnGeefAanSpelerBijEersteBeurt();
        else
            return spel.haalTweeStenenUitSteenzakjeEnGeefAanSpeler();
    }

    /**
     * UC4: steekt een steen die niet geplaatst kan worden terug in de zak
     *
     * @param waardeVanSteen waarde van de steen die terug in zak moet.
     */
    public void steekSteentjeTerugInZak(int waardeVanSteen) {
        spel.steekSteentjeTerugInZak(waardeVanSteen);
    }

    /**
     * UC4: genereert het laatste scoreblad waarop naam van de winnaar en diens scores staat.
     * Vervolgens worden de spelers weggeschreven naar de databasis en wordt de repository gewiped voor volgend spel.
     * Deze methode wordt enkel aangeroepen indien de GUI de steentjes array [0,0] krijgt. Het spel is dan gedaan.
     *
     * @return laatste scoreblad
     */
    public List<String> eindigSpel() {
        List<String> laatsteScorebladOmTeTonen = new ArrayList<>();
        laatsteScorebladOmTeTonen.add(geefWinnaar());
        laatsteScorebladOmTeTonen.addAll(spel.geefWinnaar().getScoreblad().getRegels());
        updateSpelersInDatabaseNaSpel();
        resetRepositoryVoorNieuwSpel();
        return laatsteScorebladOmTeTonen;
    }

    /**
     * UC3: laat een speler toe om het spel te cancelen. Alle spelers krijgen hun gebruikte speelkans terug.
     * Vervolgens worden de spelers geupdate in de database en wordt de repository gewiped als voorbereiding op een nieuw spel.
     */
    public void cancelSpel() {
        spel.cancelSpel();
        updateSpelersInDatabaseNaSpel();
        resetRepositoryVoorNieuwSpel();
    }


    /**
     * UC3: schrijft de staat van de spelers na spel terug naar de databasis
     * wordt de repository gewiped als voorbereiding op een nieuw spel.
     */
    private void updateSpelersInDatabaseNaSpel() {
        for (Speler speler : spelerRepository.geefSpelers())
            spelerRepository.updateSpeler(speler);
    }

    /**
     * UC3: wiped de repository gewiped als voorbereiding op een nieuw spel.
     */

    private void resetRepositoryVoorNieuwSpel() {
        spelerRepository.geefSpelers().removeAll(spelerRepository.geefSpelers());
    }

    /**
     * UC3: deze methode geeft een lijst van de scorebladen terug van de spelers
     *
     * @return een lijst aan strings met de string representatie van de scorebladen van elke speler.
     */
    public List<String> geefScorebladen() {
        List<String> alleScorebladen = new ArrayList<>();

        for (Speler speler : spelerRepository.geefSpelers()) {
            String scoreblad = String.format("%s%n", "DT  /  10  /  11  /  12  /  Bonus  /  Totaal") + speler.getScoreblad().toString();
            alleScorebladen.add(scoreblad);
        }
        return alleScorebladen;
    }

    /**
     * UC3: bepaalt wanneer het spel be�indigd is of wordt
     */
    public boolean isEindeSpel() {
        return spel.isEindeSpel();
    }


}
