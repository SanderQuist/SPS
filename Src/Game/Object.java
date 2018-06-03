package Game;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;


public class Object implements Serializable {

    private String naam;
    private ImageIcon plaatje;

    public Object(String plaatjeNaam) throws IOException {
        this.naam = plaatjeNaam;

        System.out.println(naam);
        plaatje = new ImageIcon(naam);
    }

    public String getNaam() {
        return naam;
    }

    public ImageIcon getPlaatje() {
        return plaatje;
    }
}