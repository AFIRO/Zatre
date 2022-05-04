package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SpelLogoPaneel extends StackPane {
	private static final String LOGO_LOCATIE = "file:src/assets/logo/ZatreLogo4.png";
	private final DomeinController domeinController;
	private final ImageView imLogo = new ImageView();

	/**
	 * UC3: constructor voor paneel
	 *
	 * @param domeinController de dc voor gebruik
	 */

	public SpelLogoPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	/**
	 * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
	 * op de juiste plaats.
	 */

	private void voegComponentenToe() {
		// instantie elementen
	
		Image logo = new Image(LOGO_LOCATIE);

		// styling
		imLogo.setImage(logo);
		
		imLogo.setFitWidth(400);
		imLogo.setFitHeight(100);
		StackPane.setAlignment(imLogo, Pos.CENTER);

		// insert in GUI
		this.getChildren().addAll(imLogo);
	}
}
