package testen;

import domein.Vak;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VakTest {
    @Test
    @DisplayName("Toegelaten vakje, happy flow")
    void create_happyflow() {
        assertDoesNotThrow(() -> new Vak(8, 8));
    }


    @Test
    @DisplayName("Niet toegelaten vakje, throws exception")
    void create_VakBuitenBord_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Vak(16, 16));
    }
}
