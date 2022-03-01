package main;

import cui.UC1;
import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StartUp {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();
        new UC1(domeinController);


    }
}

