package testen;

import domein.Speler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class SpelerTest {
    private final ResourceBundle gelocaliseerdeTaalbundel = ResourceBundle.getBundle("dictionary", Locale.getDefault());
    private static final String CORRECTE_GEBRUIKERSNAAM = "Joske123";
    private static final String INCORRECTE_GEBRUIKERSNAAM = "Jos";
    private static final int GEBOORTEJAAR_TOEGELATEN_LEEFTIJD = 1990;
    private static final int GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD = 2020;
    private static final int GEBOORTEJAAR_GENS_GEVAL = 2017; 
    //Moet dit jaarlijks aangepast worden, of kunnen we dit laten berekenen. 
    //Door bijvoorbeeld te rekenen localDate - geboortejaar = 5

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
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKERSNAAM_TE_KORT"), exception.getMessage());
    } //werken we hier niet beter met een ParameterizedTest om op die manier meerdere zaken te testen. 

    @Test
    @DisplayName("Incorrecte leeftijd, exception")
    public void create_LeeftijdIncorrect_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_NIET_TOEGELATEN_LEEFTIJD));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKER_TE_JONG"), exception.getMessage());
    }
    
    @Test
    @DisplayName("Incorrecte leeftijd grensgeval, exception")
    public void create_LeeftijdIncorrectGrensGeval_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_GENS_GEVAL));
        assertEquals(gelocaliseerdeTaalbundel.getString("GEBRUIKER_TE_JONG"), exception.getMessage());
    }

    @Test
    @DisplayName("Leeftijd onder nul, exception")
    public void create_LeeftijdNegatief_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, -1));
        assertEquals(gelocaliseerdeTaalbundel.getString("ONGELDIG_GEBOORTEJAAR"), exception.getMessage());
    }

    @Test
    @DisplayName("Speelkansen negatief setter, exception")
    public void setSpeelkansen_negatief_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTE_GEBRUIKERSNAAM, GEBOORTEJAAR_TOEGELATEN_LEEFTIJD).setSpeelkansen(-5));
        assertEquals(gelocaliseerdeTaalbundel.getString("SPEELKANSEN_NEGATIEF"), exception.getMessage());
    }



}