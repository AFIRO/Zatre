package domein;

import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
    private final SpelerRepository spelerRepository;
    private final Taal taal;
    
    //alle methodes die niet op DCD staan moeten private staan

    public DomeinController(Taal taal) {
        this.taal = taal;
        this.spelerRepository = new SpelerRepository();
     
    }
    
    public Taal getTaal() {
    	return taal;
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);
    }


    public void meldAan(String gebruikersnaam, int geboortejaar) {
        spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
    }

    public String geefSpeler(String gebruikersnaam, int geboortejaar) { //opmaak string in UC
    	Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);
        String naam = speler.getGebruikersnaam();
        int kansen = speler.getSpeelkansen();
        return String.format("%s%s%n%s%d%n", geefVertaling("GEBRUIKERSNAAM"), naam,
                geefVertaling("SPEELKANSEN"), kansen);
    }

    public void geefSpelers() { //opmaak string in UC
        List<Speler> spelers = spelerRepository.geefSpelers();
        String output;

        for (Speler speler : spelers) {
            output = "";
            String naam = speler.getGebruikersnaam();
            int geboortejaar = speler.getGeboortejaar();
            int speelkansen = speler.getSpeelkansen();
            output += String.format("%s %d%n%s%s%n%s%d%n%s%d%n%n", geefVertaling("SPELER"), spelers.indexOf(speler)+1,
                    geefVertaling("GEBRUIKERSNAAM"), naam,
                    geefVertaling("GEBOORTEJAAR"), geboortejaar,
                    geefVertaling("SPEELKANSEN"), speelkansen);

            System.out.print(output);
        }
    }
    //aan te passen - List<Strings> door te geven. To String gebruiken in Speler -> door Lorenz
    
    //mag weggehaald worden
    private String geefVertaling(String key) {
        return taal.getLocalisatie(key);
    }

    //te verwijderen van zodra geefSpelers list doorgeeft
    public int geefAantalSpelersInSpel() {
        return spelerRepository.geefSpelers().size();
    }

}