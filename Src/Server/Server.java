package Server;

import Setup.RockPaperScissorConstants;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        setTitle("TicTacToeServer");
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
                new DataOutputStream(
                        player1.getOutputStream()).writeInt(PLAYER1);

                // Connect to player 2
                Socket player2 = serverSocket.accept();

                jtaLog.append(new Date() +
                        ": Player 2 joined session " + sessionNo + '\n');
                jtaLog.append("Player 2's IP address" +
                        player2.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 2
                new DataOutputStream(
                        player2.getOutputStream()).writeInt(PLAYER2);

                // Display this session and increment session number
                jtaLog.append(new Date() + ": Start a thread for session " +
                        sessionNo++ + '\n');

                // Create a new task for this session of two players
                HandleSession task = new HandleSession(player1, player2);

                // notify Player1 to start
//         new DataOutputStream(
//           player1.getOutputStream()).writeInt(CONTINUE);

                // Start the new thread
                new Thread(task).start();
            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
    }
}