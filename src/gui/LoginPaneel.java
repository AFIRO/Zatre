package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

public class LoginPaneel extends VBox {

	private final HoofdPaneel hoofdPaneel;
	private final MenuPaneel menuPaneel;
	private final DomeinController domeinController;
	private TextField TxtNaam;
	private TextField TxtGeboortejaar;
	private Label LblFeedback;

	public LoginPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
		this.hoofdPaneel = hoofdPaneel;
		this.menuPaneel = menuPaneel;
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	private void voegComponentenToe() {
		final Text header = new Text(domeinController.getTaal().getLocalisatie("LOGIN"));
		GridPane.setHalignment(header, HPos.LEFT);
		final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEKENDE_NAAM"));
		TxtNaam = new TextField();
		TxtNaam.setMaxWidth(200);
		final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
		TxtGeboortejaar = new TextField();
		TxtGeboortejaar.setMaxWidth(200);
		Button btnSubmit = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
		Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
		btnSubmit.setOnAction(this::submit);
		btnBack.setOnAction(this::back);
		btnQuit.setOnAction(this::quit);
		LblFeedback = new Label();
		LblFeedback.setVisible(false);
		this.getChildren().addAll(header, naam, TxtNaam, jaar, TxtGeboortejaar, btnSubmit, btnBack, btnQuit);
	}

	private void submit(ActionEvent actionEvent) {
	
		try {
			domeinController.meldAan(this.TxtNaam.getText(), Integer.parseInt(this.TxtGeboortejaar.getText()));
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(domeinController.getTaal().getLocalisatie("CORRECT_AANGEMELD"));
			
			if (domeinController.geefSpelers().size() >= 4) {
				hoofdPaneel.setCenter(menuPaneel);
			} 
			else {
				alert.setContentText(domeinController.getTaal().getLocalisatie("NOG_AANMELDEN"));
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					menuPaneel.updateLoggedOnPlayerLabel();
					TxtNaam.setText("");
					TxtGeboortejaar.setText("");
				} else if (result.get() == ButtonType.CANCEL) {
					menuPaneel.updateLoggedOnPlayerLabel();
					hoofdPaneel.setCenter(menuPaneel);
				}
			}

		} catch (IllegalArgumentException e) {
			LblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
			alert.showAndWait();
			LblFeedback.setVisible(true);
		}
	}

	private void back(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(menuPaneel);
	}

	private void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
