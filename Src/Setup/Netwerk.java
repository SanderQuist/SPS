package Setup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Netwerk
{
    private String netwerk;

    public Netwerk(){
        if(netwerk == "Server")
        {
            server();
        }
        else client();
    }

    private void server(){
        int port = 8000;
        DataInputStream in;
        DataOutputStream out;
        ServerSocket server;
        Socket socket;

        try
        {
            server = new ServerSocket(port);
            socket = server.accept();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println(in.readDouble());
            out.writeDouble(20);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void client(){
        int port = 8000;
        String host = "localhost";
        DataInputStream in;
        DataOutputStream out;
        Socket socket;

        try
        {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeDouble(10);
            System.out.println(in.readDouble());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
