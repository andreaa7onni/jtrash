package model;

/**
 * The type Hand.
 */
public class Hand {

    private final Card[] cards;
    private int cardNum;

    /**
     * Instantiates a new Hand.
     *
     * @param numCarte the num carte
     */
    public Hand(int numCarte) {
        this.cardNum = numCarte;
        this.cards = new Card[numCarte];
    }

    /**
     * Decrease the number of cards.
     */
    public void decreaseCards() {
        cardNum = getcardsNum() - 1;
    }

    /**
     * Gets num.
     *
     * @return the number of cards
     */
    public int getcardsNum() {
        return cardNum;
    }

    /**
     * Getcards card [ ].
     *
     * @return the card [ ]
     */
    public Card[] getcards() {
        return cards;
    }

    /**
     * Add card card.
     *
     * @param position
     * @param card     
     * @return the card
     */
    public Card addCard(int position, Card card) {
        Card appoggio = cards[position];
        cards[position] = card;
        return appoggio;
    }

    /**
     * Show hand.
     *
     * @return string representation of an hand
     */
    public String showHand() {
        String manoStr = "| ";
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].isCoveredCard()) {
                manoStr = manoStr + "*";
            } else {
                manoStr = manoStr + cards[i].toString();
            }
            manoStr = manoStr + " | ";
        }
        return manoStr;
    }

}
