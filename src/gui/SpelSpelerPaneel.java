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
    private final Label lblGeselecteerdVak = new Label();
    private final Label lblGeselecteerdeSteen = new Label();
    private final Label lblFeedbackVoorSpelers = new Label();
    private boolean eersteBeurt = true;
    private boolean eersteSteen = true;
    private VBox steentjesBox;
    private Button btnVraagSteentjes;
    private Button btnZetSteenOpVakje;
    private Button btnGeefSteentjeTerug;
    private SteenGUI geklikteSteen;
    private StackPane gekliktVak;
    private List<String> zetten;

    /**
     * UC3: constructor voor paneel
     *
     * @param hoofdPaneel         om hoofdscherm aan te passen
     * @param menuPaneel          voor terug naar menu na einde spel
     * @param spelScorebladPaneel voor aanpassing scoreblad view
     * @param domeinController    voor bevraging domein
     * @param spelPaneel          voor methodes na einde spel
     */
    public SpelSpelerPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, SpelScorebladPaneel spelScorebladPaneel,
                            DomeinController domeinController, SpelPaneel spelPaneel) {
        this.hoofdPaneel = hoofdPaneel;

        this.domeinController = domeinController;
        this.menuPaneel = menuPaneel;
        this.spelScorebladPaneel = spelScorebladPaneel;
        this.spelPaneel = spelPaneel;
        zetten = new ArrayList<>();
        voegComponentenToe();
    }

    /**
     * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     */
    private void voegComponentenToe() {
        // instantie elementen
        VBox knoppenBox = new VBox();
        VBox labelbox = new VBox();
        steentjesBox = new VBox();
        btnVraagSteentjes = new Button(domeinController.getTaal().getLocalisatie("VRAAG_STEENTJES"));
        Button btnCancelSpel = new Button(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));
        btnZetSteenOpVakje = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
        btnGeefSteentjeTerug = new Button(domeinController.getTaal().getLocalisatie("STEEN_TERUG"));
        Button btnEindigSpelVoorDemo = new Button(domeinController.getTaal().getLocalisatie("EINDIG_SPEL_VOOR_DEMO"));

        // eventhandlers
        btnVraagSteentjes.setOnAction(this::vraagSteentjes);
        btnCancelSpel.setOnAction(this::cancelSpel);
        btnZetSteenOpVakje.setOnAction(this::zetSteenOpVakje);
        btnGeefSteentjeTerug.setOnAction(this::geefSteenTerug);
        btnEindigSpelVoorDemo.setOnAction(this::eindigSpelVoorDemo);

        // styling
        zetCSSVanKnopGoed(btnVraagSteentjes);
        zetCSSVanKnopGoed(btnCancelSpel);
        zetCSSVanKnopGoed(btnZetSteenOpVakje);
        zetCSSVanKnopGoed(btnGeefSteentjeTerug);
        zetCSSVanKnopGoed(btnEindigSpelVoorDemo);
        btnCancelSpel
                .setStyle("-fx-background-color: #E80C58;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                        + "-fx-font-size: 1em;" + "-fx-text-fill: #ffffff;" + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;"
                        + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(300);
        this.setMinHeight(1000);
        this.setSpacing(50);
        btnZetSteenOpVakje.setDisable(true);
        btnGeefSteentjeTerug.setDisable(true);
        knoppenBox.setSpacing(20);
        knoppenBox.setAlignment(Pos.TOP_CENTER);
        labelbox.setAlignment(Pos.CENTER);
        steentjesBox.setSpacing(20);
        steentjesBox.setAlignment(Pos.BOTTOM_CENTER);

        //insert in GUI
        labelbox.getChildren().addAll(lblGeselecteerdVak, lblGeselecteerdeSteen, lblFeedbackVoorSpelers);
        this.getChildren().addAll(knoppenBox, labelbox, steentjesBox);
        knoppenBox.getChildren().addAll(btnVraagSteentjes, btnZetSteenOpVakje, btnGeefSteentjeTerug, btnCancelSpel, btnEindigSpelVoorDemo);
    }


    /**
     * UC3: zet de CSS van de doorgegeven knop goed.
     *
     * @param knop de knop
     */
    private void zetCSSVanKnopGoed(Button knop) {
        knop.setPadding(new Insets(5, 5, 5, 5));
        knop.setLineSpacing(100);
        knop.setMaxWidth(175);
        knop.setAlignment(Pos.CENTER);
        knop.setStyle("-fx-background-color: #020470;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
                + "-fx-font-size: 1em;" + "-fx-text-fill: #ffffff;" + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
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
            resetSchermenVoorVolgendSpel();
        }

    }

    /**
     * UC4: Methode om een steentje terug in het zakje te plaatsen
     */

    private void geefSteenTerug(ActionEvent actionEvent) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(domeinController.getTaal().getLocalisatie("STEEN_TERUG"));
        alert.setHeaderText(domeinController.getTaal().getLocalisatie("STEEN_TERUG_PROMPT"));
        alert.setContentText(domeinController.getTaal().getLocalisatie("STEEN_TERUG_BVESTIGING"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            steentjesBox.getChildren().remove(geklikteSteen);
            domeinController.steekSteentjeTerugInZak(geklikteSteen.getWaarde());
            geklikteSteen = null;
            updateFeedbackLabel();
            btnGeefSteentjeTerug.setDisable(true);
            //flow einde beurt
            if (steentjesBox.getChildren().isEmpty()) {
                //finale check voor legaliteit van de zetten van deze beurt op basis van zet array in domein
                if (domeinController.checkOfZettenLegaalZijnEindValidatie(zetten)) {
                    //info wordt doorgegeven aan domein om beurt te spelen
                    domeinController.speelBeurt(domeinController.geefActieveSpeler().get(0),
                            domeinController.geefActieveSpeler().get(1), zetten);
                    //zet boolean voor eerste beurt (met speciale regels) op false.
                    eersteBeurt = false;
                    //indien domein aangeeft dat er geen steentjes meer in zakje zijn na beurt
                    if (domeinController.isEindeSpel()) {
                        //start eind spel flow
                        eindigSpel();
                        //indien dit niet zo is
                    } else {
                        //ga naar volgende beurt
                        volgendeSpeler();
                        //reset feedback label
                        lblFeedbackVoorSpelers.setText("");
                    }
                }
            }
        }
    }

    /**
     * UC4: eventHandler die identiteit van steen opslaat voor gebruik in zet. Het
     * activeert ook de knop om een steen terug te geven bij de eerste beurt.
     *
     * @param steen de betrokken steen
     */

    private void steenClicked(SteenGUI steen) {
        this.geklikteSteen = steen;

        if (!eersteBeurt) {
            btnGeefSteentjeTerug.setDisable(false);
        }
        updateFeedbackLabel();

    }

    /**
     * UC4: zet de styling van een vakje terug naar de normale styling
     */

    private void zetCSSVakNormaal() {
        this.gekliktVak.setStyle("-fx-border-color: #000000;" + "-fx-border-width: 2px;");

    }

    /**
     * UC4: zet de styling van een vakje naar een andere kleur border om aan te geven als het geselecteerd is
     */

    private void zetCSSGekliktVak() {
        this.gekliktVak.setStyle("-fx-border-color: #E80C58;" + "-fx-border-width: 2px;");

    }

    /**
     * UC4: eventHandler die identiteit van vak opslaat voor gebruik in zet.
     *
     * @param gekliktVak de betrokken vak
     */

    public void setGekliktVak(StackPane gekliktVak) {
        if (this.gekliktVak != null)
            zetCSSVakNormaal();
        this.gekliktVak = gekliktVak;
        zetCSSGekliktVak();
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
     * UC4: eventhandler voor een beurt te spelen
     */
    private void zetSteenOpVakje(ActionEvent actionEvent) {
        if (this.gekliktVak != null)
            zetCSSVakNormaal();
        lblFeedbackVoorSpelers.setVisible(false);
        //indien geklikt vak of steen null, toon gepaste feedback
        if (Objects.isNull(gekliktVak) || Objects.isNull(geklikteSteen)) {
            lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("SELECTEER_STEEN_EN_VAK"));
            lblFeedbackVoorSpelers.setVisible(true);
        } else {
            //indien de zet langs de tussentijdse validatie geraakt (geeft zet door naar domein)
            if (domeinController.checkOfZetLegaalIsTussenTijdseValidatie(eersteSteen,
                    ((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten(),
                    String.valueOf(geklikteSteen.getWaarde()))) {
                //zet representatie van steen op het vakje
                ((Text) gekliktVak.getChildren().get(1)).setText(String.valueOf(geklikteSteen.getWaarde()));
                ((ImageView) gekliktVak.getChildren().get(2)).setImage(geklikteSteen.getImage());
                //haal gebruikte steen uit GUI
                steentjesBox.getChildren().remove(geklikteSteen);
                //voeg zet toe aan array van zetten voor verdere validatie in correcte vorm
                zetten.add(
                        geklikteSteen.getWaarde() + " " + ((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten());
                //disable de zet knop gezien zet net gebeurd is
                btnZetSteenOpVakje.setDisable(true);
                //disable geef steen terug knop gezien steen net gebruikt werd
                btnGeefSteentjeTerug.setDisable(true);
                //zet boolean voor speciale validatie regels voor eerste steen moet op 8.8 uit
                eersteSteen = false;
                //zet waarden van steen en vakje weer op null
                geklikteSteen = null;
                gekliktVak = null;
                //update feedback label met info om nieuwe staat te reflecteren
                updateFeedbackLabel();
                //indien zet niet langs tussentijdse validate geraakte
            } else {
                //geef correcte feedback
                lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("ONGELDIGE_ZET"));
                lblFeedbackVoorSpelers.setVisible(true);
                //zet waarden van steen en vakje weer op null
                geklikteSteen = null;
                gekliktVak = null;
                //disable knop gezien steen nu null is
                btnGeefSteentjeTerug.setDisable(true);
                //update feedback label met info om nieuwe staat te reflecteren
                updateFeedbackLabel();
            }
            //check of alle stenen van deze beurt gebruikt zijn
            if (steentjesBox.getChildren().isEmpty()) {
                //finale check voor legaliteit van de zetten van deze beurt op basis van zet array in domein
                if (domeinController.checkOfZettenLegaalZijnEindValidatie(zetten)) {
                    //info wordt doorgegeven aan domein om beurt te spelen
                    domeinController.speelBeurt(domeinController.geefActieveSpeler().get(0),
                            domeinController.geefActieveSpeler().get(1), zetten);
                    //zet boolean voor eerste beurt (met speciale regels) op false.
                    eersteBeurt = false;
                    //indien domein aangeeft dat er geen steentjes meer in zakje zijn na beurt
                    if (domeinController.isEindeSpel()) {
                        //start eind spel flow
                        eindigSpel();
                        //indien dit niet zo is
                    } else {
                        //ga naar volgende beurt
                        volgendeSpeler();
                        //reset feedback label
                        lblFeedbackVoorSpelers.setText("");
                    }
                    //indien finale check legaliteit zetten faalt
                } else {
                    //geef feedback aan speler
                    lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("ONGELDIGE_ZET"));
                    lblFeedbackVoorSpelers.setVisible(true);
                    //gebruik de zetten lijst om spel terug te zetten naar start van beurt
                    resetSpelBordNaOngeldigeZet(zetten);
                    //reset zetten lijst
                    zetten = new ArrayList<>();
                    //reset eerste steen boolean bij gefaalde eerste beurt
                    if (eersteBeurt)
                        eersteSteen = true;
                }
            }
        }

    }

    /**
     * UC4: zet het bord en de steendoos terug naar zijn status van op start van de
     * beurt op basis van de doorgeven zetten die gebruikt werden voor validatie (en
     * ongeldig werden verklaard door het domein)
     *
     * @param zetten de ongeldige zetten
     */

    private void resetSpelBordNaOngeldigeZet(List<String> zetten) {
        //voor elke zet
        for (String zet : zetten) {
            //maak steen op basis van info in zet met nodige event handler
            SteenGUI gespeeldeSteen = new SteenGUI(Integer.parseInt(zet.substring(0, 1)));
            gespeeldeSteen.setOnMousePressed(event -> steenClicked(gespeeldeSteen));
            //haal uit het spelbord het vak gebruikt in de zet
            StackPane gespeeldeVak = (StackPane) spelPaneel.getSpelBordPaneel().getChildren().stream()
                    .filter(((vak) -> ((VakGUI) ((StackPane) vak).getChildren().get(0)).getCoordinaten()
                            .equals(zet.substring(2))))
                    .findFirst().get(); // optional check onnodig. We weten dat vak moet bestaan als spelbord bestaat.
            //voeg gemaakte steen terug toe aan GUI
            steentjesBox.getChildren().add(gespeeldeSteen);
            //reset het vakje
            ((ImageView) gespeeldeVak.getChildren().get(2)).setImage(null);
            ((Text) gespeeldeVak.getChildren().get(1)).setText("");
        }
    }

    /**
     * UC3: methode die bij detectie van het einde van het spel de procedure start.
     * Haalt de scoreblad per speler en de winnaar op. Domein regelt verdere
     * afhandeling achter de schermen. Het roept ook een methode op die de schermen
     * reset voor het volgend spel.
     */

    private void eindigSpel() {
        //haal winnaar en scorebladen uit GUI
        List<String> laatsteScorebladOmTeTonen = domeinController.eindigSpel();
        //maak alert
        Alert alert = new Alert(AlertType.INFORMATION);
        //zet winnaar info als header
        alert.setHeaderText(laatsteScorebladOmTeTonen.get(0));
        //haal uit array want onnodig
        laatsteScorebladOmTeTonen.remove(0);
        //join de strings van alle scorebladen samen
        alert.setContentText(laatsteScorebladOmTeTonen.stream().collect(Collectors.joining("\n")));
        alert.showAndWait();
        resetSchermenVoorVolgendSpel();
    }

    /**
     * Demo: eventHandler die het spel onmiddellijk eindigt. Moest een aparte methode zijn voor knop gezien eindigSpel()
     * enkel en alleen zal activeren als er aangegeven wordt dat er geen steentjes meer zijn in het zak in de domeinlaag.
     */

    private void eindigSpelVoorDemo(ActionEvent actionEvent) {
        eindigSpel();
    }

    /**
     * UC3: methode die de schermen reset voor volgend spel na eindigen spel of
     * cancel.
     */

    private void resetSchermenVoorVolgendSpel() {
        menuPaneel.getLblLoggedOn().setVisible(false);
        Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
        stage.setWidth(500);
        stage.setHeight(500);
        this.menuPaneel.setSpelPaneel(new SpelPaneel(this.hoofdPaneel, this.menuPaneel, this.domeinController));
        hoofdPaneel.setCenter(menuPaneel);
    }

    /**
     * UC3: advanceert spelstaat naar volgende beurt. Reset de huidige zetten array,
     * zegt tegen DC om volgende speler te selecteren en update de info op
     * scorebladscherm.
     */
    private void volgendeSpeler() {
        zetten = new ArrayList<>();
        this.btnVraagSteentjes.setDisable(false);
        domeinController.volgendeSpeler();
        spelScorebladPaneel.updateInfo();
    }
}