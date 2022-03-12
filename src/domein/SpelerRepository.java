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
    
    /**
     * UC1: een instantie van speler creëren en doorgeven aan de Mapper om zo in de database te plaatsen
     * @param gebruikernaam
     * @param geboortejaar
     */

    public void voegSpelerToe(String gebruikernaam, int geboortejaar) {
        Speler speler = new Speler(gebruikernaam, geboortejaar);
        spelerMapper.voegSpelerToe(speler);
    }
    
    /**
     * UC2: vraagt de Speler op via de Mapper-klasse en voegt deze toe aan lijst Spelers
     * controleert hierbij of er nog speelkansen zijn, of deze speler al in de lijst zit en of er nog niet teveel spelers zijn
     * @param gebruikersnaam
     * @param geboortejaar
     */

    public void vraagSpelerOp(String gebruikersnaam, int geboortejaar) {
        Speler opgevraagdeSpeler = spelerMapper.geefSpeler(gebruikersnaam,geboortejaar);

        if (opgevraagdeSpeler.getSpeelkansen() < 0)
            throw new IllegalArgumentException("GEEN_SPEELKANSEN_MEER");

        if (spelers.contains(opgevraagdeSpeler))
            throw new IllegalArgumentException("SPELER_AL_AANGEMELD");

        if (spelers.size() >= 4)
            throw new IllegalArgumentException("MAX_AANTAL_SPELERS_BEREIKT");

        spelers.add(opgevraagdeSpeler);
    }
    
    /**
     * UC2: geeft de lijst van spelers terug
     * @return
     */

    public List<Speler> geefSpelers() {
        return spelers;
    }

    /**
     * UC2: controleert of gekozenSpeler toegevoegd werd aan lijst spelers en geeft speler terug
     * @param gebruikersnaam
     * @param geboortejaar
     * @return
     */
    
    public Speler geefSpeler(String gebruikersnaam, int geboortejaar) {
        Speler gekozenSpeler = new Speler(gebruikersnaam, geboortejaar);
        if (!(spelers.contains(gekozenSpeler))) {
            throw new IllegalArgumentException("SPELER_BESTAAT_NIET");  //exception wordt ook gesmeten wanneer speler geregistreerd wordt
        } else
            return spelers.get(spelers.indexOf(gekozenSpeler));
    }
    
    /**
     * UC2: geeft de nieuwe speelkansen door aan de mapper om aan te passen in de database
     * @param speler
     */

    public void updateSpeler(Speler speler) {
        if (spelers.contains(speler)) 
        {
            spelers.get(spelers.indexOf(speler)).setSpeelkansen(speler.getSpeelkansen());
            spelerMapper.updateSpeler(speler);
        } else
            throw new IllegalArgumentException(("SPELER_BESTAAT_NIET"));
    }
}



