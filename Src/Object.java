import javax.swing.*;
import java.io.IOException;

public class Object  {

    private String naam;
    private ImageIcon plaatje;

    public Object(String plaatjeNaam) throws IOException {
        this.naam = plaatjeNaam;

        plaatje = new ImageIcon(naam);

    }
}
