package model;

/**
 * The type Game.
 */
public class Game {

    private final Player[] players;
    private final Table table;

    /**
     * Instantiates a new Game.
     *
     * @param the array containing players
     */
    public Game(Player[] players) {
        this.players = players;
        this.table = new Table(players);
    }

    /**
     * Sets up game.
     * it mixes the deck and deals the cards to the players
     */
    public void setUpGame() {

        table.getDeckChoice().mixDeck();
        for (int i = 0; i < players.length; i++) {
            deliverHand(table.getDeckChoice(), table.getPlayersHand()[i]);
        }
        table.getDiscardCard().push(table.getDeckChoice().deliverCard());

    }

    
    public void deliverHand(Deck deckChoice, Hand hand) {
        for (int i = 0; i < hand.getcardsNum(); i++) {
            hand.addCard(i, deckChoice.deliverCard());
        }
    }


    /**
     * Get players player [ ].
     *
     * @return the player [ ]
     */
    public Player[] getPlayers() {
        return players;
    }


    /**
     * Gets table.
     *
     * @return the table
     */
    public Table getTable() {
        return table;
    }

}
