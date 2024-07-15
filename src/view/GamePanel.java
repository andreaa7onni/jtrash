package view;

import model.Card;
import model.Table;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The type Game panel.
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 5103244202734995703L;
	private final Table table;
    private final JLabel[] playerLbl;
    private final JButton[][] handLbl;
    private final JLabel[] avatarLbl;
    private final JLabel statusLabel = new JLabel("");
    private final GridBagLayout gridBagLayout = new GridBagLayout();
    /**
     * The Background.
     */
    Color background = new Color(90, 140, 110);
    /**
     * The name players coordinates.
     */
    int[][] xyNamePlayers = {{11, 11}, {1, 6}, {11, 1}, {24, 6}};
    /**
     * The avatar coordinates.
     */
    int[][] xyAvatar = {{11, 10}, {1, 5}, {11, 0}, {24, 5}};
    /**
     * The first row of cards coordinates.
     */
    int[][] xyCards1 = {{9, 8}, {3, 3}, {13, 3}, {22, 7}};
    /**
     * The second row of cards coordinates.
     */
    int[][] xyCards2 = {{9, 9}, {2, 3}, {13, 2}, {23, 7}};
    /**
     * The deck coordinates.
     */
    int[] xyDeck = {12, 5};
    /**
     * The discard card coordinates.
     */
    int[] xyDiscardCard = {10, 5};
    /**
     * The card to play coordinates.
     */
    int[] xyCardToPlay = {11, 6};
    /**
     * The width of a card.
     */
    int widthCard = 48;
    /**
     * The height of a card.
     */
    int heightCard = 60;
    /**
     * The width of aavatar.
     */
    int widthAvatar = 50;
    /**
     * The height of an avatar.
     */
    int heightAvatar = 50;
    private JButton deckLbl;
    private JButton discardLbl;
    private JButton cardToPlayLbl;
    private int activePlayer;
    private boolean enableDraw = false;
    private boolean enablePlace = false;
    private int deckChoice = -1;
    private int positionPlace = -1;
    private GridBagConstraints gbc;

    /**
     * Instantiates a new Game panel.
     *
     * @param the game table
     */
    public GamePanel(Table table) {
        setBackground(background);
        this.table = table;
        this.playerLbl = new JLabel[table.getPlayers().length];
        this.handLbl = new JButton[table.getPlayersHand().length][];
        this.avatarLbl = new JLabel[table.getPlayers().length];

        setComponents();
    }

    public void setStatusLabel(String status) {
        this.statusLabel.setText(status);
    }

    /**
     * Uncover first card.
     */
    public void uncoverFirstCard() {
        discardLbl.setVisible(true);
    }

    /**
     * Gets an ImageIcon.
     * returns an image of given size from a path, looking for it in cardImages folder
     *
     * @param imageName the image name
     * @param dimW      the dim w
     * @param dimH      the dim h
     * @return the image icon
     */
    public ImageIcon getImageIcon(String imageName, int dimW, int dimH) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/cardImages/" + imageName));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(dimW, dimH, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
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


    /**
     * Sets components.
     * instantiate the components of the panel
     */
    public void setComponents() {
        for (int i = 0; i < table.getPlayers().length; i++) {
            playerLbl[i] = new JLabel(table.getPlayers()[i].getNickname());

            avatarLbl[i] = new JLabel(getAvatarIcon(table.getPlayers()[i].getAvatar(), widthAvatar, heightAvatar));
            avatarLbl[i].setPreferredSize(new Dimension(widthAvatar, heightAvatar));

            handLbl[i] = new JButton[10];
            for (int j = 0; j < table.getPlayersHand()[i].getcards().length; j++) {
                setVoidButton(i);
                handLbl[i][j] = new JButton(getImageIcon(table.getPlayersHand()[i].getcards()[j].toImg(), widthCard, heightCard));
                handLbl[i][j].setPreferredSize(new Dimension(widthCard, heightCard));
                handLbl[i][j].setContentAreaFilled(false);
            }
        }

        deckLbl = new JButton(getImageIcon("RETRO.jpg", widthCard, heightCard));
        deckLbl.setPreferredSize(new Dimension(widthCard, heightCard));
        deckLbl.setContentAreaFilled(false);

        Card discardTop = table.getDiscardCard().pop();
        discardTop.uncoverCard();
        discardLbl = new JButton(getImageIcon(discardTop.toImg(), widthCard, heightCard));
        discardLbl.setPreferredSize(new Dimension(widthCard, heightCard));
        table.getDiscardCard().push(discardTop);
        discardLbl.setContentAreaFilled(false);

        cardToPlayLbl = new JButton();
        cardToPlayLbl.setPreferredSize(new Dimension(widthCard, heightCard));
        cardToPlayLbl.setContentAreaFilled(false);
    }

    /**
     * setVoidButton.
     * it set void button in each empty card position
     */

    private void setVoidButton(int i) {
        if (table.getPlayersHand()[i].getcardsNum() < 10) {
            for (int j = table.getPlayersHand()[i].getcardsNum(); j < 10; j++) {
                handLbl[i][j] = new JButton();
                handLbl[i][j].setPreferredSize(new Dimension(widthCard, heightCard));
                handLbl[i][j].setOpaque(false);
                handLbl[i][j].setContentAreaFilled(false);
                handLbl[i][j].setBorderPainted(false);
            }
        }
    }

    /**
     * Setup Card on table.
     */
    private void setupCard(List<JButton[]> card) {
        setLayout(gridBagLayout);
        gbc = new GridBagConstraints();

        for (int i = 0; i < card.size(); i++) {
            int x = xyCards1[i][0];
            int y = xyCards1[i][1];
            for (int j = 0; j < card.get(i).length; j++) {
                if (j >= 5) {
                    switch (i) {
                        case 0 -> {
                            x = xyCards2[i][0] + (j - 5);
                            y = xyCards2[i][1];
                        }
                        case 1 -> {
                            x = xyCards2[i][0];
                            y = xyCards2[i][1] + (j - 5);
                        }
                        case 2 -> {
                            x = xyCards2[i][0] - (j - 5);
                            y = xyCards2[i][1];
                        }
                        case 3 -> {
                            x = xyCards2[i][0];
                            y = xyCards2[i][1] - (j - 5);
                        }
                    }
                }
                gbc.gridx = x;
                gbc.gridy = y;
                gbc.insets = new Insets(3, 3, 3, 3);
                if (i == 1 || i == 3) {
                    gbc.anchor = GridBagConstraints.PAGE_END;
                }
                add(card.get(i)[j], gbc);
                gbc.anchor = GridBagConstraints.CENTER;
                card.get(i)[j].setVisible(false);

                switch (i) {
                    case 0 -> x++;
                    case 1 -> y++;
                    case 2 -> x--;
                    case 3 -> y--;
                }
            }
        }

        for (int j = 0; j < card.get(0).length; j++) {
            card.get(0)[j].addActionListener(e -> {
                if (enablePlace) {
                    for (int i = 0; i < card.get(0).length; i++) {
                        if (e.getSource() == card.get(0)[i]) {
                            setPositionPlace(i);
                            System.out.println("posizione cliccata: " + i);
                        }
                    }
                }
            });
        }
    }

    /**
     * Setup Players on table.
     */
    private void setupPlayer(List<JLabel> players) {
        setLayout(gridBagLayout);
        gbc = new GridBagConstraints();

        for (int i = 0; i < players.size(); i++) {
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 5, 0, 5);
            gbc.anchor = GridBagConstraints.CENTER;

            int x = xyAvatar[i][0];
            int y = xyAvatar[i][1];
            gbc.gridx = x;
            gbc.gridy = y;
            add(avatarLbl[i], gbc);

            x = xyNamePlayers[i][0];
            y = xyNamePlayers[i][1];
            gbc.gridx = x;
            gbc.gridy = y;

            if (i == 1 || i == 3) {
                gbc.anchor = GridBagConstraints.PAGE_START;
                gbc.gridwidth = 1;
            }

            add(players.get(i), gbc);
        }
    }

    /**
     * Insert components.
     * add components to layout
     */
    public void InsertComponents() {
        setLayout(gridBagLayout);
        gbc = new GridBagConstraints();

        addSpacement();

        AtomicInteger indexCard = new AtomicInteger();
        AtomicInteger indexPlayer = new AtomicInteger();

        List<JButton[]> card = Arrays.stream(handLbl)
                .map(i -> indexCard.getAndIncrement())
                .map(i -> handLbl[i])
                .collect(Collectors.toList());
        setupCard(card);

        List<JLabel> players = Arrays.stream(playerLbl)
                .map(i -> indexPlayer.getAndIncrement())
                .map(i -> playerLbl[i])
                .collect(Collectors.toList());
        setupPlayer(players);

        int x = xyDeck[0];
        int y = xyDeck[1];
        gbc.gridx = x;
        gbc.gridy = y;
        add(deckLbl, gbc);

        deckLbl.addActionListener(e -> {
            if (enableDraw) {
                setDeckChoice(0);
            }

        });

        gbc.gridx = xyDiscardCard[0];
        gbc.gridy = xyDiscardCard[1];
        add(discardLbl, gbc);
        discardLbl.setVisible(false);

        discardLbl.addActionListener(e -> {
            if (enableDraw) {
                setDeckChoice(1);
            }

        });

        gbc.gridx = xyCardToPlay[0];
        gbc.gridy = xyCardToPlay[1];
        add(cardToPlayLbl, gbc);
        cardToPlayLbl.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;
        gbc.anchor = GridBagConstraints.PAGE_START;
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusLabel.setFont(new java.awt.Font("Helvetica", Font.BOLD, 20));
        statusLabel.setForeground(Color.DARK_GRAY);
        add(statusLabel, gbc);
    }



    /**
     * Add Spacement.
     */
    private void addSpacement() {
        int x = 4;
        int y = 4;
        for (int i = 0; i < 15; i++) {

            gbc.gridx = x;
            gbc.gridy = y;

            JButton Spacement = new JButton();
            Spacement.setPreferredSize(new Dimension(widthCard, heightCard));
            Spacement.setOpaque(false);
            Spacement.setContentAreaFilled(false);
            Spacement.setBorderPainted(false);
            add(Spacement, gbc);

            x++;
        }

    }

    /**
     * Sets enable draw.
     *
     * @param enableDraw the enable draw
     */
    public void setEnableDraw(boolean enableDraw) {
        this.enableDraw = enableDraw;
    }

    /**
     * Sets enable place.
     *
     * @param enablePlace the enable place
     */
    public void setEnablePlace(boolean enablePlace) {
        this.enablePlace = enablePlace;
    }


    /**
     * Gets position place.
     *
     * @return the position place
     */
    public int getPositionPlace() {
        return positionPlace;
    }


    /**
     * Sets position place.
     *
     * @param positionPlace the position place
     */
    public void setPositionPlace(int positionPlace) {
        this.positionPlace = positionPlace;
    }

    /**
     * View place card.
     * show to video a card placement
     *
     * @param player   the player
     * @param position the position
     */
    public void viewPlaceCard(int player, int position) {

        ImageIcon iconCardHand = getImageIcon(table.getPlayersHand()[player].getcards()[position].toImg(), widthCard, heightCard);
        ImageIcon iconCardToPlay = getImageIcon(table.getCardToPlay().toImg(), widthCard, heightCard);

        cardToPlayLbl.setIcon(iconCardToPlay);
        handLbl[player][position].setIcon(iconCardHand);

        revalidate();
    }

    /**
     * View discard card.
     * show to video a card discard
     */
    public void viewDiscardCard() {

        Card discardCard = table.getDiscardCard().pop();
        ImageIcon iconDiscardCard = getImageIcon(discardCard.toImg(), widthCard, heightCard);
        table.getDiscardCard().push(discardCard);
        discardLbl.setIcon(iconDiscardCard);
        revalidate();

        cardToPlayLbl.setVisible(false);
        discardLbl.setVisible(true);
        revalidate();

    }

    /**
     * View draw card.
     * show to video a card draw
     */
    public void viewDrawCard() {

        if (!table.getDiscardCard().isEmpty()) {
            Card discardCard = table.getDiscardCard().pop();
            ImageIcon iconDiscardCard = getImageIcon(discardCard.toImg(), widthCard, heightCard);
            discardLbl.setIcon(iconDiscardCard);
            table.getDiscardCard().push(discardCard);
        } else {
            discardLbl.setVisible(false);
        }

        cardToPlayLbl.setIcon(getImageIcon(table.getCardToPlay().toImg(), widthCard, heightCard));
        cardToPlayLbl.setVisible(true);

        revalidate();
    }

    /**
     * Gets deck choice.
     *
     * @return the deck choice
     */
    public int getDeckChoice() {
        return deckChoice;
    }


    /**
     * Sets deck choice.
     *
     * @param deckChoice the deck choice
     */
    public void setDeckChoice(int deckChoice) {
        this.deckChoice = deckChoice;
    }

    /**
     * Reset choice button.
     */
    public void resetChoiceButton() {
        setDeckChoice(-1);
        setPositionPlace(-1);
    }

    /**
     * View deliver cards.
     */
    public void viewDeliverCards() {
        for (JButton[] jButtons : handLbl) {
            for (JButton jButton : jButtons) {
                // if void card
                wait(300);
                jButton.setVisible(true);
            }
        }
    }

    /**
     * Wait.
     * it blocks the execution for a specified time
     *
     * @param time the time
     */
    protected void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reset game panel.
     */
    public void resetGamePanel() {
        removeAll();
        revalidate();
        setComponents();
        InsertComponents();
        setVisible(true);
        revalidate();
    }

    /**
     * Underline player label.
     *
     * @param i the
     */
    public void underlinePlayerLabel(int i) {

        for (int j = 0; j < playerLbl.length; j++) {
            if (j == i) {
                playerLbl[j].setForeground(new Color(255, 255, 0));
            } else {
                playerLbl[j].setForeground(new Color(0, 0, 0));
            }
        }
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int i) {
        activePlayer = i;
    }
}
