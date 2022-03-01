package domein;

import exceptions.ExceptionTextDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpelerRepository {
    private final List<Speler> spelers;

    public SpelerRepository() {
        spelers = new ArrayList<>();
    }

    public void voegSpelerToe(String gebruikernaam, int geboortejaar) {
        Speler speler = new Speler(gebruikernaam,geboortejaar);
        controleerSpelerUniek(speler);
        spelers.add(speler);
    }

    private void controleerSpelerUniek(Speler speler) {
        List<String> uniqueKeys = spelers.stream() //verander arraylist in stream
                .map((element) -> element.getGebruikersnaam() + element.getGeboortejaar()) //map speler naar strings
                .filter((e) -> e.equals(speler.getGebruikersnaam() + speler.getGeboortejaar())) //filter alle spelers die niet voldoen aan keys
                .collect(Collectors.toList()); //geef lijst terug met objecten

        if (!uniqueKeys.isEmpty())
            throw new IllegalArgumentException(ExceptionTextDatabase.GEBRUIKER_BESTAAT_AL);
    }

}



