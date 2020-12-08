package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicalView extends JPanel implements KeyListener {

    private SnakeModel model;

    public GraphicalView(SnakeModel sModel) {
        super();
        this.model = sModel;
        this.layoutView();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.model.addView(new IView() {
            public void updateView() {
                repaint();
            }
        });
    }

    // set layout
    private void layoutView() {
        this.setPreferredSize(new Dimension(220, 220));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // paint snake and food
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        ArrayList<Pair> body = this.model.getBody();
        Pair food = this.model.getFood();

        // draw food
        g2.drawString("\u03C0", food.x * 5, food.y * 5);

        g2.setStroke(new BasicStroke(8));
        // draw snake
        for (int i = 1; i < body.size(); i++) {
            g2.drawLine(body.get(i - 1).x * 5, body.get(i - 1).y * 5, body.get(i).x * 5, body.get(i).y * 5);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        model.setCommand(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
