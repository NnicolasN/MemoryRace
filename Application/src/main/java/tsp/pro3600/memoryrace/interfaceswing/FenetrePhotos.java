package tsp.pro3600.memoryrace.interfaceswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class displays a photo gallery where the runner can browse photos
 * one by one and enlarge them by clicking.
 * It inherits the base layout from {@link FenetreBase}, including the logo and layout styling.
 *
 * @version 1.1
 */
public class FenetrePhotos extends FenetreBase {

    /** List of all photos retrieved from the server */
    private final ArrayList<PhotoData> photos;

    /** Index of the currently displayed photo */
    private int currentIndex = 0;

    /** Label that shows the photo on screen */
    private JLabel photoLabel;

    /**
     * Creates and displays the photo gallery window.
     *
     * @param conn   The active connection to the backend
     * @param photos The list of photos to display
     */
    public FenetrePhotos(ConnectionHandler conn, ArrayList<PhotoData> photos) {
        super(conn, "MemoryRace - Galerie de Photos");
        this.photos = photos;

        // Use BorderLayout for displaying large photo and nav buttons
        panelDroite.setLayout(new BorderLayout());

        // === Photo Display Area ===
        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoLabel.setVerticalAlignment(JLabel.CENTER);
        photoLabel.setBackground(new Color(24, 203, 233, 129));
        photoLabel.setOpaque(true);

        // Add click-to-enlarge behavior
        photoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        photoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLargeImage(new ImageIcon(photos.get(currentIndex).getImageBytes()));
            }
        });

        // === Buttons ===
        JButton boutonTelecharger = new JButton("⬇ Télécharger");
        boutonTelecharger.setPreferredSize(new Dimension(150, 40));

        JButton boutonPrecedent = new JButton("◀");
        boutonPrecedent.setPreferredSize(new Dimension(60, 40));

        JButton boutonSuivant = new JButton("▶");
        boutonSuivant.setPreferredSize(new Dimension(60, 40));

        // === Navigation Panel (Bottom bar) ===
        JPanel panelNavigation = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelNavigation.setBackground(new Color(24, 203, 233, 129));
        panelNavigation.add(boutonTelecharger);
        panelNavigation.add(boutonPrecedent);
        panelNavigation.add(boutonSuivant);

        // Add to layout
        panelDroite.add(photoLabel, BorderLayout.CENTER);
        panelDroite.add(panelNavigation, BorderLayout.SOUTH);

        // === Button Actions ===
        boutonPrecedent.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updatePhoto();
            }
        });

        boutonSuivant.addActionListener(e -> {
            if (currentIndex < photos.size() - 1) {
                currentIndex++;
                updatePhoto();
            }
        });

        boutonTelecharger.addActionListener(e -> {
            PhotoData currentPhoto = photos.get(currentIndex);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("photo.jpg"));
            int result = fileChooser.showSaveDialog(FenetrePhotos.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(currentPhoto.getImageBytes());
                    JOptionPane.showMessageDialog(this, "Photo enregistrée avec succès !");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la photo.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Initial display
        updatePhoto();
        setVisible(true);
    }

    /**
     * Updates the photo currently shown on screen based on {@code currentIndex}.
     */
    private void updatePhoto() {
        if (photos == null || photos.isEmpty()) return;

        PhotoData photo = photos.get(currentIndex);
        ImageIcon icon = new ImageIcon(photo.getImageBytes());

        int largeur = panelDroite.getWidth() > 0 ? panelDroite.getWidth() : 400;
        int hauteur = panelDroite.getHeight() > 0 ? panelDroite.getHeight() - 80 : 300;

        Image img = icon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(img));
    }

    /**
     * Opens a new window to display the clicked photo in enlarged view.
     *
     * @param icon The icon to display in large size
     */
    private void showLargeImage(ImageIcon icon) {
        JFrame frame = new JFrame("Image Agrandie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);

        Image scaled = icon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaled));
        frame.add(label);

        frame.setVisible(true);
    }
}
