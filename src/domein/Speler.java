package domein;

import exceptions.ExceptionTextDatabase;

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
        if ((Calendar.getInstance().get(Calendar.YEAR) - geboortejaar) < 6)
            throw new IllegalArgumentException(ExceptionTextDatabase.GEBRUIKER_TE_JONG);
    }

    public int getSpeelkansen() {
        return speelkansen;
    }


    private void setSpeelkansen(int speelkansen) {
        this.speelkansen = speelkansen;
    }


    public String getGebruikersnaam() {
        return this.gebruikersnaam;
    }

    public int getGeboortejaar() {
        return this.geboortejaar;
    }

}