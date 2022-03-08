package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {
    public enum Taalkeuze {NEDERLANDS, ENGELS}
    private  Taalkeuze taalkeuze;
    private  ResourceBundle dictionary;

    public Taal(Taalkeuze taalkeuze){
        this.taalkeuze = taalkeuze;
        setResourceBundle(taalkeuze);
    }



    private void setResourceBundle(Taalkeuze taalkeuze) {
        if (taalkeuze.equals(Taalkeuze.NEDERLANDS))
            this.dictionary = ResourceBundle.getBundle("dictionary", new Locale("nl", "BE"));
        else
            this.dictionary = ResourceBundle.getBundle("dictionary", new Locale("en", "GB"));

    }

    public String getLocalisatie(String key){
        return dictionary.getString(key);
    }

}
