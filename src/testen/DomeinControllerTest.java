package testen;

import domein.DomeinController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.SpelerMapper;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


class DomeinControllerTest {
    private final DomeinController domeinController = new DomeinController();
    private final SpelerMapper spelerMapper = new SpelerMapper();
    private final ResourceBundle gelocaliseerdeTaalbundel = ResourceBundle.getBundle("dictionary", Locale.getDefault());
    private static final String CORRECTE_GEBRUIKERSNAAM = "Testuser";
    private static final String INCORRECTE_GEBRUIKERSNAAM = "Jos";
    private static final int GEBOORTEJAAR_TOEGELATEN_LEEFTIJD = 1990;
    private static final int GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD = 2020;

    @BeforeEach
    private void reset() {
        if (Optional.of(spelerMapper.geefSpeler(CORRECTE_GEBRUIKERSNAAM,GEBOORTEJAAR_TOEGELATEN_LEEFTIJD)).isPresent());
    }


    @Test
    @DisplayName("Registreer proberen, correcte speler gemaakt")
    public void registreer_wanneerCorrecteInfoDoorgegeven_objectGemaakt() {

        assertDoesNotThrow(() -> domeinController.registreer(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
    }

    @Test
    @DisplayName("Registreer proberen met niet toegelaten leeftijd, exception wordt gesmeten")
    public void registreer_wanneerInCorrecteLeeftijdDoorgegeven_Exception() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> domeinController.registreer(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKER_TE_JONG"), exception.getMessage());
    }

    @Test
    @DisplayName("Registreer proberen met te korte naam, exception wordt gesmeten")
    public void registreer_wanneerIncorrecteNaamDoorgegeven_Exception() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> domeinController.registreer(INCORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKERSNAAM_TE_KORT"), exception.getMessage());
    }

    @Test
    @DisplayName("Registreer proberen met edgecase leeftijd, correcte speler gemaakt")
    public void registreer_wanneerSpelerHuidigeZesZouWorden_objectGemaakt() {

        assertDoesNotThrow(() -> domeinController.registreer(CORRECTE_GEBRUIKERSNAAM, LocalDate.now().minusYears(6).plusDays(30).getYear()));
    }


    @Test
    @DisplayName("Registreer proberen van dubbele speler, gooit exception")
    public void registreer_spelerAlBestaat_Exception() {
        domeinController.registreer(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> domeinController.registreer(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKER_BESTAAT_AL"), exception.getMessage());
    }

}