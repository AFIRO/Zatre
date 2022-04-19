package gui;

import domein.DomeinController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SpelLogoPaneel extends HBox {
	private final DomeinController domeinController;
	private static final String LOGO_LOCATIE = "assets/logo/logo.jpg";
	private final ImageView imLogo = new ImageView();

	public SpelLogoPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	private void voegComponentenToe() {
		Image logo = new Image(LOGO_LOCATIE);
		imLogo.setImage(logo);
		this.getChildren().add(imLogo);
		
	}
}
