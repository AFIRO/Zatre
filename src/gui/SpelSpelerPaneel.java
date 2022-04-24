package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class SpelSpelerPaneel extends VBox {
	private final DomeinController domeinController;
	private final MenuPaneel menuPaneel;
	private final HoofdPaneel hoofdPaneel;
	private boolean eersteBeurt = true;
	VBox steentjesBox;

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
		Button btnVraagSteentjes = new Button(domeinController.getTaal().getLocalisatie("VRAAG_STEENTJES"));
		Button btnCancelSpel = new Button(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));

		knoppenBox.getChildren().addAll(btnVraagSteentjes, btnCancelSpel);
		this.setSpacing(150);
		knoppenBox.setSpacing(20);
		steentjesBox.setSpacing(20);
		knoppenBox.setAlignment(Pos.TOP_CENTER);
		steentjesBox.setAlignment(Pos.BOTTOM_CENTER);
		
		
			steentjesBox.setOnMouseClicked(this::klikOpSteen);
		

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

		this.getChildren().addAll(knoppenBox, steentjesBox);
	}
	
	
	private void klikOpSteen(MouseEvent event) {
		//wat moet er gebeuren wanneer de de steentjes aanklikken
	}

	private void vraagSteentjes(ActionEvent actionEvent) {
		verwijderStenenUitComponent();
		var stenen = domeinController.vraagSteentjes(eersteBeurt);
		if (eersteBeurt)
			eersteBeurt = false;
		for (int waarde : stenen) {
			steentjesBox.getChildren().addAll(new SteenGUI(waarde));
		}
	}

	private void verwijderStenenUitComponent() {
		this.getChildren().removeIf(node -> node instanceof SteenGUI);
	}

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
}