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
     * 
     * @return list of items
     * @throws IOEexception throws input output exception
     */
    public String displayTools() throws IOException {
        sendString("GET/TOOL/LIST");
        String response = socketIn.readLine();
        StringBuilder list = new StringBuilder();
        while (!response.equals("DONE")) {
            list.append(response);
            list.append("\n");
            response = socketIn.readLine();
        }
        return list.toString();
    }

    /**
     * Asks server to retrieve certain item
     * 
     * @param search name or id of item to be searchd
     * @return String of information about items
     * @throws IOException throws input out put exception
     */
    public String searchItem(String search) throws IOException {
        sendString("GET/TOOL/SEARCH");
        sendString(search);
        String response = socketIn.readLine();
        StringBuilder searchResponse = new StringBuilder();
        while (!response.equals("DONE")) {
            searchResponse.append(response);
            searchResponse.append("\n");
            response = socketIn.readLine();
        }
        return searchResponse.toString();
    }

    /**
     * Asks server to return quantity of an item
     * 
     * @param itemQ name or id of item
     * @return String of information about quantity
     * @throws IOException throws input output exception
     */
    public String itemQuantity(String itemQ) throws IOException {
        sendString("GET/TOOL/QUANTITY");
        sendString(itemQ);
        String response = socketIn.readLine();
        StringBuilder searchResponse = new StringBuilder();
        while (!response.equals("DONE")) {
            searchResponse.append(response);
            searchResponse.append("\n");
            response = socketIn.readLine();
        }
        return searchResponse.toString();
    }

    /**
     * Tells server to decrease quantity of item
     * 
     * @param itemName name of item
     * @return status of function
     * @throws IOException throws input output exceptions
     */
    public String decreaseTool(String itemName) throws IOException {
        sendString("TOOL/DECREASE");
        sendString(itemName);
        String response = socketIn.readLine();
        StringBuilder decreaseResponse = new StringBuilder();
        while (!response.equals("DONE")) {
            decreaseResponse.append(response);
            decreaseResponse.append("\n");
            response = socketIn.readLine();
        }
        return decreaseResponse.toString();
    }

    public String login(String loginParam) throws IOException {
        sendString("LOGIN");
        sendString(loginParam);
        String response = socketIn.readLine();
        StringBuilder loginResponse = new StringBuilder();
        while (!response.equals("DONE")) {
            loginResponse.append(response);
            loginResponse.append("\n");
            response = socketIn.readLine();
        }
        return loginResponse.toString();
    }

    /**
     * Sends string to server and flushes it after
     * 
     * @param s string to be sent
     */
    public void sendString(String s) {
        socketOut.println(s);
        socketOut.flush();
    }

}