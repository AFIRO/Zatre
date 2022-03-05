package main;

import cui.UC1;
import domein.DomeinController;
import domein.SpelerRepository;

public class StartUp {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController(new SpelerRepository());
        new UC1(domeinController);


    }
}

