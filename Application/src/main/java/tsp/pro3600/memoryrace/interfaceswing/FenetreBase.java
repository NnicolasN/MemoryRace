package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This abstract class defines the base layout for all application windows.
 * It includes a common structure with a logo panel on the left and a customizable panel on the right.
 * All windows extending this class will inherit the logo and layout behavior.
 *
 * @version 1.0
 */
public abstract class FenetreBase extends JFrame {

    /** The connection handler shared across the application */
    protected ConnectionHandler conn;

    /** The right panel intended for content in child classes */
    protected JPanel panelDroite;

    /**
     * Constructs a base window with a default clickable logo.
     * @param conn The database connection handler.
     * @param titre The title of the window.
     */
    public FenetreBase(ConnectionHandler conn, String titre) {
        this(conn, titre, true); // By default, logo is clickable
    }

    /**
     * Constructs a base window with optional clickable logo.
     * @param conn The database connection handler.
     * @param titre The title of the window.
     * @param logoClickable Whether the logo should return to the home page when clicked.
     */
    public FenetreBase(ConnectionHandler conn, String titre, boolean logoClickable) {
        this.conn = conn;

        // Set up window basics
        setTitle(titre);
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load and configure the logo on the left
        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("logo.png");
        Image image = logo.getImage().getScaledInstance(300, -1, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(image));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setVerticalAlignment(JLabel.CENTER);

        // Make the logo clickable if requested and connection is available
        if (logoClickable && conn != null) {
            logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            logoLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    try {
                        new FenetreAcceuil(conn);
                        dispose(); // Close current window
                    } catch (ConnectionErrorException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(FenetreBase.this, "Error while returning to the home page.");
                    }
                }
            });
        }

        // Panel for the logo
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(logoLabel, BorderLayout.CENTER);
        panelGauche.setBackground(Color.WHITE);

        // Main panel that combines left and right panels
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelGauche, BorderLayout.WEST);

        // Right panel to be filled by subclasses
        panelDroite = new JPanel();
        panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));
        panelDroite.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        panelDroite.setBackground(new Color(24, 203, 233, 129));

        panelPrincipal.add(panelDroite, BorderLayout.CENTER);
        add(panelPrincipal);
    }
}
