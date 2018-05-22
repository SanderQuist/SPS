package Game;

import javax.swing.*;
import java.io.IOException;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Object  {

    private String naam;
    private ImageIcon plaatje;

    public Object(String plaatjeNaam) throws IOException {
        this.naam = plaatjeNaam;

        System.out.println(naam);
        plaatje = new ImageIcon(getClass().getResource(naam));
    }

    public String getNaam() {
        return naam;
    }

    public ImageIcon getPlaatje() {
        return plaatje;
    }
}