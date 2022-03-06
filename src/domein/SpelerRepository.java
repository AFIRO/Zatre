package domein;

import persistence.SpelerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SpelerRepository {
    private final List<Speler> spelers;
    private final SpelerMapper spelerMapper;

    public SpelerRepository() {
        spelerMapper = new SpelerMapper();
        spelers = spelerMapper.geefSpelers();
    }

    public void voegSpelerToe(String gebruikernaam, int geboortejaar) {
        Speler speler = new Speler(gebruikernaam, geboortejaar);
        controleerSpelerUniek(speler);
        spelerMapper.voegSpelerToe(speler);
        spelers.add(spelerMapper.geefSpeler(gebruikernaam,geboortejaar));
    }

    private void controleerSpelerUniek(Speler speler) {
        List<String> uniqueKeys = spelers.stream()
                .map((element) -> element.getGebruikersnaam() + element.getGeboortejaar())
                .filter((e) -> e.equals(speler.getGebruikersnaam() + speler.getGeboortejaar()))
                .collect(Collectors.toList());

        if (!uniqueKeys.isEmpty())
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBRUIKER_BESTAAT_AL"));
    }

    public void vraagSpelerOp(String gebruikersnaam, int geboortejaar) {
    	int index = 1;
    	if(index<=4)
    	{	
    		if (spelerMapper.geefSpeler(gebruikersnaam, geboortejaar).getSpeelkansen() >0)
    		{
    		 spelers.add(spelerMapper.geefSpeler(gebruikersnaam,geboortejaar));
    		 index++;}
    		 else
    			 throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEEN_SPEELKANSEN_MEER")); 
    	}
    	else
    		throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("MAX_AANTAL_SPELERS_BEREIKT"));

        //Codereview Andreeas: Twee opmerkingen:
        //1. Je hebt gebruikt hier de algemene lijst van spelers die gebruikt wordt om te checken of een speler niet dubbel is in de databasis.
        //Ik zou hiervoor een andere lijst gebruiken OF de methode methode controleerSpelerUniek aanpassen zodat deze rechtstreeks in de databases krijkt via de mapper of een speler al bestaat.
        //2. Die index gaat hier nooit werken omdat deze verloren gaat na het doorlopen van de methode
        //ALs je wilt dat je lijst nooit meer dan 4 man bevat, gebruik dan list.size als controle.
    }
    
    public List<Speler> geefSpelers(){
    	return spelers;
    }
    
    public Speler geefSpeler(String gebruikersnaam, int geboortejaar) {
    	Speler gekozenSpeler = new Speler(gebruikersnaam, geboortejaar);
    	if (!(spelers.contains(gekozenSpeler))) {
    		throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER_BESTAAT_NIET"));
    	}
    		else
    		return spelers.get(spelers.indexOf(gekozenSpeler));
    		
    }
    		

    public void updateSpeler(Speler speler){
        if (spelers.contains(speler)) {
            spelers.get(spelers.indexOf(speler)).setSpeelkansen(speler.getSpeelkansen());
            spelerMapper.updateSpeler(speler);
        }
        else
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER_BESTAAT_NIET"));
    }



}



