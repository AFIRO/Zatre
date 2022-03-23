package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;


public class HoofdPaneel extends BorderPane
{
    private DomeinController domeinController;
    private final TaalPaneel taalPaneel = new TaalPaneel(this);
    private MenuPaneel menuPaneel;
    private RegistratiePaneel registratiePaneel;
    private LoginPaneel loginPaneel;


    public HoofdPaneel()
    {
        voegComponentenToe();
    }
    
    private void voegComponentenToe()
    {
        setCenter(taalPaneel);

    }

    public void taalGekozen(){
        this.menuPaneel = new MenuPaneel(this,domeinController);
        setCenter(menuPaneel);
    }

    public void setDomeincontroller(DomeinController domeinController) {
        this.domeinController = domeinController;
    }
}
