import javax.swing.*;

import model.SnakeModel;
import model.Pair;
import view.*;

public class Main {

    public static void main(String[] args) {
        model.SnakeModel smodel = new SnakeModel();
        GraphicalView vGraphical = new GraphicalView(smodel);

        JFrame frame = new JFrame("Snake Game");
        frame.add(vGraphical);

        frame.setSize(240, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        while (true) {
            Pair head = smodel.newHead();

            // if the snake head touches the edge or touches its body, reset the game
            if (head.x <= 0 || head.x >= 44 || head.y <= 0 || head.y >= 44 || smodel.contains(head)) {
                smodel.reset();
                continue;
            }

            Pair food = smodel.getFood();

            // hit box
            if ((head.x <= food.x + 1 && head.x >= food.x - 1) && (head.y <= food.y + 1 && head.y >= food.y - 1)) {
                smodel.grow(head);
            } else {
                smodel.move(head);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}