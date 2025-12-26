package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This window allows an authenticated organiser to:
 * <ul>
 *     <li>Select an existing race from the database</li>
 *     <li>Modify its name, date, and location</li>
 *     <li>Upload new photos</li>
 *     <li>Delete the race</li>
 * </ul>
 *
 * @version 1.0
 */
public class FenetreOrganisateurModifier extends FenetreBase {

    /** Factory to generate backend requests */
    private final RequestFactory reqFactory;

    /** Photos selected by the user for upload */
    private File[] selectedPhotos;

    /**
     * Constructor for the Modify Course window.
     * @param conn Active connection to the backend
     * @throws ConnectionErrorException if connection fails
     * @throws AuthentificationFailedException if organiser not authenticated
     * @throws UnauthorizedAccessException if organiser lacks access
     */
    public FenetreOrganisateurModifier(ConnectionHandler conn)
            throws ConnectionErrorException, AuthentificationFailedException, UnauthorizedAccessException {
        super(conn, "Modifier une course");
        this.reqFactory = conn.createRequestFactory();

        /* ---------- UI Components ---------- */

        JLabel titreLabel = new JLabel("Modifier une course");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setForeground(new Color(5, 23, 75));

        JComboBox<CourseItem> combo = new JComboBox<>();
        combo.setMaximumSize(new Dimension(400, 40));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField champNom = createTextField("Nom de la course");
        JTextField champDate = createTextField("Date");
        JTextField champLieu = createTextField("Lieu");

        JButton boutonPhotos = createButton("Ajouter des photos");
        JButton boutonModifier = createButton("Enregistrer les modifications");
        JButton boutonSupprimer = createButton("Supprimer la course");

        /* ---------- Layout Composition ---------- */
        panelDroite.add(titreLabel);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(combo);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(champNom);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(champDate);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(champLieu);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(boutonPhotos);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(boutonModifier);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(boutonSupprimer);
        panelDroite.add(Box.createVerticalGlue());

        /* ---------- Fill ComboBox with Courses ---------- */
        conn.setRequest(reqFactory.createCourseListRequest());
        conn.execute();
        CourseListResponse res = (CourseListResponse) conn.getResponse();
        for (CourseData c : res.getCourseList()) {
            combo.addItem(new CourseItem(c));
        }

        /* ---------- Event Handlers ---------- */

        // When a course is selected, populate fields
        combo.addActionListener(e -> {
            CourseData course = ((CourseItem) combo.getSelectedItem()).getCourse();
            champNom.setText(course.getName());
            champDate.setText(course.getDate());
            champLieu.setText(course.getPlace());
        });

        // Upload new photos for selected course
        boutonPhotos.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedPhotos = fileChooser.getSelectedFiles();
                DataFactory df = conn.createDataFactory();
                CourseData course = ((CourseItem) combo.getSelectedItem()).getCourse();
                ArrayList<PhotoData> photos = new ArrayList<>();
                for (File f : selectedPhotos) {
                    try {
                        byte[] content = Files.readAllBytes(f.toPath());
                        photos.add(df.createPhotoData(course.getDate(), 0, 0, content));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Impossible de lire la photo : " + f.getAbsolutePath(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }

                conn.setRequest(reqFactory.createPhotoUploadRequest(photos, course));
                try {
                    conn.execute();
                    JOptionPane.showMessageDialog(this, selectedPhotos.length + " photo(s) ajoutée(s).");
                } catch (UnauthorizedAccessException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Vous n'êtes pas autorisé à ajouter des photos pour cette course.");
                } catch (ConnectionErrorException | AuthentificationFailedException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Erreur de connexion lors de l’ajout des photos : " + ex.getMessage());
                }
            }
        });

        // Modify selected course
        boutonModifier.addActionListener(e -> {
            if (combo.getSelectedItem() != null) {
                String nom = champNom.getText().trim();
                String date = champDate.getText().trim();
                String lieu = champLieu.getText().trim();

                if (nom.isEmpty() || date.isEmpty() || lieu.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez remplir tous les champs.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CourseData oldCourse = ((CourseItem) combo.getSelectedItem()).getCourse();
                CourseData newCourse = conn.createDataFactory().createCourseData(
                        oldCourse.getId(), nom, lieu, date);

                conn.setRequest(reqFactory.createModifCourseRequest(oldCourse, newCourse));
                try {
                    conn.execute();
                    JOptionPane.showMessageDialog(this, "Modifications enregistrées !");
                    combo.removeItemAt(combo.getSelectedIndex());
                    combo.insertItemAt(new CourseItem(newCourse), combo.getSelectedIndex());
                } catch (UnauthorizedAccessException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Vous n’êtes pas autorisé à modifier cette course.");
                } catch (ConnectionErrorException | AuthentificationFailedException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur de connexion lors de la modification.");
                }
            }
        });

        // Delete selected course
        boutonSupprimer.addActionListener(e -> {
            if (combo.getSelectedItem() != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer cette course ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    CourseData course = ((CourseItem) combo.getSelectedItem()).getCourse();
                    conn.setRequest(reqFactory.createRemoveCourseRequest(course));
                    try {
                        conn.execute();
                        JOptionPane.showMessageDialog(this, "Course supprimée !");
                        combo.removeItemAt(combo.getSelectedIndex());
                    } catch (UnauthorizedAccessException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Vous n’êtes pas autorisé à supprimer cette course !");
                    } catch (ConnectionErrorException | AuthentificationFailedException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Erreur de connexion lors de la suppression.");
                    }
                }
            }
        });

        setVisible(true);
        combo.setSelectedIndex(0); // Trigger initial fill
    }

    /**
     * Utility method to build a styled text field.
     */
    private static JTextField createTextField(String title) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(400, 30));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        tf.setBorder(BorderFactory.createTitledBorder(title));
        return tf;
    }

    /**
     * Utility method to build a styled button.
     */
    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(400, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    /**
     * Wrapper class for displaying a course in a JComboBox.
     */
    private static class CourseItem {
        private final CourseData course;
        public CourseItem(CourseData course) {
            this.course = course;
        }
        public CourseData getCourse() {
            return course;
        }
        @Override
        public String toString() {
            return course.getName();
        }
    }
}
