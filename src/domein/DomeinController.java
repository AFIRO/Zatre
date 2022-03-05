package domein;

public class DomeinController {
    private final SpelerRepository spelerRepository;


    public DomeinController() {
        this.spelerRepository = new SpelerRepository();
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

    }

    public Speler haalSpelerOp(String gebruikernaam, int geboortejaar) {
       return spelerRepository.getSpeler(gebruikernaam, geboortejaar);
    }
}