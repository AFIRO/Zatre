package testen;

import domein.Steen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SteenTest {
    @Test
    @DisplayName("Create steen, toegelaten waarde, happy flow")
    void create_happyflow(){
        Assertions.assertDoesNotThrow(()->new Steen(5));
    }

    @Test
    @DisplayName("Create steen, te groot, exception")
    void create_TeGroot_Exception(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new Steen(10));
    }

    @Test
    @DisplayName("Create steen, te klein, exception")
    void create_TeKlein_Exception(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new Steen(-1));
    }
}
