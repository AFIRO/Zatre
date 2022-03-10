package main;


import cui.UC1;
import cui.UC2;
import domein.DomeinController;
import domein.Speler;
import util.Taal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StartUp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DomeinController domeinController = new DomeinController(geefKeuzeMenuTaal(scanner));
        geefKeuzeMenu(scanner, domeinController);
     
        //final Taal taal;
        //taal = domeinController.getTaal(); 

    }
  
    private static Taal geefKeuzeMenuTaal(Scanner scanner) {
        boolean loopflag = true;
        int taalKeuze;
        Taal taal = new Taal(Taal.Taalkeuze.NEDERLANDS);
        while (loopflag) {
            System.out.println("Kies uw taal / Choose your language:");
            System.out.println("1. Nederlands.");
            System.out.println("2. English.");
            try {
                taalKeuze = scanner.nextInt();
                if (taalKeuze < 1 || taalKeuze > 2) {
                    System.out.println("Probeer opnieuw / Try again.");
                }

                if (taalKeuze == 2) {
                    taal = new Taal(Taal.Taalkeuze.ENGELS);
                }
                loopflag = false;

            } catch (InputMismatchException e) {
                System.out.println("Probeer opnieuw / Try again.");
                scanner.next();
            }
        }
        return taal;
        
   
    }
     

    private static void geefKeuzeMenu(Scanner scanner, DomeinController domeinController) {
        boolean loopflag = true;
        while (loopflag) {
            try {
                printMenu(domeinController);
                int input = scanner.nextInt();
                switch (input) {
                    case 1: {
                        new UC1(domeinController);
                        geefKeuzeMenu(scanner, domeinController);
                    }
                    case 2: {
                        new UC2(domeinController);
                        geefKeuzeMenu(scanner, domeinController);
                    }
                    case 0: {
                        loopflag = false;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(taal.getLocalisatie("CORRECTE_KEUZE"));
                scanner.next();
            }

        }
    
    }
    


    public static void printMenu(DomeinController domeinController) {
        System.out.println(taal.getLocalisatie("STARTMENU_1"));
        System.out.println(taal.getLocalisatie("STARTMENU_2"));
        System.out.println(taal.getLocalisatie("STARTMENU_3"));
        System.out.println(taal.getLocalisatie("STARTMENU_4"));
        System.out.println(taal.getLocalisatie("STARTMENU_5"));
    }
}

//foutieve taalkeuze, bij iets anders dan 1/2 taalkeuze blijven staan
//bij registratie met goede leeftijd, blijft speler te jong staan
//bij iedereen aangemeld en opnieuw aanmelden -> komen vast te zitten



