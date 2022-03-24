package domein;

import java.util.HashMap;
import java.util.Map;

public class Spelbord {
    private final Map<String, Vak> vakjes;
    
    /**
     * UC3: constructor Spelbord
     */
    public Spelbord() {
        vakjes = genereerSpelbord();
    }
    
    /**
     * UC3: spelbord wordt aangemaakt
     * Eerst maken we het kleinere vierkant
     * vervolgens stellen we de kleur van de diagonalen in op wit 
     * dan voegen we de buitenste vakjes toe
     * en hiervan stellen we ook de correcte vakjes in op wit
     * @return
     */
    private Map<String, Vak> genereerSpelbord() {
        Map<String, Vak> vakjes = new HashMap<>();

        //genereer vierkant
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                vakjes.put(String.format("%d.%d", i+2, j+2), new Vak(i+2, j+2));
            }

        }

        //set de correctie vakjes in vierkant naar wit
        for (Vak vak : vakjes.values()){
            if (vak.getKolom() == vak.getRij())
                vak.setKleur(Vak.Kleur.WIT);

            if (16-vak.getRij() == vak.getKolom())
                vak.setKleur(Vak.Kleur.WIT);
        }

        //voeg buitenste vakjes toe
        vakjes.put("5.1",new Vak(5,1));
        vakjes.put("6.1",new Vak(6,1));
        vakjes.put("7.1",new Vak(7,1));
        vakjes.put("9.1",new Vak(9,1));
        vakjes.put("10.1",new Vak(10,1));
        vakjes.put("11.1",new Vak(11,1));

        vakjes.put("5.15",new Vak(5,15));
        vakjes.put("6.15",new Vak(6,15));
        vakjes.put("7.15",new Vak(7,15));
        vakjes.put("9.15",new Vak(9,15));
        vakjes.put("10.15",new Vak(10,15));
        vakjes.put("11.15",new Vak(11,15));

        vakjes.put("1.5",new Vak(1,5));
        vakjes.put("1.6",new Vak(1,6));
        vakjes.put("1.7",new Vak(1,7));
        vakjes.put("1.9",new Vak(1,9));
        vakjes.put("1.10",new Vak(1,10));
        vakjes.put("1.11",new Vak(1,11));

        vakjes.put("15.5",new Vak(15,5));
        vakjes.put("15.6",new Vak(15,6));
        vakjes.put("15.7",new Vak(15,7));
        vakjes.put("15.9",new Vak(15,9));
        vakjes.put("15.10",new Vak(15,10));
        vakjes.put("15.11",new Vak(15,11));

        //set correcte buitenste vakjes naar wit
        vakjes.get("7.1").setKleur(Vak.Kleur.WIT);
        vakjes.get("9.1").setKleur(Vak.Kleur.WIT);
        vakjes.get("7.15").setKleur(Vak.Kleur.WIT);
        vakjes.get("9.15").setKleur(Vak.Kleur.WIT);
        vakjes.get("1.7").setKleur(Vak.Kleur.WIT);
        vakjes.get("1.9").setKleur(Vak.Kleur.WIT);
        vakjes.get("15.7").setKleur(Vak.Kleur.WIT);
        vakjes.get("15.9").setKleur(Vak.Kleur.WIT);

        return vakjes;
    }
    
    /**
     * UC3: vraagt de vakjes op en return de Map hiervan
     * @return
     */
    public Map<String, Vak> getVakjes() {
        return vakjes;
    }
}



