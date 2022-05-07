package testen;

import domein.Scoreblad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScorebladTest {
    @Test
    @DisplayName("Maak blad, voeg 1 steentje toe, genereer een passende string")
    void create_alsScorebladRegelBestaat_CorrecteString() {
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11")));//3
        assertEquals(" ,X,X, ,3,3", testBlad.getRegels().get(0));
    }

    @Test
    @DisplayName("Maak blad, simuleer volledig spel, genereer een passende string list")
    void create_alsScorebladRegelsBestaan_CorrecteStringList() {
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,7,9"))); //0
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11"))); //3
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("1,10,11,12")));//20
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11"))); //3
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,3,5"))); //0
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12"))); //11
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12"))); //11
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12", "0,10,11,12"))); //18
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12"))); //12
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,10", "1,10,10"))); //8
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12"))); //12
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11,12"))); //12
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,12,12", "1,12,12"))); //13

        List<String> verwachteStrings = List.of(
                " , , , ,3,0",
                " ,X,X, ,3,3",
                "X,X,X,X,3,20",
                " ,X,X, ,3,3",
                " , , , ,4,0",
                " ,X,X,X,4,11",
                " ,X,X,X,4,11",
                " ,XX,XX,XX,4,18",
                " ,X,X,X,5,12",
                "X,XXXX, , ,5,8",
                " ,X,X,X,5,12",
                " ,X,X,X,5,12",
                "X, , ,XXXX,6,32");

        assertIterableEquals(verwachteStrings, testBlad.getRegels());
    }

    @Test
    @DisplayName("Maak blad, voeg geen regels toe, genereer lege lijst")
    void create_alsScorebladRegelNietBestaat_LegeLijst() {
        Scoreblad testBlad = new Scoreblad();
        assertTrue(testBlad.getRegels().isEmpty());
    }


    @Test
    @DisplayName("Maak blad, voeg 2 zetten (volledige ronde) toe, bereken correcte score voor blad")
    void create_alsScorebladRegelsBestaan_CorrecteScore() {
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11", "0,10,11,12"))); //3+10
        assertEquals(13, testBlad.berekenScoreVanScoreblad());
    }

    @Test
    @DisplayName("Maak blad, voeg 2 zetten (volledige ronde) toe, geef correcte string")
    void create_alsScorebladRegelsBestaan_correcteString() {
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelsToeAanScoreblad(new ArrayList<>(List.of("0,10,11", "1,10,11,12"))); //3+10
        assertEquals("X,XX,XX,X,3,26", testBlad.getRegels().get(0));
    }
}
