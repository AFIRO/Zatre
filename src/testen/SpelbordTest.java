package testen;

import domein.Spelbord;
import domein.Vak;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class SpelbordTest {

    @Test
    @DisplayName("Maak spelbord, correcte vakjes gegenereerd streekproef")
    public void create_VakjesCorrect() {
        Spelbord testBord = new Spelbord();
        Vak testVak1 = testBord.getVakjes().get("7.4");
        Vak testVak2 = testBord.getVakjes().get("9.15");

        assertEquals(7,testVak1.getKolom());
        assertEquals(4,testVak1.getRij());
        assertEquals(9,testVak2.getKolom());
        assertEquals(15,testVak2.getRij());
        assertEquals(Vak.Kleur.ZWART,testVak1.getKleur());
        assertEquals(Vak.Kleur.WIT,testVak2.getKleur());
    }

    @Test
    @DisplayName("Maak spelbord, vraag onbestaand vakje, is null")
    public void create_VraagIncorrectVakje_null() {
        Spelbord testBord = new Spelbord();
        assertNull(testBord.getVakjes().get("16.16"));
    }
}
