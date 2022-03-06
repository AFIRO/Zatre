package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class UC2 
{
    private final Scanner scanner;
    private final ResourceBundle taal;
    private final DomeinController domeinController;

    public UC2(DomeinController domeinController) {
        scanner = new Scanner(System.in);
        taal = ResourceBundle.getBundle("dictionary",Locale.getDefault());
        this.domeinController = domeinController;
        geefKeuzeMenu();
    }

    public void geefKeuzeMenu() {
        boolean loopflag = true;

        while (loopflag) {
            try {
                printMenu();
                int input = scanner.nextInt();
                switch (input) {
                    case 1: {
                        meldAanInputMenu();
                        geefKeuzeMenu();
                    }
                    case 0: {
                        loopflag = false;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(taal.getString("CORRECTE_KEUZE"));
                scanner.next();
            }

        }
    }

    private void meldAanInputMenu() {
        boolean loopflag = true;
        boolean inputLoopflag = true;
        int geboortejaar = 0;
        String gebruikersnaam = "";

        while (loopflag) {
            while (inputLoopflag)
                try {
                    System.out.println(taal.getString("GEKENDE_NAAM"));
                    gebruikersnaam = scanner.next();
                    System.out.println(taal.getString("GEWENSTE_GEBOORTEDATUM"));
                    geboortejaar = scanner.nextInt();
                    inputLoopflag = false;
                } catch (InputMismatchException e) {
                    System.out.println(taal.getString("CORRECTE_KEUZE"));
                    scanner.next();
                }
            try {
                domeinController.meldAan(gebruikersnaam, geboortejaar);
                System.out.println(taal.getString("CORRECT_AANGEMELD"));
                domeinController.geefSpeler(gebruikersnaam, geboortejaar);
                System.out.printf("%s%n%d", "AANTAL_SPEELKANSEN", domeinController.getSpeelkansen); 
                System.out.println();
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(taal.getString("PROBEER_OPNIEUW"));
                geefKeuzeMenu();
            }
        }
    }
    public void printMenu() {
        System.out.println(taal.getString("AANMELDMENU_1"));
        System.out.println(taal.getString("STARTMENU_2"));
        System.out.println(taal.getString("AANMELDMENU_2"));
        System.out.println(taal.getString("STARTMENU_4"));
    }
}
