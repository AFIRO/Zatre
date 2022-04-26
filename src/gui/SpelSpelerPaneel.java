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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class SpelSpelerPaneel extends VBox {
	private final DomeinController domeinController;
	private final MenuPaneel menuPaneel;
	private final HoofdPaneel hoofdPaneel;
	private boolean eersteBeurt = true;
	private VBox steentjesBox;
	private Button btnVraagSteentjes;
	private SteenGUI geklikteSteen;
	private VakGUI gekliktVak;
	private Label lblGeselecteerdVak = new Label("Ik besta vak");
	private Label lblGeselecteerdeSteen = new Label("Ik besta steen");
	private Label lblFeedbackVoorSpelers = new Label("Ik besta feedback");


	public SpelSpelerPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
		this.hoofdPaneel = hoofdPaneel;
		this.domeinController = domeinController;
		this.menuPaneel = menuPaneel;
		voegComponentenToe();
	}

	private void voegComponentenToe() {

		this.setAlignment(Pos.TOP_CENTER);
		this.setMinWidth(300);
		this.setMinHeight(1000);
		VBox knoppenBox = new VBox();
		steentjesBox = new VBox();
		btnVraagSteentjes = new Button(domeinController.getTaal().getLocalisatie("VRAAG_STEENTJES"));
		Button btnCancelSpel = new Button(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));

		knoppenBox.getChildren().addAll(btnVraagSteentjes, btnCancelSpel);
		this.setSpacing(150);
		knoppenBox.setSpacing(20);
		steentjesBox.setSpacing(20);
		knoppenBox.setAlignment(Pos.TOP_CENTER);
		steentjesBox.setAlignment(Pos.BOTTOM_CENTER);

		btnVraagSteentjes.setOnAction(this::vraagSteentjes);
		btnVraagSteentjes.setPadding(new Insets(5, 5, 5, 5));
		btnVraagSteentjes.setLineSpacing(100);
		btnVraagSteentjes.setMaxWidth(150);
		btnVraagSteentjes.setAlignment(Pos.CENTER);
		btnVraagSteentjes.setStyle("-fx-background-color: #8DFC79;" + "-fx-border-color: #000000;"
				+ "-fx-border-width: 2px;" + "-fx-font-size: 1em");

		btnCancelSpel.setOnAction(this::cancelSpel);
		btnCancelSpel.setPadding(new Insets(5, 5, 5, 5));
		btnCancelSpel.setLineSpacing(100);
		btnCancelSpel.setMaxWidth(150);
		btnCancelSpel.setAlignment(Pos.CENTER);
		btnCancelSpel.setStyle("-fx-background-color: #8DFC79;" + "-fx-border-color: #000000;"
				+ "-fx-border-width: 2px;" + "-fx-font-size: 1em");

		knoppenBox.getChildren().addAll(lblGeselecteerdVak,lblGeselecteerdeSteen,lblFeedbackVoorSpelers);

		this.getChildren().addAll(knoppenBox, steentjesBox);
	}

	/**
	 * UC3: vraag steentjes op uit domein, bouwt deze als GUI element, voegt eventhandler toe en plaatst deze in GUI
	 */

	private void vraagSteentjes(ActionEvent actionEvent) {
		verwijderStenenUitComponent();
		var stenen = domeinController.vraagSteentjes(eersteBeurt);
		if (eersteBeurt)
			eersteBeurt = false;
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
	 * UC3: Doet een alert verschijnen voor bevestiging. Indien bevestigd, geeft info door aan domein voor verwerking.
	 */
	private void cancelSpel(ActionEvent actionEvent) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));
		alert.setHeaderText(domeinController.getTaal().getLocalisatie("SPEL_STOPPEN"));
		alert.setContentText(domeinController.getTaal().getLocalisatie("BEVESTIGING_SPEL_STOPPEN"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			domeinController.cancelSpel();
			menuPaneel.lblLoggedOn.setVisible(false);
			Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
			stage.setWidth(500);
			stage.setHeight(500);
			hoofdPaneel.setCenter(menuPaneel);
		}

	}

	/**
	 * UC4: eventHandler die identiteit van steen opslaat voor gebruik in zet.
	 * @param steen de betrokken steen
	 */

	private void steenClicked(SteenGUI steen) {
		this.geklikteSteen = steen;
		updateFeedbackLabel();

	}

	/**
	 * UC4: eventHandler die identiteit van vak opslaat voor gebruik in zet.
	 * @param gekliktVak de betrokken vak
	 */

	public void setGekliktVak(VakGUI gekliktVak) {
		this.gekliktVak = gekliktVak;
		updateFeedbackLabel();
	}

	/**
	 * UC4: update feedback labels zodat speler weet wat hij geklikt heeft.
	 */

	private void updateFeedbackLabel() {
		String vakTekst = "";
		String steenTekst = "";

		if (Objects.nonNull(geklikteSteen)){
			steenTekst = "Geselecteerde Steen: "+ geklikteSteen.getWaarde();
		}

		if (Objects.nonNull(gekliktVak)){
			vakTekst = "Geselecteerd Vak: "+ gekliktVak.getKolom() + "." + gekliktVak.getRij();
		}
		lblGeselecteerdVak.setText(vakTekst);
		lblGeselecteerdeSteen.setText(steenTekst);
		lblFeedbackVoorSpelers.setVisible(true);
	}
}