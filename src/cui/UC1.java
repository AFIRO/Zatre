package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UC1 {

    public UC1(DomeinController domeinController) {
        Scanner scanner = new Scanner(System.in);
        geefKeuzeMenu(scanner, domeinController, ResourceBundle.getBundle("dictionary",Locale.getDefault()));
    }

    public void geefKeuzeMenu(Scanner scanner, DomeinController domeinController, ResourceBundle taal) {
        boolean loopflag = true;

        while (loopflag) {
            try {
                printMenu(taal);
                int input = scanner.nextInt();
                switch (input) {
                    case 1: {
                        registreerSpelerInputMenu(scanner, domeinController, taal);
                        geefKeuzeMenu(scanner, domeinController, taal);
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

    private void registreerSpelerInputMenu(Scanner scanner, DomeinController domeinController, ResourceBundle taal) {
        boolean loopflag = true;
        boolean inputLoopflag = true;
        int geboortejaar = 0;
        String gebruikersnaam = "";

        while (loopflag) {
            while (inputLoopflag)
                try {
                    System.out.println(taal.getString("GEWENSTE_NAAM"));
                    gebruikersnaam = scanner.next();
                    System.out.println(taal.getString("GEWENSTE_GEBOORTEDATUM"));
                    geboortejaar = scanner.nextInt();
                    inputLoopflag = false;
                } catch (InputMismatchException e) {
                    System.out.println(taal.getString("CORRECTE_KEUZE"));
                    scanner.next();
                }
            try {
                domeinController.registreer(gebruikersnaam, geboortejaar);
                System.out.println(taal.getString("CORRECT_GEREGISTREERD"));
                System.out.println(taal.getString("GEBRUIKERSNAAM") + gebruikersnaam);
                System.out.println(taal.getString("GEBOORTEJAAR") + geboortejaar);
                System.out.println();
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(taal.getString("PROBEER_OPNIEUW"));
                geefKeuzeMenu(scanner,domeinController, taal);
            }
        }
    }
    public void printMenu(ResourceBundle taal) {
        System.out.println(taal.getString("STARTMENU_1"));
        System.out.println(taal.getString("STARTMENU_2"));
        System.out.println(taal.getString("STARTMENU_3"));
        System.out.println(taal.getString("STARTMENU_4"));
    }
}
