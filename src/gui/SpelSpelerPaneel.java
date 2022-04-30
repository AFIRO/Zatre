package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpelSpelerPaneel extends VBox {
	private final DomeinController domeinController;
	private final MenuPaneel menuPaneel;
    private final HoofdPaneel hoofdPaneel;
    private final SpelScorebladPaneel spelScorebladPaneel;
    private final SpelPaneel spelPaneel;
    private boolean eersteBeurt = true;
    private boolean eersteSteen = true;
    private VBox steentjesBox;
    private Button btnVraagSteentjes;
    private Button btnZetSteenOpVakje;
    private SteenGUI geklikteSteen;
    private StackPane gekliktVak;
    private final Label lblGeselecteerdVak = new Label();
    private final Label lblGeselecteerdeSteen = new Label();
    private final Label lblFeedbackVoorSpelers = new Label();
    private List<String> zetten;

    /**
     * @param hoofdPaneel
     * @param menuPaneel
     * @param spelScorebladPaneel
     * @param domeinController
     * @param spelPaneel
     */
    public SpelSpelerPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, SpelScorebladPaneel spelScorebladPaneel, DomeinController domeinController, SpelPaneel spelPaneel) {
        this.hoofdPaneel = hoofdPaneel;
        this.domeinController = domeinController;
        this.menuPaneel = menuPaneel;
        this.spelScorebladPaneel = spelScorebladPaneel;
        this.spelPaneel = spelPaneel;
        zetten = new ArrayList<>();
        voegComponentenToe();
    }

    /**
     *
     */
    private void voegComponentenToe() {

        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(300);
        this.setMinHeight(1000);
        VBox knoppenBox = new VBox();
        steentjesBox = new VBox();
        btnVraagSteentjes = new Button(domeinController.getTaal().getLocalisatie("VRAAG_STEENTJES"));
        Button btnCancelSpel = new Button(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));
        btnZetSteenOpVakje = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
        knoppenBox.getChildren().addAll(btnVraagSteentjes, btnZetSteenOpVakje, btnCancelSpel);
        this.setSpacing(150);
        knoppenBox.setSpacing(20);
        steentjesBox.setSpacing(20);
        knoppenBox.setAlignment(Pos.TOP_CENTER);
        steentjesBox.setAlignment(Pos.BOTTOM_CENTER);

        btnZetSteenOpVakje.setDisable(true);

        btnVraagSteentjes.setOnAction(this::vraagSteentjes);
        btnCancelSpel.setOnAction(this::cancelSpel);
        btnZetSteenOpVakje.setOnAction(this::zetSteenOpVakje);
        zetCSSVanKnopGoed(btnVraagSteentjes);
        zetCSSVanKnopGoed(btnCancelSpel);
        zetCSSVanKnopGoed(btnZetSteenOpVakje);

        knoppenBox.getChildren().addAll(lblGeselecteerdVak, lblGeselecteerdeSteen, lblFeedbackVoorSpelers);

        this.getChildren().addAll(knoppenBox, steentjesBox);
    }

    /**
     * UC3: zet de CSS van de doorgegeven knop goed.
     *
     * @param knop de knop
     */
    private void zetCSSVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(5, 5, 5, 5));
        knop.setLineSpacing(100);
        knop.setMaxWidth(150);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #8DFC79;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 1em");
    }

    /**
     * UC3: vraag steentjes op uit domein, bouwt deze als GUI element, voegt
     * eventhandler toe en plaatst deze in GUI
     */

    private void vraagSteentjes(ActionEvent actionEvent) {
        verwijderStenenUitComponent();
        var stenen = domeinController.vraagSteentjes(eersteBeurt);
        for (int waarde : stenen) {
            SteenGUI steen = new SteenGUI(waarde);
            steen.setOnMousePressed(event -> steenClicked(steen));
            steentjesBox.getChildren().addAll(steen);
        }
        this.btnVraagSteentjes.setDisable(true);
    }

    /**
     * UC3: verwijdert de stenen uit de GUI. Hulpmethode voor andere methodes.
     */
    private void verwijderStenenUitComponent() {
        this.getChildren().removeIf(node -> node instanceof SteenGUI);
    }

    /**
     * UC3: Doet een alert verschijnen voor bevestiging. Indien bevestigd, geeft
     * info door aan domein voor verwerking.
     */
    private void cancelSpel(ActionEvent actionEvent) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));
        alert.setHeaderText(domeinController.getTaal().getLocalisatie("SPEL_STOPPEN"));
        alert.setContentText(domeinController.getTaal().getLocalisatie("BEVESTIGING_SPEL_STOPPEN"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            domeinController.cancelSpel();
            menuPaneel.getLblLoggedOn().setVisible(false);
            Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
            stage.setWidth(500);
            stage.setHeight(500);
            this.menuPaneel.setSpelPaneel(new SpelPaneel(this.hoofdPaneel, this.menuPaneel, this.domeinController));
            hoofdPaneel.setCenter(menuPaneel);
        }

    }

    /**
     * UC4: eventHandler die identiteit van steen opslaat voor gebruik in zet.
     *
     * @param steen de betrokken steen
     */

    private void steenClicked(SteenGUI steen) {
        this.geklikteSteen = steen;
        updateFeedbackLabel();

    }

    /**
     * UC4: eventHandler die identiteit van vak opslaat voor gebruik in zet.
     *
     * @param gekliktVak de betrokken vak
     */

    public void setGekliktVak(StackPane gekliktVak) {
        this.gekliktVak = gekliktVak;
        updateFeedbackLabel();
    }

    /**
     * UC4: update feedback labels zodat speler weet wat hij geklikt heeft.
     */

    private void updateFeedbackLabel() {
        String vakTekst = "";
        String steenTekst = "";

        if (Objects.nonNull(geklikteSteen)) {
            steenTekst = domeinController.getTaal().getLocalisatie("GESELECTEERDE_STEEN") + geklikteSteen.getWaarde();
        }

        if (Objects.nonNull(gekliktVak)) {
            vakTekst = domeinController.getTaal().getLocalisatie("GESELECTEERD_VAKJE")
                    + ((VakGUI) gekliktVak.getChildren().get(0)).getKolom() + "."
                    + ((VakGUI) gekliktVak.getChildren().get(0)).getRij();
        }

        if (Objects.nonNull(gekliktVak) & Objects.nonNull(geklikteSteen)) {
            btnZetSteenOpVakje.setDisable(false);
        }
        lblGeselecteerdVak.setText(vakTekst);
        lblGeselecteerdeSteen.setText(steenTekst);
        lblFeedbackVoorSpelers.setVisible(true);
    }

    	/**
 	    * @param actionEvent
    	 */
		private void zetSteenOpVakje(ActionEvent actionEvent) {
			lblFeedbackVoorSpelers.setVisible(false);
        if (Objects.isNull(gekliktVak) || Objects.isNull(geklikteSteen)) {
            lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("SELECTEER_STEEN_EN_VAK"));
            lblFeedbackVoorSpelers.setVisible(true);
        } else {
			if (domeinController.checkOfZetLegaalIsTussenTijdseValidatie(eersteSteen, ((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten(),  String.valueOf(geklikteSteen.getWaarde()))){
                ((Text) gekliktVak.getChildren().get(1)).setText(String.valueOf(geklikteSteen.getWaarde()));
                ((ImageView) gekliktVak.getChildren().get(2)).setImage(geklikteSteen.getImage());
                steentjesBox.getChildren().remove(geklikteSteen);
                zetten.add(((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten() + " " + geklikteSteen.getWaarde());
                btnZetSteenOpVakje.setDisable(true);
                eersteSteen = false;
				geklikteSteen = null;
				gekliktVak = null;
				updateFeedbackLabel();
            } else {
                lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("ONGELDIGE_ZET"));
                lblFeedbackVoorSpelers.setVisible(true);
                geklikteSteen = null;
				gekliktVak = null;
				updateFeedbackLabel();
            }

            if (steentjesBox.getChildren().isEmpty()) {
                if (domeinController.checkOfZettenLegaalZijnEindValidatie(zetten)) {
                    domeinController.speelBeurt(domeinController.geefActieveSpeler().get(0), domeinController.geefActieveSpeler().get(1), zetten);
                    eersteBeurt = false;

                    if (domeinController.isEindeSpel()) {
                        eindigSpel();
                    } else {
                        volgendeSpeler();
                    }
                } else {
                    lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("ONGELDIGE_ZET"));
                    lblFeedbackVoorSpelers.setVisible(true);
                    resetSpelBordNaOngeldigeZet(zetten);
                    zetten = new ArrayList<>();
                    if (eersteBeurt)
                        eersteSteen = true;
                }
            }
        }

    }


    private void resetSpelBordNaOngeldigeZet(List<String> zetten) {
        for (String zet : zetten) {
            SteenGUI gespeeldeSteen = new SteenGUI(Integer.parseInt(zet.substring(4)));
            gespeeldeSteen.setOnMousePressed(event -> steenClicked(gespeeldeSteen));
            StackPane gespeeldeVak = (StackPane) spelPaneel.getSpelBordPaneel().getChildren().stream()
                    .filter(((vak) -> ((VakGUI) ((StackPane) vak).getChildren().get(0)).getCoordinaten().equals(zet.substring(0, 3))))
                    .findFirst().get(); //optional check onnodig. We weten dat vak moet bestaan als spelbord bestaat.
            steentjesBox.getChildren().add(gespeeldeSteen);
            ((ImageView) gespeeldeVak.getChildren().get(2)).setImage(null);
            ((Text) gespeeldeVak.getChildren().get(1)).setText("");
        }
    }

    private void eindigSpel() {
        List<String> laatsteScorebladOmTeTonen = domeinController.eindigSpel();
        Alert alert = new Alert(AlertType.INFORMATION, laatsteScorebladOmTeTonen.get(0));
        laatsteScorebladOmTeTonen.remove(0);
        alert.setContentText(laatsteScorebladOmTeTonen.stream().collect(Collectors.joining("/n")));
    }


    /**
     * Gui:
     */
    private void volgendeSpeler() {
        zetten = new ArrayList<>();
        domeinController.volgendeSpeler();
        spelScorebladPaneel.updateInfo(0);
        this.btnVraagSteentjes.setDisable(false);
    }
}