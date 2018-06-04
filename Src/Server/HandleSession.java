package Server;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;
import Setup.RockPaperScissorConstants;
import sun.security.provider.NativePRNG;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.*;
import java.net.Socket;

class HandleSession implements Runnable, RockPaperScissorConstants
{
    private Socket player1;
    private Socket player2;

    /**
     * Construct a thread
     */
    public HandleSession(Socket player1, Socket player2)
    {
        this.player1 = player1;
        this.player2 = player2;


    }

    /**
     */
    public void run()
    {
        try
        {
            // Create data input and output streams
            ObjectInputStream fromPlayer1 = new ObjectInputStream(
                    player1.getInputStream());
            
            DataOutputStream toPlayer1 = new DataOutputStream(
                    player1.getOutputStream());
            ObjectInputStream fromPlayer2 = new ObjectInputStream(
                    player2.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(
                    player2.getOutputStream());

            // Write anything to notify player 1 to start
            // This is just to let player 1 know to start

            toPlayer1.writeInt(CONTINUE);
            toPlayer2.writeInt(CONTINUE);


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
                if (isWon(choice1, choice2) == 1) {
                    System.out.println(1);
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(PLAYER1_WON);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(PLAYER1_WON);
                    toPlayer1.writeInt(PLAYER1_WON);
                    toPlayer2.writeInt(PLAYER1_WON);
                    break; // Break the loop
                } else if (isWon(choice1, choice2) == DRAW)
                { // Check if all cells are filled
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(DRAW);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(DRAW);
                    toPlayer1.writeInt(DRAW);
                    toPlayer2.writeInt(DRAW);
                    System.out.println(3);
                    break;
                } else if (isWon(choice1, choice2) == PLAYER2_WON)
                {
                    // Notify player 2 to take the turn
//                    new DataOutputStream(
//                            player1.getOutputStream()).writeInt(PLAYER2_WON);
//                    new DataOutputStream(
//                            player2.getOutputStream()).writeInt(PLAYER2_WON);
                    toPlayer1.writeInt(PLAYER2_WON);
                    toPlayer2.writeInt(PLAYER2_WON);
                    System.out.println(2);
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
}

