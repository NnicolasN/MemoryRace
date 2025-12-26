package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * Window that lets an organiser create a new race and upload the
 * corresponding photos.
 * <p>
 * The UI follows the global look-and-feel inherited from {@link FenetreBase}:
 * a logo panel on the left and a content panel on the right.
 * </p>
 *
 * @version 1.0
 */
public class FenetreOrganisateurCreer extends FenetreBase {

    /** Factory used to create backend requests */
    private final RequestFactory reqFactory;
    /** Photos chosen by the organiser before upload */
    private File[] selectedPhotos;

    /**
     * Constructs the “Create Race” window.
     *
     * @param conn Active backend connection (must already be authenticated)
     * @throws ConnectionErrorException if the request factory cannot be created
     */
    public FenetreOrganisateurCreer(ConnectionHandler conn) throws ConnectionErrorException {
        super(conn, "Créer une course");
        this.reqFactory = conn.createRequestFactory();

        /* ---------- Input fields ---------- */

        JTextField champNom  = createTextField("Nom de la course");
        JTextField champDate = createTextField("Date (ex: 2025-05-01)");
        JTextField champLieu = createTextField("Lieu");

        /* ---------- Action buttons ---------- */

        JButton boutonSelectionPhotos = new JButton("Sélectionner les photos");
        boutonSelectionPhotos.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonSelectionPhotos.setMaximumSize(new Dimension(400, 40));

        JButton boutonCreer = new JButton("Créer la course");
        boutonCreer.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonCreer.setMaximumSize(new Dimension(400, 40));

        /* ---------- Layout composition ---------- */

        panelDroite.add(champNom);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(champDate);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDroite.add(champLieu);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(boutonSelectionPhotos);
        panelDroite.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDroite.add(boutonCreer);
        panelDroite.add(Box.createVerticalGlue());

        /* ---------- Select photos action ---------- */
        boutonSelectionPhotos.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = chooser.showOpenDialog(FenetreOrganisateurCreer.this);

            if (res == JFileChooser.APPROVE_OPTION) {
                selectedPhotos = chooser.getSelectedFiles();
                JOptionPane.showMessageDialog(FenetreOrganisateurCreer.this,
                        selectedPhotos.length + " photo(s) sélectionnée(s).");
            }
        });

        /* ---------- Create race action ---------- */
        boutonCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nom  = champNom.getText().trim();
                String date = champDate.getText().trim();
                String lieu = champLieu.getText().trim();

                /* Basic validation */
                if (nom.isEmpty() || date.isEmpty() || lieu.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this,
                            "Veuillez remplir tous les champs.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                /* ---------- Build data objects ---------- */
                DataFactory dataFactory = conn.createDataFactory();
                ArrayList<PhotoData> photos = new ArrayList<>();

                /* Read each selected photo into memory */
                if (selectedPhotos != null) {
                    for (File fi : selectedPhotos) {
                        try {
                            byte[] bytes = Files.readAllBytes(fi.toPath());
                            photos.add(dataFactory.createPhotoData(date, 0, 0, bytes));
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(
                                    FenetreOrganisateurCreer.this,
                                    "Impossible de lire la photo : " + fi.getAbsolutePath(),
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                CourseData course = dataFactory.createCourseData(nom, lieu, date);
                RequestFactory rf = conn.createRequestFactory();

                /* The organiser who is currently connected */
                ArrayList<OrganisateurData> organisateurs = new ArrayList<>();
                organisateurs.add(conn.getAuthentifiedOrganisateur());

                /* ---------- 1. Create the race ---------- */
                conn.setRequest(rf.createAddCourseRequest(course, organisateurs));
                try {
                    conn.execute();
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this, "Course créée !");
                } catch (UnauthorizedAccessException ex) {
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this,
                            "Accès refusé : vous n’êtes pas autorisé à créer une course.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (ConnectionErrorException | AuthentificationFailedException ex) {
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this,
                            "Erreur de connexion : " + ex.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                /* ---------- 2. Upload photos ---------- */
                CourseData createdCourse =
                        ((AddCourseResponse) conn.getResponse()).getCourse();

                conn.setRequest(rf.createPhotoUploadRequest(photos, createdCourse));
                try {
                    conn.execute();
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this, "Photos ajoutées !");
                } catch (UnauthorizedAccessException ex) {
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this,
                            "Vous devez être organisateur de la course pour ajouter des photos.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ConnectionErrorException | AuthentificationFailedException ex) {
                    JOptionPane.showMessageDialog(
                            FenetreOrganisateurCreer.this,
                            "Erreur lors de l’ajout des photos : " + ex.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    /**
     * Utility method to create a styled text field.
     *
     * @param title the border title
     * @return configured {@link JTextField}
     */
    private static JTextField createTextField(String title) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(400, 30));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        tf.setBorder(BorderFactory.createTitledBorder(title));
        return tf;
    }
}
