package domein;

import java.time.LocalDate;
import java.util.Objects;

public class Speler {

    private final String gebruikersnaam;
    private final int geboortejaar;
    private int speelkansen;
    private final Scoreblad scoreblad;

    public Speler(String gebruikersnaam, int geboortejaar) {
        this(gebruikersnaam, geboortejaar, 5);
    }

    public Speler(String gebruikersnaam, int geboortejaar, int speelkansen) {
        controleerGebruikersnaam(gebruikersnaam);
        this.gebruikersnaam = gebruikersnaam;
        controleerGeboortejaar(geboortejaar);
        this.geboortejaar = geboortejaar;
        setSpeelkansen(speelkansen);
        this.scoreblad = new Scoreblad();
    }

    /**
     * UC1: controleren of gebruikersnaam niet leeg is of kleiner is dan 5 karakters
     * exception gooien indien ze niet voldoende lang is
     *
     * @param gebruikersnaam Gebruikersnaam van speler
     * @throws IllegalArgumentException indien gebruikersnaam te kort
     */

    private void controleerGebruikersnaam(String gebruikersnaam) {
        if (gebruikersnaam == null
                || gebruikersnaam.isBlank()
                || gebruikersnaam.length() < 5)
            throw new IllegalArgumentException("GEBRUIKERSNAAM_TE_KORT");
    }

    /**
     * UC1: controleren of geboortejaar kleiner is dan het huidige jaar, groter dan nul en speler dus minstens 6 jaar is
     * exception gooien indien dit niet het geval is
     *
     * @param geboortejaar Geboortejaar van speler
     * @throws IllegalArgumentException indien geboortejaar hoger is an huidig jaar of negatief
     * @throws IllegalArgumentException indien huidige jaar - geboortejaar lager is dan 6 (dus speler te jong)
     */

    private void controleerGeboortejaar(int geboortejaar) {
        if (geboortejaar > LocalDate.now().getYear() || geboortejaar <= 0)
            throw new IllegalArgumentException("ONGELDIG_GEBOORTEJAAR");

        if ((LocalDate.now().getYear() - geboortejaar) < 6)
            throw new IllegalArgumentException("GEBRUIKER_TE_JONG");

    }

    public int getSpeelkansen() {
        return speelkansen;
    }

    /**
     * UC1&2: setter voor speelkansen, controleert of speelkansen niet negatief zijn
     * exception gooien indien de speekansen negatief zijn
     *
     * @param speelkansen speelkansen van speler
     * @throws IllegalArgumentException indien negatieve speelkansen wordt geset
     */

    public void setSpeelkansen(int speelkansen) {
        if (speelkansen < 0)
            throw new IllegalArgumentException("SPEELKANSEN_NEGATIEF");

        this.speelkansen = speelkansen;
    }

    public Scoreblad getScoreblad() {
        return scoreblad;
    }

    public String getGebruikersnaam() {
        return this.gebruikersnaam;
    }

    public int getGeboortejaar() {
        return this.geboortejaar;
    }


    /**
     * UC1: overschrijft de equality regels voor spelers. Nieuwe regels checken op Gebruikersnaam en geboortejaar
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speler)) return false;
        Speler speler = (Speler) o;
        return getGeboortejaar() == speler.getGeboortejaar() && getGebruikersnaam().equals(speler.getGebruikersnaam());
    }

    /**
     * UC1: overschrijft de hashcode regels voor spelers. Nieuwe regels checken op Gebruikersnaam en geboortejaar
     */

    @Override
    public int hashCode() {
        return Objects.hash(getGebruikersnaam(), getGeboortejaar());
    }

    /**
     * UC2: overschrijft de standaard toString van de klasse Speler
     */

    @Override
    public String toString() {
        return String.format("%s: %s%n%s: %d%n%s: %d%n%n", "GEBRUIKERSNAAM", gebruikersnaam,
                "GEBOORTEJAAR", geboortejaar,
                "SPEELKANSEN", speelkansen);
    }

}