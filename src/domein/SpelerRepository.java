package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpelerRepository {
    private final List<Speler> spelers;

    public SpelerRepository() {
        spelers = new ArrayList<>();
    }

    public void VoegSpelerToe(Speler speler) {
        List<String> uniqueKeys = spelers.stream() //verander arraylist in stream
                .map((element) -> element.getGebruikersnaam() + element.getGeboortejaar()) //map speler naar strings
                .collect(Collectors.toList()); //geef lijst terug met objecten

        for (String key : uniqueKeys) {
            if (key.equals(speler.getGebruikersnaam() + speler.getGeboortejaar()))
                throw new IllegalArgumentException("Gebruiker bestaat al");

            spelers.add(speler);

        }

    }


}
