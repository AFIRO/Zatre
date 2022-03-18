package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegistratiePaneel extends VBox {

	private final MenuPaneel menuPaneel;
	private final DomeinController domeinController;
	String gebruikersnaam;
	int geboortejaar;
	TextField naamText;
	TextField geboortejaarText;

	public RegistratiePaneel(MenuPaneel menuPaneel, DomeinController domeinController) {
		this.menuPaneel = menuPaneel;
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	private void voegComponentenToe() {
		final Text header = new Text(domeinController.getTaal().getLocalisatie("REGISTRATIE"));
		GridPane.setHalignment(header, HPos.LEFT);

		final Label naam = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
		naamText = new TextField();
		naamText.setPrefWidth(100);

		final Label jaar = new Label(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
		geboortejaarText = new TextField();
		geboortejaarText.setPrefWidth(100);

		Button btnSubmit = new Button(domeinController.getTaal().getLocalisatie("SUBMIT"));
		Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));

		btnSubmit.setOnAction(this::submit);
		btnQuit.setOnAction(this::quit);

		this.getChildren().addAll(header, naam, naamText, jaar, geboortejaarText, btnSubmit, btnQuit);
	}

	public void submit(ActionEvent actionEvent) {
		this.gebruikersnaam = this.naamText.getText();
		this.geboortejaar = Integer.parseInt(this.geboortejaarText.getText());

		domeinController.registreer(gebruikersnaam, geboortejaar);
	}

	public void quit(ActionEvent actionEvent) {
		System.exit(0);

	}
}
