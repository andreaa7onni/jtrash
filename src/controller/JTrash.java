package controller;

import model.Game;
import model.Player;
import view.EndGamePanel;
import view.GameDlg;
import view.GamePanel;
import view.MenuFrm;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type J trash.
 * This class contains the methods to control the main objects of the application.
 * It handles the interactions between Model and View
 */
public class JTrash {

    private Player player;
    private Game game;
    private GamePanel gamePanel;
    private GameDlg gameDlg;

    /**
     * Instantiates a new JTrash with default player
     */
    public JTrash() {
        this.setPlayer(new Player("Player", "avatar1.png"));
    }

    public static void main(String[] args) {
        new MenuFrm(new JTrash());
    }

    /**
     * Sets game panel.
     *
     * @param a GamePanel
     */
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param an istance of Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets game.
     *
     * @param an istance of Game
     */
    public void setGame(Game mygame) {
        game = mygame;
    }

    /**
     * Run game. 
     * Open the game dialog and starts the game.
     */
    public void runGame() {
		gameDlg = new GameDlg(game);
		
        new Thread(this::run).start();

        new Thread(() -> {
			ArrayList<Player> winners = handleGame();
            try {
                modifyStats(winners);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
			if(winners.contains(player)) {
				gameDlg.addEndGamePanel(new EndGamePanel(gameDlg, true));
				AudioManager.getInstance().play("/resources/sound/applausi.wav");
			}
			else {
				gameDlg.addEndGamePanel(new EndGamePanel(gameDlg, false));
				AudioManager.getInstance().play("/resources/sound/fine.wav");

			}
        }).start();
    }

    private void modifyStats(ArrayList<Player> winners) throws IOException {
        try {
            player.readStats().addfinishedGames();
            player.setStats(player.getStats());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (winners.contains(player)) {
            player.readStats().addGamesWon();
            player.setStats(player.getStats());
        } else {
            player.readStats().addlostGames();
            player.setStats(player.getStats());
        }
    }

    /**
     * Handle game.
     * It handle the game, starts rounds and decreases the hand of the players
     *
     * @return the array list that contains the players who have won the game
     */
    private ArrayList<Player> handleGame() {
        ArrayList<Player> winners = new ArrayList<>();
        boolean endGame = false;

        System.out.println("La partita sta per iniziare...\n");

        game.getTable().getDeckChoice().mixDeck();

        for (int i = 0; i < game.getPlayers().length; i++) {
            game.getTable().deliverHand(game.getTable().getDeckChoice(), game.getTable().getPlayersHand()[i]);
        }

        game.getTable().getDiscardCard().push(game.getTable().getDeckChoice().deliverCard());

        GamePanel gamePanel = new GamePanel(game.getTable());
        gamePanel.InsertComponents();
        setGamePanel(gamePanel);

        gameDlg.addGamePanel(gamePanel);
        gameDlg.setVisible(true);
        gamePanel.setStatusLabel(game.getPlayers()[0].getNickname() + " la partita sta per iniziare ...!");

        int round = 1;

        try {
            player.readStats().addGamesStarted();
            player.setStats(player.getStats());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (!endGame) {
            System.out.println("Round " + round);
            boolean[] roundWinners = playRound(gamePanel);

            for (int i = 0; i < roundWinners.length; i++) {

                if (roundWinners[i]) {

                    int newCardsNumber = game.getTable().decreaseCards(i);

                    if (newCardsNumber == 0) {
                        endGame = true;
                        winners.add(game.getPlayers()[i]);
                        break;
                    }
                }
            }

            if (!endGame) {
                game.getTable().resetTable();
                gamePanel.resetGamePanel();
                round++;
            } else {
				gamePanel.setVisible(false);
			}
        }

        return winners;
    }

    /**
     * Play round.
     * It plays every round of the game and modify at the same time the table and the game panel
     *
     * @param the game panel
     * @return the an array of boolean which, if in position i array[i] == true, it means that the player in position i won the round
     */
    private boolean[] playRound(GamePanel gamePanel) {

        boolean[] roundWinners = new boolean[game.getPlayers().length];
        boolean trash = false;

        gamePanel.viewDeliverCards();

        wait(2000);
        gamePanel.uncoverFirstCard();

        int lastPlayer = -1;

        while (!trash) {

            for (int i = 0; i < game.getPlayers().length; i++) {

                System.out.println(game.getPlayers()[i].getNickname() + " è il tuo turno\n");
                gamePanel.setStatusLabel(game.getPlayers()[i].getNickname() + " è il tuo turno!");
                gamePanel.setActivePlayer(i);

                //gamePanel.underlinePlayerLabel(i);

                playTurn(i);

                trash = game.getTable().checkTrash(i);

                waitForDiscardCard();
                game.getTable().discardCard();
                gamePanel.viewDiscardCard();

                if (trash) {
					AudioManager.getInstance().play("/resources/sound/trash.wav");
					wait(500);
                    System.out.println("Trash!\n");
                    gamePanel.setStatusLabel("Trash!");
                    roundWinners[i] = true;
                    lastPlayer = i;
                    break;
                }

                game.getTable().printTable();
            }
        }

        playLastTurn(lastPlayer, roundWinners);

        return roundWinners;
    }

    private void playTurn(int i) {
        gamePanel.underlinePlayerLabel(i);

        if (i == 0) {
            gamePanel.resetChoiceButton();

            int drawChoice = waitDrawnCard();
            game.getTable().playerDrawCard(drawChoice);
            gamePanel.viewDrawCard();

            boolean[] moves = game.getTable().findMove(i);
            while (hasMove(moves)) {

                int position = waitPlaceCard(moves);
                game.getTable().placeCard(i, position);
                gamePanel.viewPlaceCard(i, position);
                moves = game.getTable().findMove(i);
                game.getTable().printTable();
            }

            System.out.println("Non hai più mosse\n");
            gamePanel.setStatusLabel(game.getPlayers()[i].getNickname() + " non hai più mosse!");
            wait(1000);
        } else {

            boolean[] movesDiscardCard = game.getTable().findMove(i);

            game.getTable().cpuDrawCard(hasMove(movesDiscardCard));
            wait(1000);
            gamePanel.viewDrawCard();

            boolean[] cpuMoves = game.getTable().findMove(i);
            while (hasMove(cpuMoves)) {

                int cpuPosition = cpuFindPosition(cpuMoves);
                game.getTable().placeCard(i, cpuPosition);
                wait(1000);
                gamePanel.viewPlaceCard(i, cpuPosition);
                cpuMoves = game.getTable().findMove(i);
            }
        }
    }

    /**
     * Play last turn.
     * it plays the turn when someone do trash. Only the remaining players play the turn
     *
     * @param the player who do trash
     * @param the boolean array round winners
     */
    private void playLastTurn(int lastPlayer, boolean[] roundWinners) {

        int[] remainingPlayers = new int[game.getTable().getPlayers().length - 1];
		wait(1600);

        for (int i = 1; i <= remainingPlayers.length; i++) {
            int add = i + lastPlayer;
            if (add >= game.getTable().getPlayers().length) {
                remainingPlayers[i - 1] = add - game.getTable().getPlayers().length;
            } else {
                remainingPlayers[i - 1] = add;
            }
        }

        for (int remainingPlayer : remainingPlayers) {
            playTurn(remainingPlayer);
            boolean trash = game.getTable().checkTrash(remainingPlayer);

            waitForDiscardCard();
            game.getTable().discardCard();
            gamePanel.viewDiscardCard();

            if (trash) {
                System.out.println("Trash!\n");
                roundWinners[remainingPlayer] = true;
            }
        }
    }

    private int cpuFindPosition(boolean[] cpuMoves) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < cpuMoves.length; i++) {
            if (cpuMoves[i]) {
                positions.add(i);
            }
        }
        int index = (int) (Math.random() * positions.size());
        return positions.get(index);
    }

    private void waitForDiscardCard() {
		wait(1000);

    }

    private int waitPlaceCard(boolean[] moves) {
        System.out.println("Posiziona la carta\n");
        gamePanel.setEnablePlace(true);
        gamePanel.setStatusLabel(game.getPlayers()[gamePanel.getActivePlayer()].getNickname() + " posiziona la carta ...");
        int positionChoice;
        while (true) {
            positionChoice = gamePanel.getPositionPlace();
            if (positionChoice != -1 && moves[positionChoice]) {
                break;
            }
            wait(100);
        }
        gamePanel.setEnablePlace(false);
        return positionChoice;
    }

    private boolean hasMove(boolean[] moves) {
        for (boolean move : moves) {
            if (move) {
                return true;
            }
        }
        return false;
    }

    /**
     * Wait drawn card.
     *
     * @return the int who identify the deck to draw
     */
    private int waitDrawnCard() {
        System.out.println("Pesca una carta\n");
        gamePanel.setEnableDraw(true);
        gamePanel.setStatusLabel(game.getPlayers()[gamePanel.getActivePlayer()].getNickname() + " pesca una carta ...");
        int drawChoice;
        while (true) {
            drawChoice = gamePanel.getDeckChoice();
            if (drawChoice != -1) {
                break;
            }
            wait(100);
        }
        return drawChoice;
    }

    /**
     * Wait.
     * it blocks the execution for a specified time
     *
     * @param the time
     */
    protected void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        gameDlg.setVisible(true);
    }
}
