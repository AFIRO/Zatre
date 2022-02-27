package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpelerRepository {
    private final List<Speler> spelers;

    public SpelerRepository() {
        spelers = new ArrayList<>();
    }

    public void voegSpelerToe(Speler speler) {
        controleerOfSpelerUniekIs(speler);
        spelers.add(speler);

        }

    private void controleerOfSpelerUniekIs(Speler speler) {
        List<String> uniqueKeys = spelers.stream() //verander arraylist in stream
                .map((element) -> element.getGebruikersnaam() + element.getGeboortejaar()) //map speler naar strings
                .filter((e) -> e.equals(speler.getGebruikersnaam() + speler.getGeboortejaar())) //filter alle spelers die niet voldoen aan keys
                .collect(Collectors.toList()); //geef lijst terug met objecten

        if (!uniqueKeys.isEmpty())
             throw new IllegalArgumentException("Gebruiker bestaat al");
    }

}



