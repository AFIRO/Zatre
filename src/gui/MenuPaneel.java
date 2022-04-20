package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuPaneel extends VBox {
	private final DomeinController domeinController;
	private final HoofdPaneel hoofdPaneel;
	private final RegistratieEnLoginPaneel registratieEnLoginPaneel;
	private final SpelPaneel spelPaneel;
	private final TaalPaneel taalPaneel;
	private Label lblLoggedOn;

	public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController, TaalPaneel taalPaneel) {
		this.hoofdPaneel = hoofdPaneel;
		this.domeinController = domeinController;
		this.taalPaneel = taalPaneel;
		this.registratieEnLoginPaneel = new RegistratieEnLoginPaneel(hoofdPaneel,this,domeinController);
		this.spelPaneel = new SpelPaneel(hoofdPaneel, this, domeinController);

		voegComponentenToe();
	}

	private void voegComponentenToe() {

		Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
		Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
		HBox alignmentBoxEersteRij = new HBox();
		HBox alignmentBoxTweedeRij = new HBox();
		Button btnRegistreerAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3") + "/" + domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
		Button btnSpelStarten = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_7"));
		Button btnKiesTaal = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_8"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
		alignmentBoxEersteRij.getChildren().addAll(btnRegistreerAanmelden,btnSpelStarten);
		alignmentBoxTweedeRij.getChildren().addAll(btnKiesTaal,btnQuit);
		lblLoggedOn = new Label();
		btnRegistreerAanmelden.setOnAction(this::registreerAanmelden);
		btnSpelStarten.setOnAction(this::starten);
		btnKiesTaal.setOnAction(this::kiesTaal);
		lblLoggedOn.setVisible(false);
		btnQuit.setOnAction(this::quit);

		this.getChildren().addAll(header, subheader, alignmentBoxEersteRij, alignmentBoxTweedeRij,
				lblLoggedOn);

	}

	private void starten(ActionEvent actionEvent) {
		try {
			domeinController.startSpel();
			Stage stage = (Stage) this.hoofdPaneel.getScene().getWindow();
			stage.setWidth(1800);
			stage.setHeight(1200);
			this.spelPaneel.getSpelScorebladPaneel().updateInfo(0);
			hoofdPaneel.setCenter(spelPaneel);
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(domeinController.getTaal().getLocalisatie(e.getMessage()));
			alert.showAndWait();
		}

	}

	private void registreerAanmelden(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(registratieEnLoginPaneel);
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
