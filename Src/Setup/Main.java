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

    private JButton steen;
    private JButton papier;
    private JButton schaar;
    private JPanel content;
    private JPanel upperBar;
    private JPanel center;
    private JFrame menu;
    private Object papieros;
    private Object steenos;
    private Object schaaros;

    {
        try {
            papieros = new Papier();
            schaaros = new Schaar();
            steenos = new Steen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object choice = null;
    private JLabel whiteSpace1;
    private JLabel whiteSpace2;

//    public Main() throws IOException
//    {
//        createMenu();
//        connectToServer();
//    }

    public static void main(String[] args) throws IOException {
        //Main applet = new Main();
        JFrame frame = new JFrame("");

        Main applet = new Main();
        applet.isStandAlone = true;
        if(args.length == 1) applet.host= args[0];

        frame.getContentPane().add(applet, BorderLayout.CENTER);

        applet.init();
        applet.start();

        frame.setSize(320, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
            int player = (int) fromServer.readObject();
            System.out.println(player);

            while (continueToPlay) {
                    waitForPlayerAction();
                    sendMove();


                //waitForPlayerAction(); // Wait for players to move to move
//                    sendMove(); // Send the move to the server
//                    receiveInfoFromServer(); // Receive info from the server
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }

        waiting = true;
    }

    private void sendMove() throws IOException {
        toServer.writeObject(choice);
    }

    public void init(){
        JPanel p = new JPanel(new BorderLayout());
//        content = new JPanel(new BorderLayout());
//        menu.add(content);
        center = new JPanel(new GridBagLayout());
        p.add(center, BorderLayout.CENTER);
        upperBar = new JPanel();
        p.add(upperBar,BorderLayout.NORTH);
        //setupComponents();

        steen = new JButton(steenos.getPlaatje());
        papier = new JButton(papieros.getPlaatje());
        schaar = new JButton(schaaros.getPlaatje());
        whiteSpace1 = new JLabel("");
        whiteSpace2 = new JLabel("");

        steen.addActionListener(evt -> {
            if(choice==null)
                choice = steenos;
            waiting = false;
            System.out.println(choice.getNaam());

        });

        papier.addActionListener(evt -> {
            if(choice==null)
                choice = papieros;
            waiting = false;

            System.out.println(choice.getNaam());
        });

        schaar.addActionListener(evt -> {
            if(choice==null)
                choice = schaaros;
            waiting = false;
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

        add(p, BorderLayout.CENTER);
        connectToServer();

    }
}
