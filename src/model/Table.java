package model;

import java.util.Stack;

/**
 * The type Table.
 */
public class Table {

    private final Player[] players;
    private Hand[] playersHand;
    private Deck deckChoice;
    private Stack<Card> discardCards = new Stack<>();
    private Card cardToPlay;

    /**
     * Instantiates a new Table.
     * instantiate the players' hands, set the stack discardCards and by number of players it instantiates a deck by number of players
     *
     * @param the array containing players
     */
    public Table(Player[] players) {

        this.players = players;

        Hand[] hand = new Hand[players.length];
        for (int i = 0; i < hand.length; i++) {
            hand[i] = new Hand(10);
        }
        this.setPlayersHand(hand);

        if (players.length == 2) {
            setDeckChoice(new Deck(1));
        } else if (players.length == 3 || players.length == 4) {
            setDeckChoice(new Deck(2));
        } else {
            System.out.println("Numero di giocatori non consentito");
        }

        setDiscardCard(new Stack<>());
    }

    /**
     * Get players.
     *
     * @return the array containing players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Get players hand.
     *
     * @return the array containing hands
     */
    public Hand[] getPlayersHand() {
        return playersHand;
    }

    /**
     * Sets players hand.
     *
     * @param playersHand the players hand
     */
    public void setPlayersHand(Hand[] playersHand) {
        this.playersHand = playersHand;
    }

    /**
     * Gets deck choice.
     *
     * @return the deck choice
     */
    public Deck getDeckChoice() {
        return deckChoice;
    }

    /**
     * Sets deck choice.
     *
     * @param deckChoice the deck choice
     */
    public void setDeckChoice(Deck deckChoice) {
        this.deckChoice = deckChoice;
    }

    /**
     * Gets discard card.
     *
     * @return the discard card
     */
    public Stack<Card> getDiscardCard() {
        return discardCards;
    }

    /**
     * Sets discard card.
     *
     * @param discardCards the discard cards
     */
    public void setDiscardCard(Stack<Card> discardCards) {
        this.discardCards = discardCards;
    }

    /**
     * Gets card to play.
     *
     * @return the card to play
     */
    public Card getCardToPlay() {
        return cardToPlay;
    }

    /**
     * Sets card to play.
     *
     * @param cardToPlay the card to play
     */
    public void setCardToPlay(Card cardToPlay) {
        this.cardToPlay = cardToPlay;
    }

    /**
     * Print table to console.
     */
    public void printTable() {
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getNickname() + " " + playersHand[i].showHand());
        }
        System.out.println("carta da giocare: " + cardToPlay.toString());

        if (!discardCards.isEmpty()) {
            Card topDiscardCards = discardCards.pop();
            System.out.println("pila scarti: " + topDiscardCards.toString() + "\n");
            discardCards.push(topDiscardCards);
        }
    }

    /**
     * Deliver hand.
     *
     * @param the game deck
     * @param a player's hand
     */
    public void deliverHand(Deck deckChoice, Hand hand) {
        for (int i = 0; i < hand.getcardsNum(); i++) {
            hand.addCard(i, deckChoice.deliverCard());
        }
    }

    /**
     * Find move.
     * calculate the possible moves for a player
     *
     * @param i the player's index
     * @return the possible moves in a boolean[] array
     */
    public boolean[] findMove(int i) {

        boolean[] moves = new boolean[10];
		if (playersHand[i].getcardsNum() == 1 && playersHand[i].getcards()[0].isCoveredCard()) {
            if (cardToPlay.getCardValue().getValue() == 0 ||
                    cardToPlay.getCardValue().getValue() == 13) {
                moves[0] = true;
            }
        } else if (playersHand[i].getcardsNum() > 1) {
            for (int i1 = 0; i1 < playersHand[i].getcardsNum(); i1++) {
                if ((playersHand[i].getcards()[i1].isCoveredCard() &&
                        cardToPlay.getCardValue().getValue() == i1 + 1) ||
                        (playersHand[i].getcards()[i1].isCoveredCard() &&
                                (cardToPlay.getCardValue().getValue() == 0 ||
                                        cardToPlay.getCardValue().getValue() == 13))) {
                    moves[i1] = true;
                }
            }
        }

        return moves;
    }

    /**
     * Place card.
     * places a card, after uncovered it, in a specified position 
     *
     * @param the player who place the card
     * @param the position where to place the card
     */
    public void placeCard(int player, int position) {

        Card tmp = cardToPlay;
        playersHand[player].getcards()[position].uncoverCard();
        setCardToPlay(playersHand[player].getcards()[position]);
        playersHand[player].getcards()[position] = tmp;

    }

    /**
     * Discard card.
     * push a card in the discardCard stack
     */
    public void discardCard() {
        discardCards.push(cardToPlay);
    }

    /**
     * Check trash boolean.
     * it calculates if a player do trash
     *
     * @param the player's index
     * @return true if he do trash, false otherwise
     */
    public boolean checkTrash(int i) {
        for (int j = 0; j < playersHand[i].getcardsNum(); j++) {
            if (playersHand[i].getcards()[j].isCoveredCard()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cpu draw card.
     *
     * @param a boolean array containg the possible moves
     */
    public void cpuDrawCard(boolean hasMove) {
        if (hasMove) {
            cardToPlay = discardCards.pop();
        } else {
            cardToPlay = deckChoice.deliverCard();
            cardToPlay.uncoverCard();
        }
    }

    /**
     * Player draw card.
     *
     * @param the deck to draw
     */
    public void playerDrawCard(int drawChoice) {

        if (drawChoice == 0) {
            setCardToPlay(deckChoice.deliverCard());
            cardToPlay.uncoverCard();
        } else {
            if (!discardCards.isEmpty()) {
                setCardToPlay(discardCards.pop());
            }
        }
    }

    /**
     * Decrease cards.
     * decrease the length of a hand
     *
     * @param the player's index
     * @return the new length of the hand
     */
    public int decreaseCards(int i) {
        getPlayersHand()[i].decreaseCards();
        return getPlayersHand()[i].getcardsNum();
    }

    /**
     * Reset table.
     */
    public void resetTable() {

        if (players.length == 2) {
            setDeckChoice(new Deck(1));
        } else if (players.length == 3 || players.length == 4) {
            setDeckChoice(new Deck(2));
        } else {
            System.out.println("Numero di giocatori non consentito");
        }

        deckChoice.mixDeck();

        for (int i = 0; i < playersHand.length; i++) {
            getPlayersHand()[i] = new Hand(getPlayersHand()[i].getcardsNum());
            deliverHand(deckChoice, getPlayersHand()[i]);
        }

        setDiscardCard(new Stack<>());
        getDiscardCard().push(deckChoice.deliverCard());

    }

}