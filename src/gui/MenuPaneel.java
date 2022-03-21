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
	private final HoofdPaneel hoofdPaneel;
	private RegistratiePaneel registratiePaneel;
	private LoginPaneel loginPaneel;
	private final DomeinController domeinController;
	private Label LblLoggedOn;

	public MenuPaneel(HoofdPaneel hoofdPaneel, RegistratiePaneel registratiePaneel, LoginPaneel loginPaneel, DomeinController domeinController) {
		this.hoofdPaneel = hoofdPaneel;
		this.registratiePaneel = registratiePaneel;
		this.domeinController = domeinController;

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
		LblLoggedOn = new Label();
		BtnRegistreer.setOnAction(this::registreer);
		BtnAanmelden.setOnAction(this::login);
		LblLoggedOn.setVisible(false);
		btnQuit.setOnAction(this::quit);

		this.getChildren().addAll(header, subheader, BtnRegistreer, BtnAanmelden, btnQuit, LblLoggedOn);

	}

	private void registreer(ActionEvent actionEvent) {
		this.registratiePaneel = new RegistratiePaneel(hoofdPaneel,this, domeinController);
		hoofdPaneel.setCenter(registratiePaneel);
	}

	private void login(ActionEvent actionEvent) {
		this.loginPaneel = new LoginPaneel(hoofdPaneel,this, domeinController);
		hoofdPaneel.setCenter(loginPaneel);

	}

	public void updateLoggedOnPlayerLabel(){
		if (!domeinController.geefSpelers().isEmpty()) {
			this.LblLoggedOn.setVisible(true);
			this.LblLoggedOn.setText(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_6")
					+ String.format("%n%n")
					+ String.join("", domeinController.geefSpelers()));
		}
	}

	private void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
