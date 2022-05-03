package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {
    private final Taalkeuze taalkeuze;
    private ResourceBundle dictionary;

    /**
     * UC1: constructor.
     *
     * @param taalkeuze de taalkeuze van de speler
     */

    public Taal(Taalkeuze taalkeuze) {
        this.taalkeuze = taalkeuze;
        setResourceBundle(taalkeuze);
    }

    /**
     * UC1: setter voor resourcebundel. Laat ons toe om nadien de taal aan te passen
     *
     * @param taalkeuze de taalkeuze van de speler
     */

    private void setResourceBundle(Taalkeuze taalkeuze) {
        if (taalkeuze.equals(Taalkeuze.NEDERLANDS))
            this.dictionary = ResourceBundle.getBundle("dictionary", new Locale("nl", "BE"));
        else
            this.dictionary = ResourceBundle.getBundle("dictionary", new Locale("en", "GB"));

    }

    /**
     * UC1: getter die ons toegang geeft tot de vertaling.
     *
     * @param key key voor phrase in vertaling library
     */

    public String getLocalisatie(String key) {
        return dictionary.getString(key);
    }

    public enum Taalkeuze {NEDERLANDS, ENGELS}

}
