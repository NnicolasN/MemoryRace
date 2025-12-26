package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This window allows the organiser to choose between:
 * <ul>
 *     <li>Creating a new race</li>
 *     <li>Modifying an existing race</li>
 * </ul>
 * It inherits the base window layout from {@link FenetreBase}, including the clickable logo.
 *
 * @version 1.0
 */
public class FenetreOrganisateurQuestion extends FenetreBase {

    /** Factory for building backend requests */
    private final RequestFactory reqFactory;

    /**
     * Constructor to display the organiser's main menu.
     *
     * @param conn The active connection to the backend
     * @throws ConnectionErrorException if connection setup fails
     */
    public FenetreOrganisateurQuestion(ConnectionHandler conn) throws ConnectionErrorException {
        super(conn, "MemoryRace - Organisateur");
        this.reqFactory = conn.createRequestFactory();

        // Title / Question
        JLabel questionLabel = new JLabel("Vous voulez... ?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setForeground(new Color(5, 23, 75));

        // Create Course Button
        JButton boutonCreer = new JButton("Créer une course");
        boutonCreer.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonCreer.setMaximumSize(new Dimension(200, 50));

        // Modify Course Button
        JButton boutonModifier = new JButton("Modifier une course");
        boutonModifier.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonModifier.setMaximumSize(new Dimension(200, 50));

        // Add components to inherited right panel
        panelDroite.add(questionLabel);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 40)));
        panelDroite.add(boutonCreer);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(boutonModifier);
        panelDroite.add(Box.createVerticalGlue());

        // Event: Navigate to course creation window
        boutonCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new FenetreOrganisateurCreer(conn);
                    dispose();
                } catch (ConnectionErrorException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FenetreOrganisateurQuestion.this,
                            "Erreur de connexion !");
                }
            }
        });

        // Event: Navigate to course modification window
        boutonModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new FenetreOrganisateurModifier(conn);
                    dispose();
                } catch (ConnectionErrorException | AuthentificationFailedException | UnauthorizedAccessException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FenetreOrganisateurQuestion.this,
                            "Erreur de connexion !");
                }
            }
        });

        setVisible(true);
    }
}
