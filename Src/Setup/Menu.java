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

    public Menu()
    {
        JPanel content = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new GridLayout(1,3));
        content.add(center, BorderLayout.CENTER);
        setupComponents();

    }

    public void setupComponents(){
        steen = new JButton("Steen");
        papier = new JButton("Papier");
        schaar = new JButton("Schaar");
        timer = new JLabel("Tijd:" + tijd);
    }
}
