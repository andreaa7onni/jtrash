package view;

import controller.JTrash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The type Menu frm.
 */
public class MenuFrm extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
     * Instantiates a new Menu frame.
     *
     * @param jTrash instance
     */
    public MenuFrm(JTrash jTrash) {

        super("JTrash");

        JLabel jTrashLabel = new JLabel(getImageIcon("JTrash.png", 600, 190));
        JLabel imageLabel = new JLabel(getImageIcon("seed.png", 300, 80));

        // buttons
        JButton buttonPartita = new JButton("Nuova Partita");
        int widthButton = 130;
        int heightButton = 28;
        buttonPartita.setPreferredSize(new Dimension(widthButton, heightButton));
        JButton buttonStatistiche = new JButton("Statistiche");
        buttonStatistiche.setPreferredSize(new Dimension(widthButton, heightButton));
        JButton buttonProfilo = new JButton("Profilo");
        buttonProfilo.setPreferredSize(new Dimension(widthButton, heightButton));

        // layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(jTrashLabel, gbc);

        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(imageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(buttonPartita, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(buttonStatistiche, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(buttonProfilo, gbc);

        // action listener
        buttonPartita.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SetupGameFrm(jTrash);
            }
        });

        buttonStatistiche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new StatsFrm(jTrash.getPlayer().readStats());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        buttonProfilo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetupProfileFrm(jTrash);

            }
        });

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    public ImageIcon getImageIcon(String imageName, int dimW, int dimH) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/titleImages/" + imageName));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(dimW, dimH, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

}
