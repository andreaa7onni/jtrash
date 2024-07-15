package model;

public enum CardValue {

    JOLLY(0),
    ASSO(1),
    DUE(2),
    TRE(3),
    QUATTRO(4),
    CINQUE(5),
    SEI(6),
    SETTE(7),
    OTTO(8),
    NOVE(9),
    DIECI(10),
    JACK(11),
    DONNA(12),
    RE(13);

    static final CardValue[] values = CardValue.values();

    private final int value;

    CardValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int toValue() {
        return value;
    }

}
