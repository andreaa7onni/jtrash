package view;

import model.Stats;

import javax.swing.*;
import java.awt.*;

/**
 * The type Stats frm.
 */
public class StatsFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
     * The Stats.
     */
    Stats stats;

    /**
     * Instantiates a new Stats framem.
     *
     * @param stats 
     */
    public StatsFrm(Stats stats) {

        super("Statistiche");
        this.stats = stats;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] labels = new JLabel[]{new JLabel("Partite Iniziate: " + stats.getGamesStarted()), new JLabel("Partite Terminate: " + stats.getFinishedGames()), new JLabel("Partite Vinte: " + stats.getGamesWon()), new JLabel("Partite Perse: " + stats.getLostGames())};

        gbc.gridx = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i;
            add(labels[i], gbc);
        }


        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

}
