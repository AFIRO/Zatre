package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuPaneel extends VBox {
	private final DomeinController domeinController;
	private final HoofdPaneel hoofdPaneel;
	private final RegistratiePaneel registratiePaneel;
	private final LoginPaneel loginPaneel;
	private final SpelPaneel spelPaneel;
	private final TaalPaneel taalPaneel;
	private Label lblLoggedOn;

	public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController, TaalPaneel taalPaneel) {
		this.hoofdPaneel = hoofdPaneel;
		this.domeinController = domeinController;
		this.taalPaneel = taalPaneel;
		this.registratiePaneel = new RegistratiePaneel(hoofdPaneel, this, domeinController);
		this.loginPaneel = new LoginPaneel(hoofdPaneel, this, domeinController);
		this.spelPaneel = new SpelPaneel(hoofdPaneel, this, domeinController);

		voegComponentenToe();
	}

	private void voegComponentenToe() {

		Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
		GridPane.setHalignment(header, HPos.LEFT);
		Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
		GridPane.setHalignment(subheader, HPos.RIGHT);
		Button btnRegistreer = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3"));
		Button btnAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
		Button btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
		Button btnKiesTaal = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_8"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
		lblLoggedOn = new Label();
		btnRegistreer.setOnAction(this::registreer);
		btnAanmelden.setOnAction(this::login);
		btnSpelStarten.setOnAction(this::starten);
		btnKiesTaal.setOnAction(this::kiesTaal);
		lblLoggedOn.setVisible(false);
		btnQuit.setOnAction(this::quit);

		this.getChildren().addAll(header, subheader, btnRegistreer, btnAanmelden, btnSpelStarten, btnKiesTaal, btnQuit,
				lblLoggedOn);

	}

	private void starten(ActionEvent actionEvent) {
		try {
			domeinController.startSpel();
			hoofdPaneel.setCenter(spelPaneel);
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
			alert.showAndWait();
		}

	}

	private void registreer(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(registratiePaneel);
	}

	private void login(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(loginPaneel);

	}

	private void kiesTaal(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(taalPaneel);
	}

	public void updateLoggedOnPlayerLabel() {
		if (!domeinController.geefSpelers().isEmpty()) {
			this.lblLoggedOn.setVisible(true);
			this.lblLoggedOn.setText(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_6")
					+ String.format("%n%n") + String.join("", domeinController.geefSpelers()));
		}
	}

	private void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
