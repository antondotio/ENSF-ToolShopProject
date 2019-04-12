package Frontend;

import Frontend.GUI.*;
import Frontend.Controller.*;

/**
 * This application simulates a simple toolshop application that uses
 * client-server architechture. The start of the client side of the application.
 * Instantiates the client and the main frame.
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client("localhost", 9788);
        Frame mainFrame = new Frame(600, 400);
        mainFrame.setListener(new Listener(client));
    }
}
