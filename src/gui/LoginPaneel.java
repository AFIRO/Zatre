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

	private final DomeinController domeinController;
	private final HoofdPaneel hoofdPaneel;
	private final MenuPaneel menuPaneel;
	private TextField txtNaam;
	private TextField txtGeboortejaar;
	private Label lblFeedback;

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
		txtNaam = new TextField();
		txtNaam.setMaxWidth(200);
		final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
		txtGeboortejaar = new TextField();
		txtGeboortejaar.setMaxWidth(200);
		Button btnSubmit = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
		Button btnBack = new Button(domeinController.getTaal().getLocalisatie("TERUG"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
		btnSubmit.setOnAction(this::submit);
		btnBack.setOnAction(this::back);
		btnQuit.setOnAction(this::quit);
		lblFeedback = new Label();
		lblFeedback.setVisible(false);
		this.getChildren().addAll(header, naam, txtNaam, jaar, txtGeboortejaar, btnSubmit, btnBack, btnQuit,lblFeedback);
	}

	private void submit(ActionEvent actionEvent) {
	
		try {
			domeinController.meldAan(this.txtNaam.getText(), Integer.parseInt(this.txtGeboortejaar.getText()));
			lblFeedback.setText(domeinController.getTaal().getLocalisatie("CORRECT_AANGEMELD"));
			lblFeedback.setVisible(true);

			if (domeinController.geefSpelers().size() >= 4) {
				menuPaneel.updateLoggedOnPlayerLabel();
				hoofdPaneel.setCenter(menuPaneel);
			} 
			else {
				lblFeedback.setText(domeinController.getTaal().getLocalisatie("NOG_AANMELDEN2"));
				menuPaneel.updateLoggedOnPlayerLabel();
				txtNaam.setText("");
				txtGeboortejaar.setText("");
			}

		} catch (IllegalArgumentException e) {
			lblFeedback.setText(domeinController.getTaal().getLocalisatie(e.getMessage()));
			lblFeedback.setVisible(true);
		}
	}

	private void back(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(menuPaneel);
	}

	private void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
