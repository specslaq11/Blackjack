import java.util.ArrayList;
import java.util.List;

public class Player {
    protected List<Card> hand;
    private int balance;
    private int bet;

    public Player(int balance) {
        this.balance = balance;
        hand = new ArrayList<>();
    }

    public void placeBet(int amount) {
        if (amount <= balance) {
            bet = amount;
            balance -= amount;
        } else {
            System.out.println("Insufficient balance to place the bet.");
        }
    }

    public void winBet() {
        balance += bet * 2; // Player wins double the bet
    }

    public void drawBet() {
        balance += bet; // Return the bet amount
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int handValue = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
            handValue += card.getValue();
        }

        while (handValue > 21 && aceCount > 0) {
            handValue -= 10; // Adjusting Ace from 11 to 1
            aceCount--;
        }

        return handValue;
    }

    public void clearHand() {
        hand.clear();
    }

    public int getBalance() {
        return balance;
    }

    public Card getCard(int index) {
        if (index < hand.size()) {
            return hand.get(index);
        }
        return null;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand); // Returns a copy of the hand list
    }

    public void displayHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Total Value: " + getHandValue());
    }
}
