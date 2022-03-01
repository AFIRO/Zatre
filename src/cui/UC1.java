package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC1 {

    public UC1(DomeinController domeinController) {
        Scanner scanner = new Scanner(System.in);
        geefKeuzeMenu(scanner, domeinController);
    }

    public void geefKeuzeMenu(Scanner scanner, DomeinController domeinController) {
        boolean loopflag = true;

        while (loopflag) {
            try {
                printMenu();


                int input = scanner.nextInt();
                switch (input) {
                    case 1: {
                        registreerSpelerInputMenu(scanner, domeinController);
                        geefKeuzeMenu(scanner, domeinController);
                    }
                    case 0: {
                        loopflag = false;
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("Gelieve een correcte keuze te maken.");
                scanner.next();
            }

        }
    }

    private void registreerSpelerInputMenu(Scanner scanner, DomeinController domeinController) {
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
                    System.out.println("Gelieve een correcte keuze te maken.");
                    scanner.next();
                }

            try {
                domeinController.registreer(gebruikersnaam, geboortejaar);
                System.out.println("Speler werd correct geregistreerd.");
                System.out.println("Gebruikersnaam: " + gebruikersnaam);
                System.out.println("Geboortejaar: " + geboortejaar);
                System.out.println();
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Probeer opnieuw.");
                geefKeuzeMenu(scanner,domeinController);
            }
        }
    }

    public void printMenu() {
        System.out.println("Welkom bij de Zartre applicatie");
        System.out.println("Maak een keuze:");
        System.out.println("1. Registreer speler.");
        System.out.println("0. Verlaat het spel.");
    }
}
