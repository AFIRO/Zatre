package gui;

import domein.DomeinController;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public class SpelPaneel extends GridPane {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private final SpelLogoPaneel spelLogoPaneel;
    private final SpelScorebladPaneel spelScorebladPaneel;
    private final SpelBordPaneel spelBordPaneel;
    private final SpelSpelerPaneel spelSpelerPaneel;

    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController,SpelLogoPaneel spelLogoPaneel, SpelScorebladPaneel spelScorebladPaneel, SpelBordPaneel spelBordPaneel, SpelSpelerPaneel spelSpelerPaneel) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        this.spelLogoPaneel = new SpelLogoPaneel(this, domeinController);
        this.spelScorebladPaneel = new SpelScorebladPaneel(this, domeinController);
        this.spelBordPaneel = new SpelBordPaneel(this, domeinController);
        this.spelSpelerPaneel = new SpelSpelerPaneel(this, menuPaneel, domeinController);
        voegComponentenToe();
        
    }
    
    private void voegComponentenToe() {
		// TODO Auto-generated method stub
		
	}
    
    
}
