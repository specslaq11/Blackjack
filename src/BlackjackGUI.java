import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI extends JFrame {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean dealerRevealed = false;  // Added to manage the visibility of the dealer's second card

    private JPanel playerPanel, dealerPanel, controlPanel;
    private JButton hitButton, standButton, newGameButton;
    private JLabel statusLabel;

    public BlackjackGUI() {
        // Initialize game components
        deck = new Deck();
        player = new Player(100);
        dealer = new Dealer();

        // Setup the frame
        setTitle("Blackjack Game");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Player and dealer panels
        playerPanel = new JPanel();
        dealerPanel = new JPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player"));
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer"));

        // Control panel
        controlPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        statusLabel = new JLabel("Welcome to Blackjack!");

        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        controlPanel.add(newGameButton);
        controlPanel.add(statusLabel);

        playerPanel.setLayout(new FlowLayout());
        dealerPanel.setLayout(new FlowLayout());
        controlPanel.setLayout(new FlowLayout());

        add(playerPanel, BorderLayout.SOUTH);
        add(dealerPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.addCard(deck.dealCard());
                updateGameDisplay();
                checkGameStatus();
            }
        });
        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dealerRevealed = true;  // Update flag to reveal dealer's cards
                dealerPlay();
            }
        });
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        setVisible(true);
    }

    private void updateGameDisplay() {
        playerPanel.removeAll();
        dealerPanel.removeAll();

        // Display player's cards
        for (Card card : player.getHand()) {
            ImageIcon icon = createIcon(card.getImagePath(), 100, 150);  // Adjust size as necessary
            JLabel cardLabel = new JLabel(icon);
            playerPanel.add(cardLabel);
        }

        // Display dealer's cards
        if (dealer.getHand().size() > 1) {
            // Always show the first card
            ImageIcon firstCardIcon = createIcon(dealer.getHand().get(0).getImagePath(), 100, 150);
            dealerPanel.add(new JLabel(firstCardIcon));

            // Use the back of card image for the second card until conditions change
            if (!dealerRevealed) {  // dealerRevealed is a boolean flag to control when to show the dealer's cards
                ImageIcon backIcon = createIcon("resources/cards/card_back.png", 100, 150);
                dealerPanel.add(new JLabel(backIcon));
            } else {
                ImageIcon secondCardIcon = createIcon(dealer.getHand().get(1).getImagePath(), 100, 150);
                dealerPanel.add(new JLabel(secondCardIcon));
            }

            // Add any additional dealer cards
            for (int i = 2; i < dealer.getHand().size(); i++) {
                ImageIcon icon = createIcon(dealer.getHand().get(i).getImagePath(), 100, 150);
                dealerPanel.add(new JLabel(icon));
            }
        }

        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
        pack();
    }




    private void checkGameStatus() {
        if (player.getHandValue() > 21) {
            statusLabel.setText("Bust! You lose.");
            standButton.setEnabled(false);
            hitButton.setEnabled(false);
        } else if (player.getHandValue() == 21) {
            statusLabel.setText("You've got Blackjack!");
            dealerPlay();
        }
    }


    private void dealerPlay() {
        standButton.setEnabled(false);
        hitButton.setEnabled(false);

        dealerRevealed = true; // Reveal dealer cards when it's the dealer's turn
        while (dealer.getHandValue() < 17) {
            dealer.addCard(deck.dealCard());
        }
        updateGameDisplay();
        if (dealer.getHandValue() > 21) {
            statusLabel.setText("Dealer busts! You win!");
        } else if (dealer.getHandValue() >= player.getHandValue()) {
            statusLabel.setText("Dealer wins!");
        } else {
            statusLabel.setText("You win!");
        }
    }


    private void resetGame() {
        deck = new Deck(); // Assuming Deck class has a method to shuffle the cards
        player.clearHand();
        dealer.clearHand();
        dealerRevealed = false;  // Reset the dealerRevealed flag for the new game

        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        updateGameDisplay();

        statusLabel.setText("New game started. Place your bet.");
        standButton.setEnabled(true);
        hitButton.setEnabled(true);
    }



    private ImageIcon createIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }



    public static void main(String[] args) {
        new BlackjackGUI();
    }
}
