package Frontend.GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Frontend.Controller.*;

public class Login extends JFrame {
    /**
     * Serial Version UID
     */
    public static final long serialVersionUID = 1L;

    private JButton login, newUser;
    /**
     * The panels in the main frame
     */
    private JPanel p, options;

    private JTextField username;
    private JPasswordField password;

    private JLabel user, pass;

    /**
     * listener for the button
     */
    private Listener listener;

    public Login(Listener l) {
        listener = l;
        setSize(400, 100);
        setTitle("Login");
        p = new JPanel();
        user = new JLabel("Username");
        pass = new JLabel("Password");
        username = new JTextField(10);
        password = new JPasswordField(10);
        newUser = new JButton("Register New User");

        setPanels();
        setButtons();

        setContentPane(p);
        setVisible(true);
    }

    public void setPanels() {
        p.add(user);
        p.add(username);
        p.add(pass);
        p.add(password);
    }

    public void setButtons() {
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uN = username.getText();
                String pW = password.getText();
                if (uN == null || pW == null) {
                    return;
                }
                String logingIn = listener.actionPerformed("LOGIN-" + uN + "-" + pW); // Sends a string to the
                                                                                      // socket for the
                // server to hear.
                if (logingIn.equals("") || logingIn.equals("Invalid")) {
                    JOptionPane.showMessageDialog(null, "Invalid Login Information", "Invalid",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Logged in", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }

        });
        p.add(login);

        p.add(newUser);
    }

}
