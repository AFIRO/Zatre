package cui;

import domein.DomeinController;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UC1 {
    private final Scanner scanner;
    private final ResourceBundle taal;
    private final DomeinController domeinController;

    public UC1(DomeinController domeinController) {
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
                        registreerSpelerInputMenu();
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

    private void registreerSpelerInputMenu() {
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
                geefKeuzeMenu();
            }
        }
    }
    public void printMenu() {
        System.out.println(taal.getString("STARTMENU_1"));
        System.out.println(taal.getString("STARTMENU_2"));
        System.out.println(taal.getString("STARTMENU_3"));
        System.out.println(taal.getString("STARTMENU_4"));
    }
}
