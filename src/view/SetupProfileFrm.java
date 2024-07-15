package view;

import controller.JTrash;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Setup profile frm.
 */
public class SetupProfileFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JTextField textFieldNickname;
    private final ButtonGroup avatarGroup;
    /**
     * The Width avatar.
     */
    int widthAvatar = 50;
    /**
     * The Height avatar.
     */
    int heightAvatar = 50;

    /**
     * Instantiates a new Setup profile frm.
     *
     * @param jTrash instance
     */
    public SetupProfileFrm(JTrash jTrash) {
        super("Profilo");

        // components
        JLabel labelNickname = new JLabel("Nickname: ");
        this.textFieldNickname = new JTextField(15);
        JLabel avatarLabel = new JLabel("Avatar: ");
        String[] avatarImages = {"avatar1.png", "avatar2.png", "avatar3.png", "avatar4.png", "avatar5.png", "avatar6.png", "avatar7.png", "avatar8.png", "avatar9.png"};
        JRadioButton[] avatarRadioButtons = new JRadioButton[avatarImages.length];
        JLabel[] avatarImagesLabel = new JLabel[avatarImages.length];
        this.avatarGroup = new ButtonGroup();
        JButton saveButton = new JButton("Salva");

        // layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 0, 10, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelNickname, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        add(textFieldNickname, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(avatarLabel, gbc);

        int x = 1;
        int y = 1;
        for (int i = 0; i < avatarImages.length; i++) {
            avatarRadioButtons[i] = new JRadioButton();
            if (i == 0) {
                avatarRadioButtons[i].setSelected(true);
            }
            avatarRadioButtons[i].setActionCommand(avatarImages[i]);
            avatarGroup.add(avatarRadioButtons[i]);
            avatarImagesLabel[i] = new JLabel(getAvatarIcon(avatarImages[i], widthAvatar, heightAvatar));
            gbc.gridx = x;
            gbc.gridy = y;
            add(avatarRadioButtons[i], gbc);
            x++;
            gbc.gridx = x;
            add(avatarImagesLabel[i], gbc);
            switch (i) {
                case 2 -> {
                    x = 1;
                    y++;
                }
                case 5 -> {
                    x = 1;
                    y++;
                }
                default -> x++;
            }

        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                jTrash.setPlayer(new Player(textFieldNickname.getText(), avatarGroup.getSelection().getActionCommand()));
                dispose();

            }
        });

        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    /**
     * Gets avatar icon.
     * returns an image of given size from a path, looking for it in avatarImages folder
     *
     * @param imageName the image name
     * @param dimW      the dim w
     * @param dimH      the dim h
     * @return the avatar icon
     */
    public ImageIcon getAvatarIcon(String imageName, int dimW, int dimH) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/avatarImages/" + imageName));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(dimW, dimH, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

}
