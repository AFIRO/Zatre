package domein;

import java.time.LocalDate;
import java.util.Objects;

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
        if (gebruikersnaam == null
                || gebruikersnaam.isBlank()
                || gebruikersnaam.length() < 5)
            throw new IllegalArgumentException("GEBRUIKERSNAAM_TE_KORT");
    }

    private void controleerGeboortejaar(int geboortejaar) {
        if (geboortejaar > LocalDate.now().getYear() || geboortejaar <= 0)
            throw new IllegalArgumentException("ONGELDIG_GEBOORTEJAAR");

        if ((LocalDate.now().getYear() - geboortejaar) < 6)
            throw new IllegalArgumentException("GEBRUIKER_TE_JONG");

    }

    public int getSpeelkansen() {
        return speelkansen;
    }


    public void setSpeelkansen(int speelkansen) {
        if (speelkansen < 0)
            throw new IllegalArgumentException("SPEELKANSEN_NEGATIEF");

        this.speelkansen = speelkansen;
    }


    public String getGebruikersnaam() {
        return this.gebruikersnaam;
    }

    public int getGeboortejaar() {
        return this.geboortejaar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speler)) return false;
        Speler speler = (Speler) o;
        return getGeboortejaar() == speler.getGeboortejaar() && getGebruikersnaam().equals(speler.getGebruikersnaam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGebruikersnaam(), getGeboortejaar());
    }
    
    
}