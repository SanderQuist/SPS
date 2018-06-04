package Server;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;
import Setup.RockPaperScissorConstants;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Setup.RockPaperScissorConstants.PLAYER1;

public class Server
        extends JFrame
        implements RockPaperScissorConstants {
    public static void main(String[] args) {
        Server frame = new Server();
    }

    public Server() {
        JTextArea jtaLog = new JTextArea();

        // Create a scroll pane to hold text area
        JScrollPane scrollPane = new JScrollPane(jtaLog);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Server");
        setVisible(true);

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jtaLog.append(new Date() +
                    ": Server started at socket 8000\n");

            // Number a session
            int sessionNo = 1;

            // Ready to create a session for every two players
            while (true) {
                jtaLog.append(new Date() +
                        ": Wait for players to join session " + sessionNo + '\n');

                // Connect to player 1
                Socket player1 = serverSocket.accept();

                jtaLog.append(new Date() + ": Player 1 joined session " +
                        sessionNo + '\n');
                jtaLog.append("Player 1's IP address" +
                        player1.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 1
                DataOutputStream dt1 = new DataOutputStream(
                        player1.getOutputStream());
                dt1.writeInt(PLAYER1);

                // Connect to player 2
                Socket player2 = serverSocket.accept();

                jtaLog.append(new Date() +
                        ": Player 2 joined session " + sessionNo + '\n');
                jtaLog.append("Player 2's IP address" +
                        player2.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 2
                DataOutputStream dt2 = new DataOutputStream(
                        player2.getOutputStream());
                dt2.writeInt(PLAYER2);

                // Display this session and increment session number
                jtaLog.append(new Date() + ": Start a thread for session " +
                        sessionNo++ + '\n');

                // Create a new task for this session of two players
                HandleSession task = new HandleSession(player1 ,player2);
                // notify Player1 to start
//         new DataOutputStream(
//           player1.getOutputStream()).writeInt(CONTINUE);

                // Start the
                // new thread
                new Thread(task).start();
                    if (task.getStatus() != 0) {
//                        new DataOutputStream(
//                                player2.getOutputStream()).writeInt(task.getStatus());
//
//                        new DataOutputStream(
//                                player1.getOutputStream()).writeInt(task.getStatus());
                    }

            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
    }


}
class HandleSession implements Runnable, RockPaperScissorConstants
{
    private Socket player1;
    private Socket player2;
    private DataInputStream fromPlayer1;
    private DataOutputStream toPlayer1;
    private DataInputStream fromPlayer2;
    private DataOutputStream toPlayer2;
    private int status;

    /**
     * Construct a thread
     */
    public HandleSession(Socket player1, Socket player2)
    {

        this.player1 = player1;
        this.player2 = player2;


    }
//    public HandleSession(DataOutputStream d1, DataOutputStream d2){
//        this.d1 = d1;
//        this.d2 = d2;
//    }

    /**
     */
    public void run()
    {
        try
        {
            // Create data input and output streams
            ObjectInputStream fromPlayer1 = new ObjectInputStream(
                    player1.getInputStream());
            DataOutputStream toPlayer1 =
                    new DataOutputStream(
                    player1.getOutputStream());
            ObjectInputStream fromPlayer2 = new ObjectInputStream(
                    player2.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(
                    player2.getOutputStream());


            // Write anything to notify player 1 to start
            // This is just to let player 1 know to start

            //toPlayer1.writeInt(CONTINUE);
            //toPlayer2.writeInt(CONTINUE);


            // Continuously serve the players and determine and report
            // the game status to the players
            while (true)
            {
                // Receive a move from players
                Object choice1 = (Object) fromPlayer1.readObject();
                Object choice2 = (Object) fromPlayer2.readObject();

                System.out.println("!!!"+choice1.getNaam());
                System.out.println("!!!"+choice2.getNaam());

                // Check if Player 1 wins

                System.out.println(isWon(choice1, choice2));
                System.out.println("writing Data" );
                if (isWon(choice1, choice2) == PLAYER1_WON) {
                    status = PLAYER1_WON;
//                    System.out.println(1);
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(PLAYER1_WON);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(PLAYER1_WON);
                    toPlayer1.writeInt(PLAYER1_WON);
                    toPlayer2.writeInt(PLAYER1_WON);
                    break; // Break the loop
                } else if (isWon(choice1, choice2) == DRAW)
                { // Check if all cells are filled
                    status = DRAW;
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(DRAW);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(DRAW);
                    toPlayer1.writeInt(DRAW);
                    System.out.println("S: 1");
                    toPlayer2.writeInt(DRAW);
                    System.out.println("S: 2");

//                    d1.writeInt(DRAW);
//                    d2.writeInt(DRAW);

//                    System.out.println(3);
                    break;
                } else if (isWon(choice1, choice2) == PLAYER2_WON)
                {
                    status = PLAYER2_WON;
                    // Notify player 2 to take the turn
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(PLAYER2_WON);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(PLAYER2_WON);

                    toPlayer1.writeInt(PLAYER2_WON);
                    toPlayer2.writeInt(PLAYER2_WON);
//                    System.out.println(2);
                    break;

                }


            }
        } catch (IOException ex)
        {
            System.err.println(ex);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void sendMove(DataOutputStream out, int row, int column)
            throws IOException
    {
        out.write(row); // Send row index
        out.writeInt(column); // Send column index
    }
    /** Determine if the cells are all occupied */

    /**
     * Determine if the player with the specified token wins
     */

    public int isWon(Object one, Object two)
    {
        if (one.getClass().getName().equals(two.getClass().getName()))
            return 3;
        else if (one instanceof Papier && two instanceof Steen)
            return 1;
        else if (one instanceof Steen && two instanceof Schaar)
            return 1;
        else if (one instanceof Schaar && two instanceof Papier)
            return 1;
        else
            return 2;
    }

    public int getStatus(){
        return status;
    }
}