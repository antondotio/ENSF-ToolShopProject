package Frontend.GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Frontend.Controller.*;

public class CustomerFrame extends JFrame {
    /**
     * Serial Version UID
     */
    public static final long serialVersionUID = 1L;
    /**
     * Button that will list all items on click
     */
    private JButton listAll, search, buyItem;
    /**
     * The panels in the main frame
     */
    private JPanel p, options, center;
    /**
     * The text area where the items will be listed
     */
    private JTextArea textArea;
    /**
     * Scrollbar for the text area
     */
    private JScrollPane scroll;
    /**
     * Title for the application
     */
    private JLabel title;
    /**
     * listener for the button
     */
    private Listener listener;

    public CustomerFrame(int width, int height) {
        checkLogin();
        setTitle("ToolShop");
        setSize(width, height);
        p = new JPanel();
        options = new JPanel();
        center = new JPanel();
        textArea = new JTextArea();
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        title = new JLabel("Welcome to Anton and Julian's Toolshop");

        setAttributes();
        setPanels();
        createButtons();

        setContentPane(p);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void checkLogin(){
        Login login = new Login(listener);
    }

    /**
     * Sets the layouts of the panels
     */
    public void setAttributes() {
        title.setPreferredSize(new Dimension(100, 25));
        title.setHorizontalAlignment(JLabel.CENTER);

        p.setLayout(new BorderLayout());
        center.setLayout(new BorderLayout());
        options.setLayout(new FlowLayout());

        textArea.setEditable(false);
    }

    /**
     * Adds the panels to the main frame.
     */
    public void setPanels() {
        p.add("South", options);
        p.add("Center", center);
        p.add("North", title);

        center.add("Center", scroll);
    }

    /**
     * Creates the buttons and adds the action listener
     */
    public void createButtons() {
        listAll = new JButton("List All Items");
        listAll.addActionListener(new ActionListener() {
            /**
             * Anonymous listener that will execute the action when the button is pressed
             * 
             * @param e The event to listen to
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String listOfTools = listener.actionPerformed("GET/TOOL/LIST"); // Sends a string to the socket for the
                // server to hear.
                if (listOfTools.equals("") || listOfTools.equals("Error getting list of tools")) {
                    System.err.println("Error getting list of tools");
                } else {
                    textArea.setEditable(true);
                    textArea.setText(listOfTools);
                    textArea.setEditable(false);
                }

            }
        });
        options.add(listAll);

        search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            /**
             * Anonymous listener that will execute the action when the button is pressed
             * 
             * @param e The event to listen to
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Enter the name or ID of the item you are looking for.");
                if (search == null) {
                    return;
                }
                String toolSearched = listener.actionPerformed("GET/TOOL/SEARCH-" + search); // Sends a string to the
                                                                                             // socket for the
                // server to hear.
                if (toolSearched.equals("") || toolSearched.equals("Error searching of tool")) {
                    System.err.println("Error searching for tool");
                } else {
                    JOptionPane.showMessageDialog(null, toolSearched, "Item found", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        options.add(search);

        buyItem = new JButton("Buy Item");
        buyItem.addActionListener(new ActionListener() {
            /**
             * Anonymous listener that will execute the action when the button is pressed
             * 
             * @param e The event to listen to
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog(
                        "Enter the name of the item you wish to buy\nand the quantity, separated by a dash. Example: Inny Outies-34.");
                if (itemName == null) {
                    return;
                }
                String decrease = listener.actionPerformed("TOOL/DECREASE-" + itemName); // Sends a string to the socket
                                                                                         // for the
                // server to hear.
                String[] split = itemName.split("-");
                String name = split[0];
                if (decrease.equals("") || decrease.equals("Error decreasing item quantity")) {
                    System.err.println("Error decreasing item quantity");
                } else if (decrease.equals("Not enough quantity")) {
                    JOptionPane.showMessageDialog(null,
                            "The quantity of " + name + " is not high enough to be decreased.", "Item Quantity ",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, ("Item quantity of " + name + " has been decreased."),
                            "Item Decreased", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        options.add(buyItem);
    }

    public void setListener(Listener l) {
        listener = l;
    }
}