module zatre.g101 {
    exports persistence;
    exports gui;
    exports domein;
    exports util;


   requires javafx.controls;
    requires java.sql;
	requires javafx.graphics;
  //requires org.mockito;
   // requires org.mockito.junit.jupiter;
  // requires org.junit.jupiter.api;

    opens gui;
}