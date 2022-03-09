package domein;

import persistence.SpelerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SpelerRepository {
    private final List<Speler> spelers;
    private final SpelerMapper spelerMapper;

    public SpelerRepository() {
        spelerMapper = new SpelerMapper();
        spelers = new ArrayList<>();
    }

    public void voegSpelerToe(String gebruikernaam, int geboortejaar) {
        Speler speler = new Speler(gebruikernaam, geboortejaar);
        spelerMapper.voegSpelerToe(speler);
    }


    public void vraagSpelerOp(String gebruikersnaam, int geboortejaar) {
        int index = 1;
        if (index <= 4) {
            if (spelerMapper.geefSpeler(gebruikersnaam, geboortejaar).getSpeelkansen() > 0) {
                spelers.add(spelerMapper.geefSpeler(gebruikersnaam, geboortejaar));
                index++;
            } else
                throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEEN_SPEELKANSEN_MEER"));
        } else
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("MAX_AANTAL_SPELERS_BEREIKT"));

    }

    public List<Speler> geefSpelers() {
        return spelers;
    }

    public Speler geefSpeler(String gebruikersnaam, int geboortejaar) {
        Speler gekozenSpeler = new Speler(gebruikersnaam, geboortejaar);
        if (!(spelers.contains(gekozenSpeler))) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER_BESTAAT_NIET"));
        } else
            return spelers.get(spelers.indexOf(gekozenSpeler));

    }


    public void updateSpeler(Speler speler) {
        if (spelers.contains(speler)) {
            spelers.get(spelers.indexOf(speler)).setSpeelkansen(speler.getSpeelkansen());
            spelerMapper.updateSpeler(speler);
        } else
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER_BESTAAT_NIET"));
    }


}



