package domein;

public class DomeinController {
    private final SpelerRepository spelerRepository;


    public DomeinController() {
        this.spelerRepository = new SpelerRepository();
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        Speler nieuweSpeler = new Speler(gebruikernaam, geboortejaar);
        spelerRepository.voegSpelerToe(nieuweSpeler);

    }
    //Andreeas commit gelukt
    //Lorenz commit gelukt
    //Tess commit gelukt
    //Scarlett commit gelukt
}