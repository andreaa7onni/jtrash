package view;

import model.Game;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;


/**
 * The type Game dlg.
 */
public class GameDlg extends JDialog {

	private static final long serialVersionUID = 1L;
	/**
     * Instantiates a new Game dialog.
     *
     * @param a game
     */
    public GameDlg(Game mygame) {
        setTitle("Partita");

        // background
        getContentPane().setBackground(new Color(90, 140, 110));

        setSize(1290, 725);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
    }

    /**
     * Add game panel.
     *
     * @param gamePanel the game panel
     */
    public void addGamePanel(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(gamePanel, gbc);

        gbc.gridy = GridBagConstraints.PAGE_END;
    }

	public void addEndGamePanel(EndGamePanel endGamePanel) {
		
		repaint();
		revalidate();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(endGamePanel, gbc);
		endGamePanel.setVisible(true);
		revalidate();
	}
}
