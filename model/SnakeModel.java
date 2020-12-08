package model;

import java.util.ArrayList;

import java.awt.event.KeyEvent;

public class SnakeModel {
    // A list of model's views
    private ArrayList<IView> views = new ArrayList<>();

    private ArrayList<Pair> body;

    private int direction;

    private Pair food;

    // initial snake length is 3, facing and moving right
    public SnakeModel() {
        this.food = new Pair(22, 22);

        int x = 11;
        int y = 22;

        this.body = new ArrayList<>();

        Pair p1 = new Pair(x, y);
        Pair p2 = new Pair(x - 1, y);
        Pair p3 = new Pair(x - 2, y);

        this.body.add(p1);
        this.body.add(p2);
        this.body.add(p3);

        this.direction = KeyEvent.VK_RIGHT;
    };

    // add a new view
    public void addView(IView view) {
        this.views.add(view);
        view.updateView();
    }

    // get body
    public ArrayList<Pair> getBody() {
        return this.body;
    }

    // get the position pair of food
    public Pair getFood() {
        return this.food;
    }

    // check if the snake body contains a position
    public boolean contains(Pair pos) {
        for (Pair bodypos : this.body) {
            if (bodypos.x == pos.x && bodypos.y == pos.y) {
                return true;
            }
        }
        return false;
    }

    // generate new food if food is eaten
    // new food should be at a random position outside body
    private void generateFood() {
        this.food = null;
        while (this.food == null) {
            boolean outside = true;
            int randX = (int) (Math.random() * 40) + 3;
            int randY = (int) (Math.random() * 40) + 3;

            for (Pair bodyPos : this.body) {
                if ((bodyPos.x <= randX + 1 && bodyPos.x >= randX - 1)
                        && (bodyPos.y <= randY + 1 && bodyPos.y >= randY - 1)) {
                    outside = false;
                    break;
                }
            }

            if (outside) {
                this.food = new Pair(randX, randY);
            }
        }

        this.updateAllViews();
    }

    // get new x, y Pair
    public Pair newHead() {
        int xPos = this.body.get(0).x;
        int yPos = this.body.get(0).y;
        switch (direction) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_D:
                xPos++;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_A:
                xPos--;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
            case KeyEvent.VK_W:
                yPos--;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
            case KeyEvent.VK_S:
                yPos++;
                break;
        }
        return new Pair(xPos, yPos);
    }

    // append to body when successfully eating a food
    public void grow(Pair newhead) {
        this.body.add(0, newhead);

        generateFood();
    }

    // not eating food and automatically generates new image
    public void move(Pair newhead) {
        this.body.add(0, newhead);
        this.body.remove(this.body.size() - 1);

        this.updateAllViews();
    }

    /*
     * receive the latest command;
     * WSAD, arrow keys, and numberpad arrow keys
     * supported do nothing if the command is opposite to current direction ignores
     * all other keys
     */
    
    public void setCommand(int keyCommand) {
        switch (keyCommand) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_D:
                if (direction == KeyEvent.VK_LEFT || direction == KeyEvent.VK_KP_LEFT || direction == KeyEvent.VK_A) {
                    return;
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_A:
                if (direction == KeyEvent.VK_RIGHT || direction == KeyEvent.VK_KP_RIGHT || direction == KeyEvent.VK_S) {
                    return;
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
            case KeyEvent.VK_W:
                if (direction == KeyEvent.VK_DOWN || direction == KeyEvent.VK_KP_DOWN || direction == KeyEvent.VK_S) {
                    return;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
            case KeyEvent.VK_S:
                if (direction == KeyEvent.VK_UP || direction == KeyEvent.VK_KP_UP || direction == KeyEvent.VK_W) {
                    return;
                }
                break;
            default:
                return;

        }

        this.direction = keyCommand;

    }

    // get moving direction of the snake,
    // i.e. command direction
    public int getdirection() {
        return this.direction;
    }

    // reset model to initial values
    public void reset() {
        this.food = new Pair(22, 22);

        int x = 11;
        int y = 22;

        this.body.clear();
        Pair p1 = new Pair(x, y);
        Pair p2 = new Pair(x - 1, y);
        Pair p3 = new Pair(x - 2, y);

        this.body.add(p1);
        this.body.add(p2);
        this.body.add(p3);

        this.direction = KeyEvent.VK_RIGHT;
    }

    // update all views
    private void updateAllViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }

}
