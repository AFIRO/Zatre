package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SpelLogoPaneel extends StackPane {
	private final DomeinController domeinController;
	private static final String LOGO_LOCATIE = "file:src/assets/logo/logo.jpg";
	private final ImageView imLogo = new ImageView();

	public SpelLogoPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	private void voegComponentenToe() {
		Image logo = new Image(LOGO_LOCATIE);
		imLogo.setImage(logo);
		imLogo.setFitWidth(400);
		imLogo.setFitHeight(100);
		StackPane.setAlignment(imLogo,Pos.CENTER);
		this.getChildren().add(imLogo);

		
	}
}
