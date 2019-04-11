package Frontend.Controller;

import java.io.IOException;

/**
 * listens for the action performed
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Listener {
    /**
     * client for listener to use
     */
    private Client client;

    /**
     * constructor for listener class. sets the client
     * 
     * @param c client to set
     */
    public Listener(Client c) {
        client = c;
    }

    /**
     * the action that is to be performed
     * 
     * @param s instruction to be executed
     * @return the string to be sent to the frame.
     */
    public String actionPerformed(String s) {
        String[] split = s.split("-", 2);
        if (s.equals("GET/TOOL/LIST")) {
            try {
                return client.displayTools();
            } catch (IOException ioe) {
                return "Error getting list of tools";
            }
        } else if (split[0].equals("GET/TOOL/SEARCH")) {
            try {
                return client.searchItem(split[1]);
            } catch (IOException ioe) {
                return "Error searching for tool";
            }
        } else if (split[0].equals("GET/TOOL/QUANTITY")) {
            try {
                return client.itemQuantity(split[1]);
            } catch (IOException ioe) {
                return "Error searching for tool";
            }
        } else if (split[0].equals("TOOL/DECREASE")) {
            try {
                return client.decreaseTool(split[1]);
            } catch (IOException ioe) {
                return "Error decreasing item quantity";
            }
        }
        return "";
    }
}