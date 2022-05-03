package domein;

import java.util.Objects;

public class Steen {
    private final int waarde;

    /**
     * UC3: constructor van steen, kent waarde aan steen toe
     *
     * @param waarde de waarde van de steen
     */
    public Steen(int waarde) {
        if (waarde < 1 || waarde > 6) {
            throw new IllegalArgumentException();
        }
        this.waarde = waarde;
    }

    /**
     * UC3: methode om waarde steen op te vragen.
     *
     * @return waarde van steen
     */
    public int getWaarde() {
        return waarde;
    }

    /**
     * UC3: override gelijkheid met als basis de waarde van de steen
     */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Steen))
            return false;
        Steen steen = (Steen) o;
        return getWaarde() == steen.getWaarde();
    }

    /**
     * UC3: override de hascode omdat we stenen met hun waarde identificeren
     */

    @Override
    public int hashCode() {
        return Objects.hash(getWaarde());
    }
}
