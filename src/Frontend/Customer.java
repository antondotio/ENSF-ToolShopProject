
package Frontend;

import Frontend.Controller.*;
import Frontend.GUI.*;

public class Customer {

    public static void main(String[] args) {
        Client client = new Client("localhost", 8897);
        CustomerFrame customerFrame = new CustomerFrame(600, 400);
        customerFrame.setListener(new Listener(client));
    }
}