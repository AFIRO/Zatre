package gui;

import domein.DomeinController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SpelBordPaneel extends GridPane{
	 private static final String SPELBORD_LOCATIE = "assets/spelbord/spelbord.jpg";
	 private final DomeinController domeinController;
	 private final ImageView imgSpelbord = new ImageView();
	

	public SpelBordPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		  voegComponentenToe();
		
	}

	private void voegComponentenToe() {
		Image spelbord = new Image(SPELBORD_LOCATIE);
		imgSpelbord.setImage(spelbord);
		this.getChildren().add(imgSpelbord);
	}
	
}
