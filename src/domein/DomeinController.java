package domein;

public class DomeinController {
    private final SpelerRepository spelerRepository;


    public DomeinController() {
        this.spelerRepository = new SpelerRepository();
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

    }

  /*  public Speler haalSpelerOp(String gebruikernaam, int geboortejaar) {
       return spelerRepository.getSpeler(gebruikernaam, geboortejaar);
    } */

    public void meldAan(String gebruikersnaam, int geboortejaar) {
    	spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
    } 
    
    public String geefSpeler(String gebruikersnaam, int geboortejaar) {
    	String naam = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getGebruikersnaam();
    	int jaar = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getGeboortejaar();
    	
    	return String.format("Gebruikersnaam: %s%nGeboortejaar:%D", naam, jaar);
    }
    
}