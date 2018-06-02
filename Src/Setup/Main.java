package Setup;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Main extends JApplet implements Runnable, RockPaperScissorConstants{
    // Indicate whether the player has the turn
    private boolean myTurn = false;

    // selected object
    private Object selected;

    // input and output stream
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;

    // Continue to play?
    private boolean continueToPlay = true;

    // Wait for the player select
    private boolean waiting = true;

    // Indicate if it runs as application
    private boolean isStandAlone = false;

    // Host name or ip
    private String host = "localhost";

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

    public Main() throws IOException
    {
        createMenu();
        connectToServer();
    }

    public static void main(String[] args) throws IOException {
        Main applet = new Main();
        applet.isStandAlone = true;
        if(args.length == 1) applet.host= args[0];


        applet.start();
    }

    private void connectToServer() {
        try {
            // Create a socket to connect to the server
            Socket socket;
            if (isStandAlone)
                socket = new Socket(host, 8000);
            else
                socket = new Socket(getCodeBase().getHost(), 8000);

            // Create an input stream to receive data from the server
            fromServer = new ObjectInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception ex) {
            System.err.println(ex);
        }

        // Control the game on a separate thread
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try {
            int player = fromServer.readInt();
            System.out.println(player);

            while (continueToPlay) {
//                    waitForPlayerAction(); // Wait for players to move to move
//                    sendMove(); // Send the move to the server
//                    receiveInfoFromServer(); // Receive info from the server
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMenu(){
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
