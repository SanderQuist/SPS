package Setup;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Main extends JApplet implements Runnable, RockPaperScissorConstants{
    // Indicate whether the player has the turn
    private boolean myTurn = false;

    // selected object
    private Object selected;

    // input and output stream
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    // Continue to play?
    private boolean continueToPlay = true;

    // Wait for the player select
    private boolean waiting = true;

    // Indicate if it runs as application
    private boolean isStandAlone = false;

    // Host name or ip
    private String host = "localhost";

    public static void main(String[] args) throws IOException {
        Main applet = new Main();
        applet.isStandAlone = true;
        if(args.length == 1) applet.host= args[0];

        Menu menu = new Menu();
        applet.start();
    }


    @Override
    public void run() {

    }
}
