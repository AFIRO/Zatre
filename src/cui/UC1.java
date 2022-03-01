package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UC1 {

    public UC1(DomeinController domeinController) {
        Scanner scanner = new Scanner(System.in);
        ResourceBundle taal = ResourceBundle.getBundle("dictionary",geefKeuzeMenuTaal(scanner));
        geefKeuzeMenu(scanner, domeinController, taal);
    }

    private Locale geefKeuzeMenuTaal(Scanner scanner) {
        boolean loopflag = true;
        int taalKeuze;
        Locale taal = new Locale("nl", "BE");
        while (loopflag) {
            System.out.println("Kies uw taal / Choose your language:");
            System.out.println("1. Nederlands.");
            System.out.println("2. English.");
            try {
                taalKeuze = scanner.nextInt();
                if (taalKeuze < 1 || taalKeuze > 2){
                    System.out.println("Probeer opnieuw / Try again.");
                }

                if (taalKeuze == 2) {
                    taal = new Locale("en");
                }
                loopflag = false;

            } catch (InputMismatchException e) {
                System.out.println("Probeer opnieuw / Try again.");
                scanner.next();
            }
        }
        return taal;
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
                    System.out.println("Geef uw gewenste gebruikersnaam in: ");
                    gebruikersnaam = scanner.next();
                    System.out.println("Geef uw geboortejaar in: ");
                    geboortejaar = scanner.nextInt();
                    inputLoopflag = false;
                } catch (InputMismatchException e) {
                    System.out.println(taal.getString("CORRECTE_KEUZE"));
                    scanner.next();
                }
            try {
                domeinController.registreer(gebruikersnaam, geboortejaar);
                System.out.println("Speler werd correct geregistreerd.");
                System.out.println("Gebruikersnaam: " + gebruikersnaam);
                System.out.println("Geboortejaar: " + geboortejaar);
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Probeer opnieuw.");
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
