package domein;

import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
    private final SpelerRepository spelerRepository;
    private final Taal taal;

    public DomeinController(Taal taal) {
        this.taal = taal;
        this.spelerRepository = new SpelerRepository();
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);
    }


    public void meldAan(String gebruikersnaam, int geboortejaar) {
        spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
    }

    public String geefSpeler(String gebruikersnaam, int geboortejaar) {
    	Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);
        String naam = speler.getGebruikersnaam();
        int kansen = speler.getSpeelkansen();
        //Codereview Andreeas: Ik zou aanraden om maar 1 keer geefspeler te gebruiken en de gevonden speler op te slaan.
        //Je doet hier nu twee calls naar de databasis voor hetzelfde object.

        return String.format("%s%s%n%s%d%n", geefVertaling("GEBRUIKERSNAAM"), naam,
                geefVertaling("SPEELKANSEN"), kansen);
    }

    public void geefSpelers() {
        List<Speler> spelers = new ArrayList<>(spelerRepository.geefSpelers());
        //Codereview Andreeas: Je vult hier een arraylist met de geefspelers methode uit de repository.
        //Echter, die methode geeft alle spelers terug in de spelers arraylist uit de repo.
        //Deze wordt bij creatie van de repository gevuld met alle spelers in de databasis.
        //Je start dus altijd met de volledige spelerlijst uit de databasis.
        //Het is btw niet nodig om een nieuw object aan te maken met new arraylist. De lijst uit de repo wordt sowieso gekopieerd.
        //Wat je eigenlijk wil doen is een lokale spelers lijst maken in de DC (of elders) en daaraan spelers toevoegen die je opvraagt uit de databasis.
        //via repo.geefSpeler

        int index = 1;
        String output;
        for (Speler speler : spelers) {
        	output = "";
            String naam = speler.getGebruikersnaam();
            int geboortejaar = speler.getGeboortejaar();
            int speelkansen = speler.getSpeelkansen();
            output += String.format("%s%d%n%s%s%n%s%d%n%s%d%n%n", geefVertaling("SPELER"), index,
                    geefVertaling("GEBRUIKERSNAAM"), naam,
                    geefVertaling("GEBOORTEJAAR"), geboortejaar,
                    geefVertaling("SPEELKANSEN"), speelkansen);
            index++;
            System.out.print(output);
        }

        //Codereview Andreeas:
        //For reference, een stream is hier een betere keuze

//		String andereOutput = spelers.stream()
//				.map((speler) -> {String.format("%s%d%n%s%s%n%s%d%n%s%d%n", ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER"), spelers.indexOf(speler),
//						ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
//						ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBOORTEJAAR"), speler.getGeboortejaar(),
//						ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPEELKANSEN"), speler.getSpeelkansen());})
//				.collect(Collectors.joining("\n"));

    }

    public String geefVertaling(String key) {
        return taal.getLocalisatie(key);
    }

}