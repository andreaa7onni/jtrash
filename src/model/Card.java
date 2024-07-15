package model;

/**
 * The type Card.
 */
public class Card {

    private final Seed seed;
    private final CardValue cardValue;
    private boolean coveredCard;

    /**
     * Instantiates a new Card.
     *
     * @param the seed of the card
     * @param the value of the card
     */
    public Card(Seed seed, CardValue value) {
        this.seed = seed;
        this.cardValue = value;
        this.coveredCard = true;
    }

    /**
     * Gets seme.
     *
     * @return the seme
     */
    public Seed getSeed() {
        return seed;
    }

    /**
     * Gets card value.
     *
     * @return the card value
     */
    public CardValue getCardValue() {
        return cardValue;
    }

    public String toString() {
        if (this.cardValue == CardValue.JOLLY) {
            return ("JOLLY");
        } else {
            return (cardValue + "DI" + seed);
        }
    }

    /**
     * Uncover card.
     */
    public void uncoverCard() {
        this.coveredCard = false;
    }

    /**
     * Is covered card.
     * returns if a card is covered or not
     *
     * @return the boolean
     */
    public boolean isCoveredCard() {
        return coveredCard;
    }

    /**
     * To img.
     *
     * @return the name of the corresponding jpg card name
     */
    public String toImg() {
        if (isCoveredCard()) {
            return "RETRO.jpg";
        } else {
            return this + ".jpg";
        }
    }

}
