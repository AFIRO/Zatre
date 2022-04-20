package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;


public class HoofdPaneel extends BorderPane
{
    private DomeinController domeinController;
    private final TaalPaneel taalPaneel;
    private MenuPaneel menuPaneel;


    public HoofdPaneel(DomeinController domeinController)
    {
        this.domeinController = domeinController;
        taalPaneel = new TaalPaneel(domeinController, this);
        voegComponentenToe();
    }
    
    private void voegComponentenToe()
    {
        setCenter(taalPaneel);

    }

    public void taalGekozen(){
        this.menuPaneel = new MenuPaneel(this,domeinController, new TaalPaneel(domeinController,this));
        setCenter(menuPaneel);
    }

    public void setDomeincontroller(DomeinController domeinController) {
        this.domeinController = domeinController;
    }
}
