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
        return String.format("%s%s%n%s%d%n", geefVertaling("GEBRUIKERSNAAM"), naam,
                geefVertaling("SPEELKANSEN"), kansen);
    }

    public void geefSpelers() {
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

    public String geefVertaling(String key) {
        return taal.getLocalisatie(key);
    }

    public int geefAantalSpelersInSpel() {
        return spelerRepository.geefSpelers().size();
    }

}