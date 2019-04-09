package Frontend.Controller;

import java.io.*;
import java.net.Socket;
import java.lang.StringBuilder;

/**
 * The client side of the application. Will send instructions to the server
 * side.
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Client {
    /**
     * The socket that will connect the client and server
     */
    private Socket socket;
    /**
     * To read response from server
     */
    private BufferedReader socketIn;
    /**
     * to send message to server
     */
    private PrintWriter socketOut;

    /**
     * Constructor for the client. Sets the class variables
     * 
     * @param serverName name of server
     * @param portNumber port number to connect to
     */
    public Client(String serverName, int portNumber) {
        try {
            socket = new Socket(serverName, portNumber);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter((socket.getOutputStream()), true);
        } catch (IOException ioe) {
            System.err.println(ioe.getStackTrace());
        }
    }

    // public void communicate(){
    // }

    /**
     * Asks server to get list of items
     * @return list of items
     */
    public String displayTools() throws IOException {
        socketOut.println("GET/TOOL/LIST");
        String response = socketIn.readLine();
        StringBuilder list = new StringBuilder();
        while (!response.equals("DONE")) {
            list.append(response);
            list.append("\n");
            response = socketIn.readLine();
        }
        return list.toString();
    }
}