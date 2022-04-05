package testen;

import domein.Scoreblad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScorebladTest {
    private static final boolean DUBBELE_PUNTEN = true;
    private static final boolean ENKELE_PUNTEN = false;
    private static final boolean TIEN_PUNTEN_GEHAALD = true;
    private static final boolean TIEN_PUNTEN_NIET_GEHAALD = false;
    private static final boolean ELF_PUNTEN_GEHAALD = true;
    private static final boolean ELF_PUNTEN_NIET_GEHAALD = false;
    private static final boolean TWAALF_PUNTEN_GEHAALD = true;
    private static final boolean TWAALF_PUNTEN_NIET_GEHAALD = false;

    @Test
    @DisplayName("Maak blad, voeg regel toe, genereer een passende string")
    void create_alsScorebladRegelBestaat_CorrecteString(){
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD); //3
        assertEquals("O X X O 3 3", testBlad.getRegels().get(0));
    }

    @Test
    @DisplayName("Maak blad, simuleer volledig spel, genereer een passende string list")
    void create_alsScorebladRegelsBestaan_CorrecteStringList(){
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_NIET_GEHAALD,ELF_PUNTEN_NIET_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD); //0
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD); //3
        testBlad.voegRegelToeAanScoreblad(DUBBELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //20
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_NIET_GEHAALD,ELF_PUNTEN_NIET_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD); //0
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //11
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //11
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //11
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //11
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //12
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //12
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //12
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //12
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //13

        List<String> verwachteStrings = List.of(
                "O O O O 3 0",
                "O X X O 3 3",
                "X X X X 3 20",
                "O O O O 3 0",
                "O X X X 4 11",
                "O X X X 4 11",
                "O X X X 4 11",
                "O X X X 4 11",
                "O X X X 5 12",
                "O X X X 5 12",
                "O X X X 5 12",
                "O X X X 5 12",
                "O X X X 6 13");

        assertIterableEquals(verwachteStrings, testBlad.getRegels());
    }

    @Test
    @DisplayName("Maak blad, voeg geen regels toe, genereer lege lijst")
    void create_alsScorebladRegelNietBestaat_LegeLijst(){
        Scoreblad testBlad = new Scoreblad();
        assertTrue(testBlad.getRegels().isEmpty());
    }


    @Test
    @DisplayName("Maak blad, voeg regels toe, bereken correcte score voor blad")
    void create_alsScorebladRegelsBestaan_CorrecteScore(){
        Scoreblad testBlad = new Scoreblad();
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD); //3
        testBlad.voegRegelToeAanScoreblad(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD); //10


        assertEquals(13,testBlad.berekenScoreVanScoreblad());
    }







}
