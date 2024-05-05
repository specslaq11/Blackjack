import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private Player player;
    private Player dealer;

    public Blackjack() {
        deck = new Deck();
        player = new Player(100); // initial balance can be set here
        dealer = new Player(0); // dealer doesn't need a balance
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        while (player.getBalance() > 0) {
            System.out.println("Current balance: " + player.getBalance());
            System.out.print("Place your bet: ");
            int bet = scanner.nextInt();
            player.placeBet(bet);

            dealer.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());

            System.out.println("Your hand:");
            player.displayHand();

            System.out.println("Dealer's first card:");
            System.out.println(dealer.getHand().get(0));

            // Player's turn
            boolean playerTurn = true;
            while (playerTurn) {
                System.out.print("Do you want to hit (1) or stand (2)? ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    player.addCard(deck.dealCard());
                    System.out.println("Your hand:");
                    player.displayHand();
                    if (player.getHandValue() > 21) {
                        System.out.println("Bust! You lose.");
                        playerTurn = false;
                    }
                } else {
                    playerTurn = false;
                }
            }

            // Dealer's turn
            while (dealer.getHandValue() < 17) {
                dealer.addCard(deck.dealCard());
            }

            System.out.println("Dealer's hand:");
            dealer.displayHand();

            if (dealer.getHandValue() > 21 || player.getHandValue() > dealer.getHandValue()) {
                System.out.println("You win!");
                player.winBet();
            } else if (player.getHandValue() == dealer.getHandValue()) {
                System.out.println("It's a draw!");
                player.drawBet();
            } else {
                System.out.println("Dealer wins!");
            }

            player.clearHand();
            dealer.clearHand();
            System.out.print("Play again? (yes=1/no=0): ");
            if (scanner.nextInt() == 0) {
                break;
            }
        }

        System.out.println("Game over! Your final balance: " + player.getBalance());
        scanner.close();
    }

    public static void main(String[] args) {
        new Blackjack().play();
    }
}
