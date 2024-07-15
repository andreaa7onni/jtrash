package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import static java.awt.Color.ORANGE;

public class EndGamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JDialog GameDlg;
	JLabel message;
	JButton close;
	
	
	public EndGamePanel(JDialog GameDlg, boolean outcome) {
		
		this.GameDlg = GameDlg;
		
		setPreferredSize(new Dimension(600,300));
		setBackground(ORANGE);

		if (outcome == true) {
			message = new JLabel("CONGRATULAZIONI! HAI VINTO.");
		} else {
			message = new JLabel("PECCATO! HAI PERSO.");
		}

		close = new JButton("Chiudi");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(0, 0, 20, 0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(message, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(close, gbc);
		
		close.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameDlg.dispose();
			}
		});
		
	}



}
