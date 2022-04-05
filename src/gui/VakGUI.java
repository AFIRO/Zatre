package gui;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class VakGUI extends Button {
    private int waardeVanSteen;
    private String rij;
    private String kolom;


    public VakGUI(boolean wit, String s, Node node, String rij, String kolom) {
        super(s, node);
        this.rij = rij;
        this.kolom = kolom;

        if (wit = true){

        }
    }

    public void setWaardeVanSteen(int waardeVanSteen) {
        this.waardeVanSteen = waardeVanSteen;
    }

    public int getWaardeVanSteen() {
        return waardeVanSteen;
    }

    public String getRij() {
        return rij;
    }

    public String getKolom() {
        return kolom;
    }
}
