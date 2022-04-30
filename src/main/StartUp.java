package main;

import cui.UC1;
import cui.UC2;
import domein.DomeinController;
import util.Taal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StartUp {
    private final static Scanner scanner = new Scanner(System.in);

    /**
     *UC1: entry point CLI applicatie.
     *
     */

    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();
        domeinController.setTaal(geefKeuzeMenuTaal());
        geefKeuzeMenu(domeinController);
    }

    /**
     *UC1: taalmenu die speler in staat stelt om zijn taal in te stellen
     *
     */

    private static Taal geefKeuzeMenuTaal() {
        boolean loopflag = true;
        int taalKeuze = 0;
        Taal taal = new Taal(Taal.Taalkeuze.NEDERLANDS);
        while (loopflag) {
            while (taalKeuze < 1 || taalKeuze > 2) {
                System.out.println("Kies uw taal / Choose your language:");
                System.out.println("1. Nederlands.");
                System.out.println("2. English.");
                try {
                    taalKeuze = scanner.nextInt();
                    if (taalKeuze < 1 || taalKeuze > 2) {
                        throw new IllegalArgumentException("Probeer opnieuw / Try again.");
                    }

                    if (taalKeuze == 2) {
                        taal = new Taal(Taal.Taalkeuze.ENGELS);
                    }

                    loopflag = false;

                } catch (InputMismatchException e) {
                    System.out.println("Probeer opnieuw / Try again.");
                    scanner.next();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return taal;

    }

    /**
     *UC2: menu die speler in staat stelt om doorheen de applicatie te navigeren
     *
     */

    private static void geefKeuzeMenu(DomeinController domeinController) {

        boolean loopflag = true;
        while (loopflag) {
            try {
                printMenu(domeinController);
                int input = scanner.nextInt();
                switch (input) {
                    case 1: {
                        new UC1(domeinController);
                        geefKeuzeMenu(domeinController);
                    }
                    case 2: {
                        new UC2(domeinController);
                        geefKeuzeMenu(domeinController);
                    }
                    case 0: {
                        loopflag = false;
                    }
                }
            } catch (InputMismatchException e) {

                System.out.println(domeinController.getTaal().getLocalisatie("CORRECTE_KEUZE"));
                scanner.next();
            }
        }
    }

    /**
     *UC2: print het keuzemenu
     *
     */

    public static void printMenu(DomeinController domeinController) {

        System.out.println(domeinController.getTaal().getLocalisatie("STARTMENU_1"));
        System.out.println(domeinController.getTaal().getLocalisatie("STARTMENU_2"));
        System.out.println(domeinController.getTaal().getLocalisatie("STARTMENU_3"));
        System.out.println(domeinController.getTaal().getLocalisatie("STARTMENU_4"));
        System.out.println(domeinController.getTaal().getLocalisatie("STARTMENU_5"));
    }
}
