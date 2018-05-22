package Setup;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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

    public Menu()
    {
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
        steen = new JButton("Steen");
        papier = new JButton("Papier");
        schaar = new JButton("Schaar");
        timer = new JLabel("Tijd:" + tijd);

        center.add(steen);
        center.add(papier);
        center.add(schaar);
        upperBar.add(timer);
    }
}
