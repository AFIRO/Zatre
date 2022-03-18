package domein;

public class Vak {
    public enum Kleur {WIT,ZWART}
    private final int kolom;
    private final int rij;
    private Kleur kleur;
    private int score;

    public Vak(int kolom, int rij) {
        this.kolom = kolom;
        this.rij = rij;
        setKleur(Kleur.ZWART);
    }

    public int getKolom() {
        return kolom;
    }

    public int getRij() {
        return rij;
    }

    public Kleur getKleur() {
        return kleur;
    }

    public int getScore() {
        return score;
    }

    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
