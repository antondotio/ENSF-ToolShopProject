package Frontend.GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Frontend.Controller.*;

/**
 * Creates the main window
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Frame extends JFrame {
    /**
     * Serial Version UID
     */
    public static final long serialVersionUID = 1L;
    /**
     * Button that will list all items on click
     */
    private JButton listAll, search, add, decrease;
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

    /**
     * Constructor for the frame. Sets the size, title, and panels in the frame.
     * 
     * @param width  width of the frame
     * @param height height of the frame
     */
    public Frame(int width, int height) {
        setTitle("ToolShop Application");
        setSize(width, height);
        p = new JPanel();
        options = new JPanel();
        center = new JPanel();
        textArea = new JTextArea();
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        title = new JLabel("Anton and Julian's Toolshop");

        setAttributes();
        setPanels();
        createButtons();

        setContentPane(p);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        options.add(search);
        add = new JButton("Add Item");
        options.add(add);
        decrease = new JButton("Decrease Item");
        options.add(decrease);
    }

    /**
     * Sets the listener
     * 
     * @param l the listener to be set.
     */
    public void setListener(Listener l) {
        listener = l;
    }

}