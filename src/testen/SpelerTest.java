package testen;

import exceptions.ExceptionTextDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domein.Speler;

import static org.junit.jupiter.api.Assertions.*;

class SpelerTest {
    private static final String CORRECTE_GEBRUIKERSNAAM = "Joske123";
    private static final String INCORRECTE_GEBRUIKERSNAAM = "Jos";
    private static final int GEBOORTEJAAR_TOEGELATEN_LEEFTIJD = 1990;
    private static final int GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD = 2020;


    @Test
    @DisplayName("Correct aanmaken van speler met correcte info")
    public void create_InfoCorrect_ObjectWordtGemaakt() {
        assertDoesNotThrow(() -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
    }

    @Test
    @DisplayName("Correct aanmaken van speler met correcte info, check op info")
    public void create_InfoCorrect_ObjectWordtGemaaktMetJuisteVelden() {
        Speler speler = new Speler(CORRECTE_GEBRUIKERSNAAM,GEBOORTEJAAR_TOEGELATEN_LEEFTIJD);
        assertEquals(GEBOORTEJAAR_TOEGELATEN_LEEFTIJD, speler.getGeboortejaar());
        assertEquals(CORRECTE_GEBRUIKERSNAAM,speler.getGebruikersnaam());
        assertEquals(5,speler.getSpeelkansen());
    }

    @Test
    @DisplayName("Incorrecte naam, exception")
    public void create_NaamIncorrect_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(INCORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD));
        assertEquals(ExceptionTextDatabase.GEBRUIKERSNAAM_TE_KORT, exception.getMessage());
    }

    @Test
    @DisplayName("Incorrecte leeftijd, exception")
    public void create_LeeftijdIncorrect_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD));
        assertEquals(ExceptionTextDatabase.GEBRUIKER_TE_JONG, exception.getMessage());
    }

    @Test
    @DisplayName("Leeftijd onder nul, exception")
    public void create_LeeftijdNegatief_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, -1));
        assertEquals(ExceptionTextDatabase.ONGELDIG_GEBOORTEJAAR, exception.getMessage());
    }

    @Test
    @DisplayName("Speelkansen negatief setter, exception")
    public void setSpeelkansen_negatief_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD).setSpeelkansen(-5));
        assertEquals(ExceptionTextDatabase.SPEELKANSEN_NEGATIEF, exception.getMessage());
    }



}