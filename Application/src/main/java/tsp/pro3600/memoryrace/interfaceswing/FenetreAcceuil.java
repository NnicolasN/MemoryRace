package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class represents the welcome screen of the MemoryRace application.
 * It allows the user to choose whether they are a runner or an organizer.
 * The layout is inherited from {@link FenetreBase}, with a logo on the left and
 * interactive content on the right.
 *
 * @version 1.0
 */
public class FenetreAcceuil extends FenetreBase {

    /** Factory for creating database requests */
    private RequestFactory reqFactory;

    /**
     * Constructs the welcome window.
     *
     * @param conn The database connection handler
     * @throws ConnectionErrorException if the connection fails while creating the request factory
     */
    public FenetreAcceuil(ConnectionHandler conn) throws ConnectionErrorException {
        super(conn, "Bienvenue");
        this.reqFactory = conn.createRequestFactory();

        // === Header question ===
        JLabel questionLabel = new JLabel("Vous êtes... ?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setForeground(new Color(5, 23, 75)); // Dark blue

        // === Runner button ===
        JButton boutonCoureur = new JButton("Coureur");
        boutonCoureur.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonCoureur.setMaximumSize(new Dimension(200, 50));

        // === Organizer button ===
        JButton boutonOrganisateur = new JButton("Organisateur");
        boutonOrganisateur.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonOrganisateur.setMaximumSize(new Dimension(200, 50));

        // === Add components to the right panel ===
        panelDroite.add(questionLabel);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 40))); // Vertical spacing
        panelDroite.add(boutonCoureur);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(boutonOrganisateur);
        panelDroite.add(Box.createVerticalGlue()); // Pushes components to the top

        // === Action for the Runner button ===
        boutonCoureur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new FenetreCoureur(conn); // Opens the runner interface
                    dispose(); // Close welcome screen
                } catch (ConnectionErrorException | AuthentificationFailedException | UnauthorizedAccessException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FenetreAcceuil.this, "Erreur de connexion !");
                }
            }
        });

        // === Action for the Organizer button ===
        boutonOrganisateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreOrganisateurConnexion(conn); // Opens organizer login
                dispose();
            }
        });

        // === Display the window ===
        setVisible(true);
    }
}
