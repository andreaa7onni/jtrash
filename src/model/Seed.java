package model;

/**
 * The enum Seed.
 */
public enum Seed {

    JOLLY(0), CUORI(1), QUADRI(2), FIORI(3), PICCHE(4);

    /**
     * The Semi.
     */
    static final Seed[] semi = Seed.values();
    private final int value;

    Seed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * To seed value int.
     *
     * @return the int
     */
    public int toSeedValue() {
        return value;
    }

}
