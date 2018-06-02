package Setup;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
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
    private JLabel whiteSpace1;
    private JLabel whiteSpace2;


    public Menu() throws IOException {
        menu = new JFrame();
        content = new JPanel(new BorderLayout());
        menu.add(content);
        center = new JPanel(new GridBagLayout());
        content.add(center, BorderLayout.CENTER);
        upperBar = new JPanel();
        content.add(upperBar,BorderLayout.NORTH);
        setupComponents();

        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);

        menu.setVisible(true);
    }

    public void paintComponent(Graphics2D g){
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(1000,1000,1000,1000);

    }

    public void setupComponents(){
        steen = new JButton(steenos.getPlaatje());
        papier = new JButton(papieros.getPlaatje());
        schaar = new JButton(schaaros.getPlaatje());
        timer = new JLabel("Tijd:" + tijd);
        whiteSpace1 = new JLabel("");
        whiteSpace2 = new JLabel("");

        timer.setFont(new Font("TimesRoman", 1, 20));

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

        steen.setPreferredSize(new Dimension(135,181));
        papier.setPreferredSize(new Dimension(136,181));
        schaar.setPreferredSize(new Dimension(230,181));
        whiteSpace1.setPreferredSize(new Dimension(100,100));
        whiteSpace2.setPreferredSize(new Dimension(100,100));

        center.add(steen);
        center.add(whiteSpace1);
        center.add(schaar);
        center.add(whiteSpace2);
        center.add(papier);
        upperBar.add(timer);
    }
    }



