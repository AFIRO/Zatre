package domein;

import exceptions.ExceptionTextDatabase;
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
        spelers = new ArrayList<>();
        spelerMapper = new SpelerMapper();
    }

    public void voegSpelerToe(String gebruikernaam, int geboortejaar) {
        Speler speler = new Speler(gebruikernaam,geboortejaar);
        controleerSpelerUniek(speler);
        spelers.add(speler);
        spelerMapper.voegSpelerToe(speler);// stuurt spelerobject door naar Spelermapper
    }

    private void controleerSpelerUniek(Speler speler) {
        List<String> uniqueKeys = spelers.stream() //verander arraylist in stream
                .map((element) -> element.getGebruikersnaam() + element.getGeboortejaar()) //map speler naar strings
                .filter((e) -> e.equals(speler.getGebruikersnaam() + speler.getGeboortejaar())) //filter alle spelers die niet voldoen aan keys
                .collect(Collectors.toList()); //geef lijst terug met objecten

        if (!uniqueKeys.isEmpty())
            throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBRUIKER_BESTAAT_AL"));
    }

}



