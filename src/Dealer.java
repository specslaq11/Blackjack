public class Dealer extends Player {
    public Dealer() {
        super(0); // Dealer doesn't need a balance
    }

    @Override
    public void displayHand() {
        // Display only the first card and hide the second card initially
        if (!hand.isEmpty()) {
            System.out.println(getCard(0) + " and [hidden]");
        }
    }

    public void revealHand() {
        // When it's time to reveal the hand
        super.displayHand(); // Use the display method from the Player class
    }

    public void play(Deck deck) {
        // Dealer must hit until their total is at least 17
        while (getHandValue() < 17) {
            addCard(deck.dealCard());
        }
    }
}
