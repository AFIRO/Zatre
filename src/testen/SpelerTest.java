package testen;

import domein.Speler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SpelerTest {
    private static final String CORRECTE_GEBRUIKERSNAAM = "Joske123";
    private static final String INCORRECTE_GEBRUIKERSNAAM = "Jos";
    private static final int GEBOORTEJAAR_TOEGELATEN_LEEFTIJD = LocalDate.now().minusYears(12).getYear();
    private static final int GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD = LocalDate.now().minusYears(2).getYear();
    private static final int GEBOORTEJAAR_GENS_GEVAL = LocalDate.now().minusYears(5).getYear();

    @Test
    @DisplayName("Correct aanmaken van speler met correcte info")
    public void create_InfoCorrect_ObjectWordtGemaakt() {
        assertDoesNotThrow(() -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
    }

    @Test
    @DisplayName("Correct aanmaken van speler met correcte info, check op info")
    public void create_InfoCorrect_ObjectWordtGemaaktMetJuisteVelden() {
        Speler speler = new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD);
        assertEquals(GEBOORTEJAAR_TOEGELATEN_LEEFTIJD, speler.getGeboortejaar());
        assertEquals(CORRECTE_GEBRUIKERSNAAM, speler.getGebruikersnaam());
        assertEquals(5, speler.getSpeelkansen());
    }

    @Test
    @DisplayName("Incorrecte naam, exception")
    public void create_NaamIncorrect_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Speler(INCORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
    }

    @Test
    @DisplayName("Incorrecte leeftijd, exception")
    public void create_LeeftijdIncorrect_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD));

    }

    @Test
    @DisplayName("Incorrecte leeftijd grensgeval, exception")
    public void create_LeeftijdIncorrectGrensGeval_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_GENS_GEVAL));

    }

    @Test
    @DisplayName("Leeftijd onder nul, exception")
    public void create_LeeftijdNegatief_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, -1));

    }

    @Test
    @DisplayName("Speelkansen negatief setter, exception")
    public void setSpeelkansen_negatief_Exception() {
        assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD).setSpeelkansen(-5));

    }


}