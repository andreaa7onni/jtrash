package view;

import controller.JTrash;
import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Setup game frm.
 */
public class SetupGameFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JComboBox<String> playerNumberSelection;

    /**
     * Instantiates a new Setup game frame.
     *
     * @param jTrash the j trash
     */
    public SetupGameFrm(JTrash jTrash) {
        super("Nuova Partita");

        // components
        JLabel playersNumber = new JLabel("Numero di Giocatori: ");
        String[] opzioniGiocatori = {"2", "3", "4"};
        this.playerNumberSelection = new JComboBox<String>(opzioniGiocatori);
        JButton startGame = new JButton("Inizia Partita");

        // layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playersNumber, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(playerNumberSelection, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(startGame, gbc);

        // event listener
        startGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Player[] giocatori = new Player[]{};
                switch (Integer.parseInt((String) playerNumberSelection.getSelectedItem())) {
                    case 2 ->
                            giocatori = new Player[]{jTrash.getPlayer(), new Player("CPU1", Player.getRandomAvatar())};
                    case 3 ->
                            giocatori = new Player[]{jTrash.getPlayer(), new Player("CPU1", Player.getRandomAvatar()), new Player("CPU2", Player.getRandomAvatar())};
                    case 4 ->
                            giocatori = new Player[]{jTrash.getPlayer(), new Player("CPU1", Player.getRandomAvatar()), new Player("CPU2", Player.getRandomAvatar()),
                                    new Player("CPU3", Player.getRandomAvatar())};
                }

                dispose();
                Game partita = new Game(giocatori);
                jTrash.setGame(partita);
                jTrash.runGame();
            }
        });

        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

}
