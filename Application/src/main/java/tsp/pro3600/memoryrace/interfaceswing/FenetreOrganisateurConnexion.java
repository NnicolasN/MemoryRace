package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class represents the login interface for organizers.
 * It provides a form for entering an email and password, and performs authentication
 * against the backend system using the provided {@link ConnectionHandler}.
 *
 * Inherits layout and logo handling from {@link FenetreBase}.
 *
 * @version 1.0
 */
public class FenetreOrganisateurConnexion extends FenetreBase {

    /**
     * Constructs the login window for organizers.
     *
     * @param conn The active connection handler to the backend
     */
    public FenetreOrganisateurConnexion(ConnectionHandler conn) {
        super(conn, "Connexion Organisateur");

        // === Title label ===
        JLabel titreLabel = new JLabel("Connexion Organisateur");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setForeground(new Color(5, 23, 75));

        // === Email input field ===
        JTextField emailField = new JTextField("Email");
        emailField.setMaximumSize(new Dimension(300, 40));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));

        // === Password input field ===
        JPasswordField passwordField = new JPasswordField("Mot de passe");
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        // === Login button ===
        JButton loginButton = new JButton("Se connecter");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(300, 50));

        // === Add components to the right panel (inherited from FenetreBase) ===
        panelDroite.add(titreLabel);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 40)));
        panelDroite.add(emailField);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(passwordField);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(loginButton);
        panelDroite.add(Box.createVerticalGlue());

        // === Login button action: try to authenticate and redirect if successful ===
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                // Create authentication data and request
                DataFactory dataFactory = conn.createDataFactory();
                AuthentificationData authData = dataFactory.createAuthentificationData(email, password);
                RequestFactory reqFactory = conn.createRequestFactory();
                conn.setRequest(reqFactory.createAuthentificationRequest(authData));

                try {
                    conn.execute(); // Tries to authenticate
                    new FenetreOrganisateurQuestion(conn); // Success: go to organizer menu
                    dispose(); // Close login window
                }
                catch (AuthentificationFailedException ex) {
                    JOptionPane.showMessageDialog(FenetreOrganisateurConnexion.this, "Email ou mot de passe incorrect.");
                }
                catch (ConnectionErrorException | UnauthorizedAccessException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FenetreOrganisateurConnexion.this, "Erreur de connexion après authentification.");
                }
            }
        });

        // === Show the window ===
        setVisible(true);
    }
}
