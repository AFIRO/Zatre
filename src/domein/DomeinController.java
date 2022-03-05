package domein;

public class DomeinController {
    private final SpelerRepository spelerRepository;


    public DomeinController() {
        this.spelerRepository = new SpelerRepository();
    }

    public void registreer(String gebruikernaam, int geboortejaar) {
        spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

    }

    public void haalSpelerOp(String gebruikernaam, int geboortejaar) {
        spelerRepository.getSpeler(gebruikernaam, geboortejaar);

    }
}