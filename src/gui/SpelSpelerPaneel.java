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
import javafx.scene.layout.BorderPane;
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
	private Button btnGeefSteentjeTerug;
	private SteenGUI geklikteSteen;
	private StackPane gekliktVak;
	private BorderPane randSteen;
	private final Label lblGeselecteerdVak = new Label();
	private final Label lblGeselecteerdeSteen = new Label();
	private final Label lblFeedbackVoorSpelers = new Label();
	private List<String> zetten;

	/**
	 * UC3: constructor voor paneel
	 *
	 * @param hoofdPaneel         om hoofdscherm aan te passen
	 * @param menuPaneel          voor terug naar menu na einde spel
	 * @param spelScorebladPaneel voor aanpassing scoreblad viwe
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

		// eventhandlers
		btnVraagSteentjes.setOnAction(this::vraagSteentjes);
		btnCancelSpel.setOnAction(this::cancelSpel);
		btnZetSteenOpVakje.setOnAction(this::zetSteenOpVakje);
		btnGeefSteentjeTerug.setOnAction(this::geefSteenTerug);

		// styling
		zetCSSVanKnopGoed(btnVraagSteentjes);
		zetCSSVanKnopGoed(btnCancelSpel);
		zetCSSVanKnopGoed(btnZetSteenOpVakje);
		zetCSSVanKnopGoed(btnGeefSteentjeTerug);
		btnCancelSpel
				.setStyle("-fx-background-color: #E80C58;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
						+ "-fx-font-size: 1em;" + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;"
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
        knoppenBox.getChildren().addAll(btnVraagSteentjes, btnZetSteenOpVakje, btnGeefSteentjeTerug, btnCancelSpel);
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
		knop.setStyle("-fx-background-color: #8DFC79;" + "-fx-border-color: #000000;" + "-fx-border-width: 2px;"
				+ "-fx-font-size: 1em;" + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;"
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
			randSteen = new BorderPane(steen);
			randSteen.setPrefHeight(steen.getImage().getHeight() * 0.5);
			randSteen.setPrefWidth(randSteen.getHeight());
			randSteen.setStyle("-fx-border-color: #000000;" + "-fx-border-width: 2px;" + "-fx-border-radius: 50px;");

			randSteen.setOnMousePressed(event -> steenClicked(steen));
			steentjesBox.getChildren().addAll(randSteen);
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
			if (steentjesBox.getChildren().isEmpty()) {
				volgendeSpeler();
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
		if(this.geklikteSteen != null)
			zetCSSSteenNormaal();
		this.geklikteSteen = steen;
		zetCSSGeklikteSteen();
		if (!eersteBeurt) {
			btnGeefSteentjeTerug.setDisable(false);
		}
		updateFeedbackLabel();

	}

	/**
	 * UC4: eventHandler die identiteit van vak opslaat voor gebruik in zet.
	 *
	 * @param gekliktVak de betrokken vak
	 */

	private void zetCSSVakNormaal() {
		this.gekliktVak.setStyle("-fx-border-color: #000000;" + "-fx-border-width: 2px;");

	}
	private void zetCSSSteenNormaal() {
		randSteen.setStyle("-fx-border-color: #000000;" + "-fx-border-width: 2px;" + "-fx-border-radius: 50px;");
	}

	private void zetCSSGekliktVak() {
		this.gekliktVak.setStyle("-fx-border-color: #E80C58;" + "-fx-border-width: 2px;");

	}
	private void zetCSSGeklikteSteen() {
		randSteen.setStyle("-fx-border-color:  #E80C58;" + "-fx-border-width: 2px;" + "-fx-border-radius: 50px;");
	}

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
	 * 
	 * @param actionEvent
	 */
	private void zetSteenOpVakje(ActionEvent actionEvent) {
		if (this.gekliktVak != null)
			zetCSSVakNormaal();

		lblFeedbackVoorSpelers.setVisible(false);
		if (Objects.isNull(gekliktVak) || Objects.isNull(geklikteSteen)) {
			lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("SELECTEER_STEEN_EN_VAK"));
			lblFeedbackVoorSpelers.setVisible(true);
		} else {
			if (domeinController.checkOfZetLegaalIsTussenTijdseValidatie(eersteSteen,
					((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten(),
					String.valueOf(geklikteSteen.getWaarde()))) {
				((Text) gekliktVak.getChildren().get(1)).setText(String.valueOf(geklikteSteen.getWaarde()));
				((ImageView) gekliktVak.getChildren().get(2)).setImage(geklikteSteen.getImage());
				steentjesBox.getChildren().remove(geklikteSteen);
				zetten.add(
						geklikteSteen.getWaarde() + " " + ((VakGUI) gekliktVak.getChildren().get(0)).getCoordinaten());
				btnZetSteenOpVakje.setDisable(true);
				btnGeefSteentjeTerug.setDisable(true);
				eersteSteen = false;
				geklikteSteen = null;
				gekliktVak = null;
				updateFeedbackLabel();
			} else {
				lblFeedbackVoorSpelers.setText(domeinController.getTaal().getLocalisatie("ONGELDIGE_ZET"));
				lblFeedbackVoorSpelers.setVisible(true);
				geklikteSteen = null;
				gekliktVak = null;
				btnGeefSteentjeTerug.setDisable(true);
				updateFeedbackLabel();
			}

			if (steentjesBox.getChildren().isEmpty()) {
				if (domeinController.checkOfZettenLegaalZijnEindValidatie(zetten)) {
					domeinController.speelBeurt(domeinController.geefActieveSpeler().get(0),
							domeinController.geefActieveSpeler().get(1), zetten);
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

	/**
	 * UC4: zet het bord en de steendoos terug naar zijn status van op start van de
	 * beurt op basis van de doorgeven zetten die gebruikt werden voor validatie (en
	 * ongeldig werden verklaard door het domein)
	 * 
	 * @param zetten de ongeldige zetten
	 */

	private void resetSpelBordNaOngeldigeZet(List<String> zetten) {
		for (String zet : zetten) {
			SteenGUI gespeeldeSteen = new SteenGUI(Integer.parseInt(zet.substring(0, 1)));
			gespeeldeSteen.setOnMousePressed(event -> steenClicked(gespeeldeSteen));
			StackPane gespeeldeVak = (StackPane) spelPaneel.getSpelBordPaneel().getChildren().stream()
					.filter(((vak) -> ((VakGUI) ((StackPane) vak).getChildren().get(0)).getCoordinaten()
							.equals(zet.substring(2))))
					.findFirst().get(); // optional check onnodig. We weten dat vak moet bestaan als spelbord bestaat.
			steentjesBox.getChildren().add(gespeeldeSteen);
			((ImageView) gespeeldeVak.getChildren().get(2)).setImage(null);
			((Text) gespeeldeVak.getChildren().get(1)).setText("");
		}
	}

	/**
	 * UC3: methode die bij detectie van het einde van het spel de procedure start.
	 * Haalt de scoreblad per speler en de winnaar op. Domein regelt verdere
	 * afhandeling achter de schermen. Het roept ook een methode op die de schermen
	 * reset voor het volgend spel.
	 *
	 */

	private void eindigSpel() {
		List<String> laatsteScorebladOmTeTonen = domeinController.eindigSpel();
		Alert alert = new Alert(AlertType.INFORMATION, laatsteScorebladOmTeTonen.get(0));
		laatsteScorebladOmTeTonen.remove(0);
		alert.setContentText(laatsteScorebladOmTeTonen.stream().collect(Collectors.joining("/n")));
		resetSchermenVoorVolgendSpel();
	}

	/**
	 * UC3: methode die de schermen reset voor volgend spel na eindigen spel of
	 * cancel.
	 *
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
	 *
	 */
	private void volgendeSpeler() {
		zetten = new ArrayList<>();
		this.btnVraagSteentjes.setDisable(false);
		domeinController.volgendeSpeler();
		spelScorebladPaneel.updateInfo();
	}
}