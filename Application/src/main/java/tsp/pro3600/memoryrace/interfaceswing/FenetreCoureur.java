package tsp.pro3600.memoryrace.interfaceswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class represents the runner interface window of the MemoryRace application.
 * It allows the user to select a race and enter their bib number to search for photos.
 * Inherits layout and logo from {@link FenetreBase}.
 *
 * @version 1.0
 */
public class FenetreCoureur extends FenetreBase {

    /** Factory to create request objects for the database interaction */
    private RequestFactory reqFactory;

    /**
     * Constructs the runner window.
     *
     * @param conn The active database connection
     * @throws ConnectionErrorException If there is a failure during communication
     * @throws AuthentificationFailedException If authentication fails
     * @throws UnauthorizedAccessException If access is not authorized
     */
    public FenetreCoureur(ConnectionHandler conn) throws ConnectionErrorException, AuthentificationFailedException, UnauthorizedAccessException {
        super(conn, "MemoryRace - Coureur");
        this.reqFactory = conn.createRequestFactory();

        // === Instruction text ===
        JLabel instructionLabel = new JLabel("Trouvez vos photos !");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setForeground(new Color(5, 23, 75)); // Dark blue

        // === Dropdown list for race selection ===
        JComboBox<CourseItem> combo = new JComboBox<>();
        combo.setMaximumSize(new Dimension(300, 40));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Text field for bib number ===
        JTextField texte = new JTextField("N° Dossard");
        texte.setMaximumSize(new Dimension(300, 40));
        texte.setAlignmentX(Component.CENTER_ALIGNMENT);
        texte.setFont(new Font("Arial", Font.PLAIN, 16));

        // === Button to trigger photo search ===
        JButton bouton = new JButton("Trouver mes photos");
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setMaximumSize(new Dimension(300, 50));

        // === Load races from database into the combo box ===
        CourseListRequest reqCourseList = reqFactory.createCourseListRequest();
        conn.setRequest(reqCourseList);
        conn.execute();
        CourseListResponse resCourseList = (CourseListResponse) conn.getResponse();
        for (CourseData course : resCourseList.getCourseList()) {
            combo.addItem(new CourseItem(course));
        }

        // === Add components to the right panel ===
        panelDroite.add(instructionLabel);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 40)));
        panelDroite.add(combo);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(texte);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(bouton);
        panelDroite.add(Box.createVerticalGlue());

        // === Action for the search button ===
        bouton.addActionListener(e -> {
            try {
                CourseItem selectedCourse = (CourseItem) combo.getSelectedItem();
                String dossard = texte.getText().trim();

                // Create and execute the photo request
                PhotoWithDossardRequest reqPhotos = reqFactory.createPhotoWithDossardRequest(
                        Integer.parseInt(dossard), selectedCourse.getCourse());
                conn.setRequest(reqPhotos);
                conn.execute();

                // Display the result in a new window
                PhotoListResponse resPhotos = (PhotoListResponse) conn.getResponse();
                new FenetrePhotos(conn, resPhotos.getPhotoList());
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la recherche de photos.");
            }
        });

        // === Show the window ===
        setVisible(true);
    }

    /**
     * Helper class to wrap CourseData in a readable way for the JComboBox.
     */
    private class CourseItem {
        private CourseData course;

        public CourseItem(CourseData course) {
            this.course = course;
        }

        @Override
        public String toString() {
            return course.getName();
        }

        public CourseData getCourse() {
            return course;
        }
    }
}
