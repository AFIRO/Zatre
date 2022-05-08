package gui;

import javafx.beans.property.SimpleStringProperty;

public class ScorebladRegelDataModel {
    private final SimpleStringProperty dubbeleScore;
    private final SimpleStringProperty tienPunten;
    private final SimpleStringProperty elfPunten;
    private final SimpleStringProperty twaalfPunten;
    private final SimpleStringProperty bonusPunten;
    private final SimpleStringProperty scoreVoorRegel;

    /**
     * UC4: constructor voor datamodel van TableView in SpelScoreBladPaneel
     *
     * @param dubbeleScore   dubbele score modifier
     * @param tienPunten     Aantal tien punten in regel
     * @param elfPunten      Aantal elf punten in regel
     * @param twaalfPunten   Aantal twaalf punten in regel
     * @param bonusPunten    Aantal bonus punten in regel
     * @param scoreVoorRegel Totaal punten in regel
     */

    public ScorebladRegelDataModel(String dubbeleScore, String tienPunten, String elfPunten, String twaalfPunten, String bonusPunten, String scoreVoorRegel) {
        this.dubbeleScore = new SimpleStringProperty(dubbeleScore);
        this.tienPunten = new SimpleStringProperty(tienPunten);
        this.elfPunten = new SimpleStringProperty(elfPunten);
        this.twaalfPunten = new SimpleStringProperty(twaalfPunten);
        this.bonusPunten = new SimpleStringProperty(bonusPunten);
        this.scoreVoorRegel = new SimpleStringProperty(scoreVoorRegel);
    }

    /**
     * UC4: onderstaande methodes worden impliciet gebruikt door de TableView als hulpmethodes.
     */

    public String getDubbeleScore() {
        return dubbeleScore.get();
    }

    public void setDubbeleScore(String dubbeleScore) {
        this.dubbeleScore.set(dubbeleScore);
    }

    public SimpleStringProperty dubbeleScoreProperty() {
        return dubbeleScore;
    }

    public String getTienPunten() {
        return tienPunten.get();
    }

    public void setTienPunten(String tienPunten) {
        this.tienPunten.set(tienPunten);
    }

    public SimpleStringProperty tienPuntenProperty() {
        return tienPunten;
    }

    public String getElfPunten() {
        return elfPunten.get();
    }

    public void setElfPunten(String elfPunten) {
        this.elfPunten.set(elfPunten);
    }

    public SimpleStringProperty elfPuntenProperty() {
        return elfPunten;
    }

    public String getTwaalfPunten() {
        return twaalfPunten.get();
    }

    public void setTwaalfPunten(String twaalfPunten) {
        this.twaalfPunten.set(twaalfPunten);
    }

    public SimpleStringProperty twaalfPuntenProperty() {
        return twaalfPunten;
    }

    public String getBonusPunten() {
        return bonusPunten.get();
    }

    public void setBonusPunten(String bonusPunten) {
        this.bonusPunten.set(bonusPunten);
    }

    public SimpleStringProperty bonusPuntenProperty() {
        return bonusPunten;
    }

    public String getScoreVoorRegel() {
        return scoreVoorRegel.get();
    }

    public void setScoreVoorRegel(String scoreVoorRegel) {
        this.scoreVoorRegel.set(scoreVoorRegel);
    }

    public SimpleStringProperty scoreVoorRegelProperty() {
        return scoreVoorRegel;
    }
}
