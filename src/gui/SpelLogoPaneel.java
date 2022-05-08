package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class SpelLogoPaneel extends StackPane {
    private static final String LOGO_LOCATIE = "file:src/assets/logo/ZatreLogo.png";
    private final ImageView imLogo = new ImageView();

    public SpelLogoPaneel() {
        voegComponentenToe();
    }

    /**
     * UC3: initialiseert de elementen, geeft hen de correcte styling en plaatst hen
     * op de juiste plaats.
     * <p>
     * Hulpmethode voor constructor
     */

    private void voegComponentenToe() {
        // instantie elementen
        Image logo = new Image(LOGO_LOCATIE);

        // styling
        imLogo.setImage(logo);
        imLogo.setFitWidth(400);
        imLogo.setFitHeight(100);
        StackPane.setMargin(imLogo, new Insets(20, 0, 0, 0));
        StackPane.setAlignment(imLogo, Pos.CENTER);

        // insert in GUI
        this.getChildren().addAll(imLogo);
    }
}
