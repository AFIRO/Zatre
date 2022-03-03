package testen;

import domein.SpelerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpelerRepositoryTest {
    SpelerRepository spelerRepository = new SpelerRepository();
    private final ResourceBundle gelocaliseerdeTaalbundel = ResourceBundle.getBundle("dictionary", Locale.getDefault());
    private static final String CORRECTE_GEBRUIKERSNAAM = "Joske123";
    private static final int GEBOORTEJAAR_TOEGELATEN_LEEFTIJD = 1990;


    @Test
    @DisplayName("speler toevoegen, happy flow")
    public void toevoegen_spelerOk_happyflow() {

        assertDoesNotThrow(() -> spelerRepository.voegSpelerToe(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
    }

    @Test
    @DisplayName("Registreer proberen van dubbele speler, gooit exception")
    public void toevoegen_SpelerBestaatAl_Exception() {
        spelerRepository.voegSpelerToe(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> spelerRepository.voegSpelerToe(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKER_BESTAAT_AL"), exception.getMessage());
    }






}