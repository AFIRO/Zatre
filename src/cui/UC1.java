package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC1 {
    private final Scanner scanner;
    private final DomeinController domeinController;

    public UC1(DomeinController domeinController) {
        scanner = new Scanner(System.in);
        this.domeinController = domeinController;
        registreerSpelerInputMenu();
    }


    private void registreerSpelerInputMenu() {
        boolean loopflag = true;
        boolean inputLoopflag = true;
        int geboortejaar = 0;
        String gebruikersnaam = "";

        while (loopflag) {
            while (inputLoopflag)
                try {
                    System.out.println(domeinController.geefVertaling("GEWENSTE_NAAM"));
                    gebruikersnaam = scanner.next();
                    System.out.println(domeinController.geefVertaling("GEWENSTE_GEBOORTEDATUM"));
                    geboortejaar = scanner.nextInt();
                    inputLoopflag = false;
                } catch (InputMismatchException e) {
                    System.out.println(domeinController.geefVertaling("CORRECTE_KEUZE"));
                    scanner.next();
                }
            try {
                domeinController.registreer(gebruikersnaam, geboortejaar);
                System.out.println(domeinController.geefVertaling("CORRECT_GEREGISTREERD"));
                System.out.println(domeinController.geefVertaling("GEBRUIKERSNAAM") + gebruikersnaam);
                System.out.println(domeinController.geefVertaling("GEBOORTEJAAR") + geboortejaar);
                System.out.println();
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(domeinController.geefVertaling(e.getMessage()));
                System.out.println(domeinController.geefVertaling("PROBEER_OPNIEUW"));
                registreerSpelerInputMenu();
            }
        }
    }
}
