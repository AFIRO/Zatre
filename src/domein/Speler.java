package domein;

import exceptions.ExceptionTextDatabase;

import java.time.LocalDate;
import java.util.Calendar;

public class Speler {

    private final String gebruikersnaam;
    private final int geboortejaar;
    private int speelkansen;

    public Speler(String gebruikersnaam, int geboortejaar) {
        this(gebruikersnaam, geboortejaar, 5);
    }

    public Speler(String gebruikersnaam, int geboortejaar, int speelkansen) {
        controleerGebruikersnaam(gebruikersnaam);
        this.gebruikersnaam = gebruikersnaam;
        controleerGeboortejaar(geboortejaar);
        this.geboortejaar = geboortejaar;
        setSpeelkansen(speelkansen);
    }

    private void controleerGebruikersnaam(String gebruikersnaam) {
        if (gebruikersnaam.length() < 5)
            throw new IllegalArgumentException(ExceptionTextDatabase.GEBRUIKERSNAAM_TE_KORT);
    }

    private void controleerGeboortejaar(int geboortejaar) {
        if ((LocalDate.now().getYear()  - geboortejaar) < 6)
            throw new IllegalArgumentException(ExceptionTextDatabase.GEBRUIKER_TE_JONG);

        if (geboortejaar > LocalDate.now().getYear()  || geboortejaar <= 0)
            throw new IllegalArgumentException(ExceptionTextDatabase.ONGELDIG_GEBOORTEJAAR);
    }

    public int getSpeelkansen() {
        return speelkansen;
    }


    public void setSpeelkansen(int speelkansen) {
        if (speelkansen < 0)
            throw new IllegalArgumentException(ExceptionTextDatabase.SPEELKANSEN_NEGATIEF);

        this.speelkansen = speelkansen;
    }


    public String getGebruikersnaam() {
        return this.gebruikersnaam;
    }

    public int getGeboortejaar() {
        return this.geboortejaar;
    }

}