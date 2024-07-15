package model;

import java.util.Arrays;

/**
 * The type Deck.
 */
public class Deck {

    private final int CARDSNUMBER = 54;
    private int decksNum;
    private int actualCards;
    private Card[] deck = new Card[CARDSNUMBER * decksNum];

    /**
     * Instantiates a new Deck.
     *
     * @param the number of decks
     */
    public Deck(int decks) {
        this.decksNum = decks;
        if (decks < 1 || decks > 2) {
            System.out.println("numero di mazzi non disponibile");
        } else {
            Card[] myDeck = new Card[CARDSNUMBER * decks];
            for (int i = 0; i < decks; i++) {
                for (int k = 1; k <= 4; k++) {
                    for (int j = 1; j <= 13; j++) {
                        myDeck[i * CARDSNUMBER + (k - 1) * 13 + (j - 1)] = new Card(Seed.semi[k], CardValue.values[j]);
                    }
                }
                myDeck[CARDSNUMBER * (i + 1) - 1] = new Card(Seed.JOLLY, CardValue.JOLLY);
                myDeck[CARDSNUMBER * (i + 1) - 2] = new Card(Seed.JOLLY, CardValue.JOLLY);
            }
            this.deck = myDeck;
            this.actualCards = CARDSNUMBER * decks;
        }
    }

    /**
     * Mix deck.
     * place every card of the deck in a random position
     */
    public void mixDeck() {
        for (int i = 0; i < deck.length; i++) {
            int casuale = (int) (Math.random() * deck.length);
            Card appoggio = deck[casuale];
            deck[casuale] = deck[i];
            deck[i] = appoggio;
        }
    }

    /**
     * Deliver card.
     *
     * @return the card on the top of the deck
     */
    public Card deliverCard() {
        Card card = deck[actualCards - 1];
        deck = Arrays.copyOf(deck, actualCards - 1);
        actualCards--;
        return card;
    }

}
