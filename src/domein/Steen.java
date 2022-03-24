package domein;

public class Steen {
    private final int waarde;
    
    /**
     * UC3: constructor van steen, kent waarde aan steen toe
     * @param waarde
     */
    public Steen(int waarde) {
        this.waarde = waarde;
    }
    
    /**
     * UC3: methode om waarde steen op te vragen. 
     * @return
     */
    public int getWaarde() {
        return waarde;
    }
}
