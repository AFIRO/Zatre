package gui;

import javafx.beans.property.SimpleStringProperty;

public class ScorebladRegelDataModel {
    private final SimpleStringProperty dubbeleScore;
    private final SimpleStringProperty tienPunten;
    private final SimpleStringProperty elfPunten;
    private final SimpleStringProperty twaalfPunten;
    private final SimpleStringProperty bonusPunten;
    private final SimpleStringProperty scoreVoorRegel;

    public ScorebladRegelDataModel(String dubbeleScore, String tienPunten, String elfPunten, String twaalfPunten, String bonusPunten, String scoreVoorRegel) {
        this.dubbeleScore = new SimpleStringProperty(dubbeleScore);
        this.tienPunten = new SimpleStringProperty(tienPunten);
        this.elfPunten = new SimpleStringProperty(elfPunten);
        this.twaalfPunten = new SimpleStringProperty(twaalfPunten);
        this.bonusPunten = new SimpleStringProperty(bonusPunten);
        this.scoreVoorRegel = new SimpleStringProperty(scoreVoorRegel);
    }

    //onderstaande getters en setters worden "under the hood" aangesproken door Tableview.
    //Niet wijzigen

    public String getDubbeleScore() {
        return dubbeleScore.get();
    }

    public SimpleStringProperty dubbeleScoreProperty() {
        return dubbeleScore;
    }

    public void setDubbeleScore(String dubbeleScore) {
        this.dubbeleScore.set(dubbeleScore);
    }

    public String getTienPunten() {
        return tienPunten.get();
    }

    public SimpleStringProperty tienPuntenProperty() {
        return tienPunten;
    }

    public void setTienPunten(String tienPunten) {
        this.tienPunten.set(tienPunten);
    }

    public String getElfPunten() {
        return elfPunten.get();
    }

    public SimpleStringProperty elfPuntenProperty() {
        return elfPunten;
    }

    public void setElfPunten(String elfPunten) {
        this.elfPunten.set(elfPunten);
    }

    public String getTwaalfPunten() {
        return twaalfPunten.get();
    }

    public SimpleStringProperty twaalfPuntenProperty() {
        return twaalfPunten;
    }

    public void setTwaalfPunten(String twaalfPunten) {
        this.twaalfPunten.set(twaalfPunten);
    }

    public String getBonusPunten() {
        return bonusPunten.get();
    }

    public SimpleStringProperty bonusPuntenProperty() {
        return bonusPunten;
    }

    public void setBonusPunten(String bonusPunten) {
        this.bonusPunten.set(bonusPunten);
    }

    public String getScoreVoorRegel() {
        return scoreVoorRegel.get();
    }

    public SimpleStringProperty scoreVoorRegelProperty() {
        return scoreVoorRegel;
    }

    public void setScoreVoorRegel(String scoreVoorRegel) {
        this.scoreVoorRegel.set(scoreVoorRegel);
    }
}
