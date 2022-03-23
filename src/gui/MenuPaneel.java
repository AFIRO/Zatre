package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
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
	private Label lblLoggedOn;

	public MenuPaneel(HoofdPaneel hoofdPaneel, DomeinController domeinController) {
		this.hoofdPaneel = hoofdPaneel;
		this.domeinController = domeinController;
		this.registratiePaneel = new RegistratiePaneel(hoofdPaneel,this, domeinController);
		this.loginPaneel = new LoginPaneel(hoofdPaneel,this, domeinController);

		voegComponentenToe();
	}

	private void voegComponentenToe() {

		Text header = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_1"));
		GridPane.setHalignment(header, HPos.LEFT);
		Text subheader = new Text(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_2"));
		GridPane.setHalignment(subheader, HPos.RIGHT);
		Button BtnRegistreer = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_3"));
		Button BtnAanmelden = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_4"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
		lblLoggedOn = new Label();
		BtnRegistreer.setOnAction(this::registreer);
		BtnAanmelden.setOnAction(this::login);
		lblLoggedOn.setVisible(false);
		btnQuit.setOnAction(this::quit);

		this.getChildren().addAll(header, subheader, BtnRegistreer, BtnAanmelden, btnQuit, lblLoggedOn);

	}

	private void registreer(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(registratiePaneel);
	}

	private void login(ActionEvent actionEvent) {
		hoofdPaneel.setCenter(loginPaneel);

	}

	public void updateLoggedOnPlayerLabel(){
		if (!domeinController.geefSpelers().isEmpty()) {
			this.lblLoggedOn.setVisible(true);
			this.lblLoggedOn.setText(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_6")
					+ String.format("%n%n")
					+ String.join("", domeinController.geefSpelers()));
		}
	}

	private void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
