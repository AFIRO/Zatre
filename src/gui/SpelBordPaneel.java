package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SpelBordPaneel extends GridPane{
	private static final String SPELBORD_LOCATIE = "file:src/assets/spelbord/spelbord.jpg";
	private final DomeinController domeinController;
	private final ImageView imgSpelbord = new ImageView();
	

	public SpelBordPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		this.setAlignment(Pos.CENTER);
		this.setHeight(1000);
		this.setWidth(1000);
		voegComponentenToe();
		
	}

	private void voegComponentenToe() {
		StackPane centeringPane = new StackPane();
		Image spelbord = new Image(SPELBORD_LOCATIE);
		imgSpelbord.setImage(spelbord);
		imgSpelbord.setFitWidth(1300);
		imgSpelbord.setFitHeight(1000);
		centeringPane.getChildren().add(imgSpelbord);
		StackPane.setAlignment(imgSpelbord, Pos.CENTER);
		this.getChildren().add(centeringPane);
	}
	
}
