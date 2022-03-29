package testen;

import domein.Speler;
import domein.SpelerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.SpelerMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpelerRepositoryTest {
    @Mock
    private SpelerMapper spelerMapper;
    @InjectMocks
    private SpelerRepository spelerRepository;

    private final static String NAAM_TOEGELATEN = "Joske123";
    private final static int GEBOORTEJAAR_TOEGELATEN = 1990;


    @Test
    @DisplayName("Voeg speler toe happy flow")
    public void setSpelerRepository_VoegSpelerToe_HappyFlow(){
        spelerRepository.voegSpelerToe(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);
        verify(spelerMapper,times(1)).voegSpelerToe(new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("Vraag speler op, speler is correct in spelerlijst gestoken")
    public void vraagSpelerOp_HappyFlow(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        assertTrue(spelerRepository.geefSpelers().contains(testSpeler));
    }

    @Test
    @DisplayName("Vraag speler op, speler geeft onvoldoende speelkansen, gooit exception")
    public void vraagSpelerOp_OnvoldoendeSpeelkansen_Exception(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);
        testSpeler.setSpeelkansen(0);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);

        assertThrows(IllegalArgumentException.class,()->spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("Vraag speler op, speler is al aangemeld, gooit exception")
    public void vraagSpelerOp_SpelerIsAlAangemeld_Exception(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        assertThrows(IllegalArgumentException.class,()->spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("Vraag speler op, vijfde speler, gooit exception")
    public void vraagSpelerOp_VijfdeSpeler_Exception(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler("Lorenz", 1988)).thenReturn(new Speler("Lorenz", 1988));
        when(spelerMapper.geefSpeler("Sofie", 1982)).thenReturn(new Speler("Sofie", 1982));
        when(spelerMapper.geefSpeler("TessT", 1992)).thenReturn(new Speler("TessT", 1992));
        when(spelerMapper.geefSpeler("Andreeas", 1990)).thenReturn(new Speler("Andreeas", 1990));
        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        spelerRepository.vraagSpelerOp("Lorenz", 1988);
        spelerRepository.vraagSpelerOp("Sofie", 1982);
        spelerRepository.vraagSpelerOp("TessT", 1992);
        spelerRepository.vraagSpelerOp("Andreeas", 1990);

        assertThrows(IllegalArgumentException.class,()->spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("Geef aangemelde speler, happy flow")
    public void geefAangemeldeSpeler_HappyFlow(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        Speler teruggekeerdeSpeler = spelerRepository.geefAangemeldeSpeler(NAAM_TOEGELATEN,GEBOORTEJAAR_TOEGELATEN);

        assertEquals(testSpeler.getGebruikersnaam(),teruggekeerdeSpeler.getGebruikersnaam());
        assertEquals(testSpeler.getGeboortejaar(),teruggekeerdeSpeler.getGeboortejaar());
        assertEquals(testSpeler.getSpeelkansen(),teruggekeerdeSpeler.getSpeelkansen());
    }

    @Test
    @DisplayName("Geef speler, happy flow")
    public void geefSpeler_HappyFlow(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        when(spelerMapper.checkOfSpelerAlBestaatInDatabase(testSpeler)).thenReturn(true);

        Speler teruggekeerdeSpeler = spelerRepository.geefSpeler(NAAM_TOEGELATEN,GEBOORTEJAAR_TOEGELATEN);

        assertEquals(testSpeler.getGebruikersnaam(),teruggekeerdeSpeler.getGebruikersnaam());
        assertEquals(testSpeler.getGeboortejaar(),teruggekeerdeSpeler.getGeboortejaar());
        assertEquals(testSpeler.getSpeelkansen(),teruggekeerdeSpeler.getSpeelkansen());
    }

    @Test
    @DisplayName("Geef speler, bestaat niet, exception")
    public void geefSpeler_BestaatNiet_exception(){
        assertThrows(IllegalArgumentException.class,()->spelerRepository.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("Geef speler, Niet Aangemeld, exception")
    public void geefSpeler_NietAangemeld_exception(){
        assertThrows(IllegalArgumentException.class,()->spelerRepository.geefAangemeldeSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN));
    }

    @Test
    @DisplayName("update speler, happy flow")
    public void updateSpeler_happyflow(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        when(spelerMapper.geefSpeler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN)).thenReturn(testSpeler);
        spelerRepository.vraagSpelerOp(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);
        testSpeler.setSpeelkansen(4);
        spelerRepository.updateSpeler(testSpeler);
        Speler teruggekeerdeSpeler = spelerRepository.geefSpelers().get(0);

        assertEquals(testSpeler.getGebruikersnaam(),teruggekeerdeSpeler.getGebruikersnaam());
        assertEquals(testSpeler.getGeboortejaar(),teruggekeerdeSpeler.getGeboortejaar());
        assertEquals(testSpeler.getSpeelkansen(),teruggekeerdeSpeler.getSpeelkansen());
        verify(spelerMapper,times(1)).updateSpeler(testSpeler);

    }

    @Test
    @DisplayName("update speler, niet aangemeld, exception")
    public void updateSpeler_NietAangemeld_Exception(){
        Speler testSpeler = new Speler(NAAM_TOEGELATEN, GEBOORTEJAAR_TOEGELATEN);

        assertThrows(IllegalArgumentException.class,()->spelerRepository.updateSpeler(testSpeler));
    }

}
