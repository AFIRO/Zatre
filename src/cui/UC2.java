package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC2 {
    private final Scanner scanner;
    private final DomeinController domeinController;

    public UC2(DomeinController domeinController) {
        scanner = new Scanner(System.in);
        this.domeinController = domeinController;
        meldAanInputMenu();
    }

    private void meldAanInputMenu() {
        boolean loopflag = true;
        boolean inputLoopflag = true;
        int geboortejaar = 0;
        String gebruikersnaam = "";

        while (loopflag) {
            while (inputLoopflag)
                try {
                    System.out.println(domeinController.geefVertaling("GEKENDE_NAAM"));
                    gebruikersnaam = scanner.next();
                    System.out.println(domeinController.geefVertaling("GEWENSTE_GEBOORTEDATUM"));
                    geboortejaar = scanner.nextInt();
                    inputLoopflag = false;
                } catch (InputMismatchException e) {
                    System.out.println(domeinController.geefVertaling("CORRECTE_KEUZE"));
                    scanner.next();
                }
            try {
                domeinController.meldAan(gebruikersnaam, geboortejaar);
                System.out.println(domeinController.geefVertaling("CORRECT_AANGEMELD"));
                System.out.printf("%s", domeinController.geefSpelers());
                System.out.println();
                loopflag = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(domeinController.geefVertaling("PROBEER_OPNIEUW"));
            }

            //eerst alle spelers aanmelden en dan pas printen
        }
    }

}
