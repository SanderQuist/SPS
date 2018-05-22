package Setup;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame
{
    private String tijd;
    private JButton steen;
    private JButton papier;
    private JButton schaar;
    private JLabel timer;
    private JPanel content;
    private JPanel upperBar;
    private JPanel center;
    private JFrame menu;
    private Object papieros = new Papier();
    private Object schaaros = new Schaar();
    private Object steenos = new Steen();
    private Object choice = null;
    private Object object = new Object("Kip");


    public Menu() throws IOException {
        menu = new JFrame();
        content = new JPanel(new BorderLayout());
        menu.add(content);
        center = new JPanel(new GridLayout(1,3));
        content.add(center, BorderLayout.CENTER);
        upperBar = new JPanel();
        content.add(upperBar,BorderLayout.NORTH);
        setupComponents();

        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);

        menu.setVisible(true);

    }

    public void setupComponents(){
        steen = new JButton(steenos.getPlaatje());
        papier = new JButton(papieros.getPlaatje());
        schaar = new JButton(schaaros.getPlaatje());
        timer = new JLabel("Tijd:" + tijd);

        steen.addActionListener(evt -> {
            choice = steenos;
            System.out.println(choice.getNaam());

        });

        papier.addActionListener(evt -> {
            choice = papieros;
            System.out.println(choice.getNaam());
        });

        schaar.addActionListener(evt -> {
            choice = schaaros;
            System.out.println(choice.getNaam());
        });

        center.add(steen);
        center.add(papier);
        center.add(schaar);
        upperBar.add(timer);
    }


}
