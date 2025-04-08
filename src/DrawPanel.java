import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    //Rectangle object allow tracking of what Rect is clicked
    private Rectangle button;

    public DrawPanel() {
        button = new Rectangle(147, 100, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50; 
        int y = 10; 
        JSeparator seperatedSections = new JSeparator();
        int cardWidth = hand.get(0).getImage().getWidth();
        int cardHeight = hand.get(0).getImage().getHeight();
        int gap = 10;
        int totalWidth = 3 * cardWidth + 2 * gap;
        int totalHeight = 3 * cardHeight + 2 * gap;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - totalHeight) / 2;

        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            if (c.getHighlight()) {
                // represents highlight --> border rect around card
                int row = i / 3;
                int col = i % 3;
                int cardX = startX + col * (cardWidth + gap);
                int cardY = startY + row * (cardHeight + gap);
                g.drawRect(cardX, cardY, c.getImage().getWidth(), c.getImage().getHeight());
            }
            // establish the location of rect "hitbox"
            int row = i / 3;
            int col = i % 3;
            int cardX = startX + col * (cardWidth + gap);
            int cardY = startY + row * (cardHeight + gap);
            c.setRectangleLocation(cardX, cardY);
            g.drawImage(c.getImage(), cardX, cardY, null);
        }

        // Drawing bottom New Cards BUTTON
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        button.setLocation(getWidth() - (int)button.getWidth() - 20, 20);
        g.drawString("GET NEW CARDS", (int)button.getX() + 5, (int)button.getY() + 20);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {
        // Getting coordinates of User Click
        Point clicked = e.getPoint();
        System.out.println(clicked);

        // left click
        if (e.getButton() == 1) {
            // if clicked is inside the button rect object
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }

            // iterate through each card
            // check if any of them were clicked
            // if clicked --> flipCard();
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}