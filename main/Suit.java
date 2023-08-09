package main;

/**
 * Enum for the suit of a card.
 */
public enum Suit {
    // SPADES("♠"),
    // HEARTS("♥"),
    // CLUBS("♣"),
    // DIAMONDS("♦");
    SPADES("S"),
    HEARTS("H"),
    CLUBS("C"),
    DIAMONDS("D");

    private String label;

    private Suit(String s) {
        this.label = s;
    }

    public String toString() {
        return this.label;
    }
}
