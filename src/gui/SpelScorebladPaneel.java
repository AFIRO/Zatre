package gui;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SpelScorebladPaneel extends VBox {

    private final DomeinController domeinController;
    private Label lblTitel;
    private Label lblActieveSpeler;
    private TableView scoreTable;
    private ObservableList<ScorebladRegelDataModel> scorebladRegels;

    /**
     * UC3: constructor voor paneel
     *
     * @param domeinController de dc voor gebruik
     */

    public SpelScorebladPaneel(DomeinController domeinController) {
        this.domeinController = domeinController;
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     * <p>
     * Hulpmethode voor constructor
     */

    private void voegComponentenToe() {
        // instantie elementen
        lblTitel = new Label(domeinController.getTaal().getLocalisatie("HUIDIGE_SPELER"));
        lblActieveSpeler = new Label();
        scoreTable = new TableView<>();
        zetTabelGoed();

        // styling
        lblTitel.setStyle("-fx-font-size: 2em");
        lblActieveSpeler.setStyle("-fx-font-size: 1.5em");
        this.setPrefHeight(1000);
        this.setPrefWidth(350);
        this.setAlignment(Pos.TOP_LEFT);
        scoreTable.setMaxWidth(320);
        scoreTable.setMinHeight(500);
        scoreTable.setEditable(false);

        // insert in GUI
        this.getChildren().addAll(lblTitel, lblActieveSpeler, scoreTable);

    }

    /**
     * UC3: zet de tabel goed qua structuur
     * <p>
     * Hulpmethode voor constructor
     */

    private void zetTabelGoed() {
        TableColumn bonusScoreModifierKolom = maakKolomMetDoorgegevenNaamEnBinding("DT", "dubbeleScore");
        TableColumn tienPuntenKolom = maakKolomMetDoorgegevenNaamEnBinding("10", "tienPunten");
        TableColumn elfpuntenKolom = maakKolomMetDoorgegevenNaamEnBinding("11", "elfPunten");
        TableColumn twaalfpuntenKolom = maakKolomMetDoorgegevenNaamEnBinding("12", "twaalfPunten");
        TableColumn bonusPuntenKolom = maakKolomMetDoorgegevenNaamEnBinding("Bonus", "bonusPunten");
        TableColumn totaalKolom = maakKolomMetDoorgegevenNaamEnBinding(domeinController.getTaal().getLocalisatie("TOTAAL"), "scoreVoorRegel");
        scoreTable.getColumns().addAll(bonusScoreModifierKolom, tienPuntenKolom, elfpuntenKolom, twaalfpuntenKolom,
                bonusPuntenKolom, totaalKolom);
        scoreTable.setItems(FXCollections
                .observableArrayList(new ArrayList<>(List.of(new ScorebladRegelDataModel("", "", "", "", "", "")))));
    }

    /**
     * UC3: zet de kolommen goed qua structuur
     * <p>
     * Hulpmethode voor constructor
     */

    private TableColumn maakKolomMetDoorgegevenNaamEnBinding(String tabelNaam, String tabelBinding) {
        TableColumn kolom = new TableColumn(tabelNaam);
        kolom.setCellValueFactory(new PropertyValueFactory<ScorebladRegelDataModel, String>(tabelBinding));
        kolom.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.16));
        kolom.setResizable(false);
        return kolom;
    }

    /**
     * UC4: eventhandler voor update van info in dit scherm
     */

    public void updateInfo() {
        this.lblActieveSpeler.setText(domeinController.geefActieveSpeler().get(0));
        vulTabel();
    }

    /**
     * UC4: vul tabel met data na elke beurt
     * <p>
     * Hulpmethode info updaten
     */

    private void vulTabel() {
        ArrayList<ScorebladRegelDataModel> data = new ArrayList<>();

        if (domeinController.geefScoreBladVanActieveSpeler().isEmpty()) {
            data = new ArrayList<>(List.of(new ScorebladRegelDataModel("", "", "", "", "", "")));
        }

        for (String scorebladRegelString : domeinController.geefScoreBladVanActieveSpeler()) {
            String[] score = scorebladRegelString.split(",");
            ScorebladRegelDataModel scorebladRegel = new ScorebladRegelDataModel(score[0], score[1], score[2], score[3],
                    score[4], score[5]);
            data.add(scorebladRegel);
        }

        scorebladRegels = FXCollections.observableArrayList(data);
        scoreTable.setItems(scorebladRegels);

    }
}
