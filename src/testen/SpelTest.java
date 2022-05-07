package testen;

import domein.Spel;
import domein.Speler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpelTest {
    private static final Speler SPELER_1 = new Speler("Andreeas", 1990);
    private static final Speler SPELER_2 = new Speler("Lorenz", 1990);
    private static final Speler SPELER_3 = new Speler("Sofie", 1990);
    private static final Speler SPELER_4 = new Speler("Scarlett", 1990);

    @Test
    @DisplayName("Create spel, alle elementen correct geinitialiseerd")
    void create_happyflow() {
        ArrayList<Speler> spelers = new ArrayList<>(List.of(SPELER_1, SPELER_2, SPELER_3, SPELER_4));
        assertDoesNotThrow(() -> new Spel(spelers));
    }

    @Test
    @DisplayName("Start spel met voldoende spelers, happy flow")
    void startSpel_voldoendeSpelers_HappyFlow() {
        ArrayList<Speler> spelers = new ArrayList<>(List.of(SPELER_1, SPELER_2, SPELER_3, SPELER_4));
        Spel testSpel = new Spel(spelers);
        assertDoesNotThrow(testSpel::startSpel);

    }

    @Test
    @DisplayName("Start spel met onvoldoende spelers, happy flow")
    void startSpel_onvoldoendeSpelers_HappyFlow() {
        ArrayList<Speler> spelers = new ArrayList<>(List.of(SPELER_1));
        Spel testSpel = new Spel(spelers);
        assertThrows(IllegalArgumentException.class, testSpel::startSpel);

    }

    @Test
    @DisplayName("Speel beurt, correcte string terug")
    void speelBeurt_correcteString() {
        ArrayList<Speler> spelers = new ArrayList<>(List.of(SPELER_1, SPELER_2, SPELER_3, SPELER_4));
        Spel testSpel = new Spel(spelers);
        List<String> zetten = new ArrayList<>(List.of("6 8.8", "6 9.8"));
        List<String> scoreBlad = testSpel.speelBeurt("Andreeas", "1990", zetten);
        assertEquals(" , , ,X,3,4", scoreBlad.get(0));

    }
}
