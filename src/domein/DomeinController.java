package domein;

public class DomeinController {
    private final SpelerRepository spelerRepository;


    public DomeinController(SpelerRepository spelerRepository) {
        this.spelerRepository = spelerRepository;
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

    }
}