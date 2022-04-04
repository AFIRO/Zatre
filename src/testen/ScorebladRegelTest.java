package testen;

import domein.ScorebladRegel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScorebladRegelTest {
    private static final boolean DUBBELE_PUNTEN = true;
    private static final boolean ENKELE_PUNTEN = false;
    private static final boolean TIEN_PUNTEN_GEHAALD = true;
    private static final boolean TIEN_PUNTEN_NIET_GEHAALD = false;
    private static final boolean ELF_PUNTEN_GEHAALD = true;
    private static final boolean ELF_PUNTEN_NIET_GEHAALD = false;
    private static final boolean TWAALF_PUNTEN_GEHAALD = true;
    private static final boolean TWAALF_PUNTEN_NIET_GEHAALD = false;
    private static final int BONUSPUNT_TOEGELATEN_WAARDE = 3;
    private static final int BONUSPUNT_NIET_TOEGELATEN_WAARDE = 1;

    @Test
    @DisplayName("Maak regel, maar bonuspunt niet toegelaten, gooit exception")
    void create_bonuspuntIncorrect_exception(){
        assertThrows(IllegalArgumentException.class,
                () -> new ScorebladRegel(DUBBELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD,BONUSPUNT_NIET_TOEGELATEN_WAARDE));
    }

    @Test
    @DisplayName("maak regel, happy flow")
    void create_happyFlow(){
        assertDoesNotThrow(
                () -> new ScorebladRegel(DUBBELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE));
    }

    @Test
    @DisplayName("Bereken correcte score indien geen 10, 11 of 12")
    void berekenScore_geen10of11of12(){
        ScorebladRegel testRegel = new ScorebladRegel(DUBBELE_PUNTEN,TIEN_PUNTEN_NIET_GEHAALD,ELF_PUNTEN_NIET_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE);
        assertEquals(0,testRegel.getScoreVoorRegel());
    }

    @Test
    @DisplayName("Bereken correcte score indien 10")
    void berekenScore_10(){
        ScorebladRegel testRegel = new ScorebladRegel(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_NIET_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE);
        assertEquals(1,testRegel.getScoreVoorRegel());
    }

    @Test
    @DisplayName("Bereken correcte score indien 10 en 11")
    void berekenScore_10en11(){
        ScorebladRegel testRegel = new ScorebladRegel(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_NIET_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE);
        assertEquals(3,testRegel.getScoreVoorRegel());
    }

    @Test
    @DisplayName("Bereken correcte score indien 10 en 11 en 12")
    void berekenScore_10en11en12(){
        ScorebladRegel testRegel = new ScorebladRegel(ENKELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE);
        assertEquals(10,testRegel.getScoreVoorRegel());
    }

    @Test
    @DisplayName("Bereken correcte score indien 10 en 11 en 12 met dubbele punten")
    void berekenScore_10en11en12MetDubbelePunten(){
        ScorebladRegel testRegel = new ScorebladRegel(DUBBELE_PUNTEN,TIEN_PUNTEN_GEHAALD,ELF_PUNTEN_GEHAALD,TWAALF_PUNTEN_GEHAALD,BONUSPUNT_TOEGELATEN_WAARDE);
        assertEquals(20,testRegel.getScoreVoorRegel());
    }



}
